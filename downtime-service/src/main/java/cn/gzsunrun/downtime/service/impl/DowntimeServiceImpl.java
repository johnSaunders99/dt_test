package cn.gzsunrun.downtime.service.impl;

import cn.gzsunrun.downtime.client.SunrunPage;
import cn.gzsunrun.downtime.client.SunrunService;
import cn.gzsunrun.downtime.client.SunrunTPage;
import cn.gzsunrun.downtime.entity.DowntimeCheckParam;
import cn.gzsunrun.downtime.entity.DowntimeCountRes;
import cn.gzsunrun.downtime.entity.DowntimeRecord;
import cn.gzsunrun.downtime.mapper.DowntimeMapper;
import cn.gzsunrun.downtime.service.DowntimeService;
import cn.gzsunrun.downtime.util.SearchTextUtil;
import cn.gzsunrun.oars.dbutils.entity.ResultPage;
import cn.gzsunrun.oars.dbutils.page.OarsPageUtil;
import cn.gzsunrun.tool.oars.util.HttpUtils;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/16
 * @time: 15:16
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DowntimeServiceImpl extends ServiceImpl<DowntimeMapper, DowntimeRecord> implements DowntimeService {
    private final SunrunService srService;

    @Override
    public IPage<DowntimeRecord> page(DowntimeCheckParam param) {

        if (!StringUtils.isBlank(param.getSysName())){
            String sysName = param.getSysName();
            StringBuilder buf = new StringBuilder();
            for (char c :sysName.toCharArray()){
                char firstLetter = PinyinUtil.getFirstLetter(c);
                buf.append(firstLetter);
            }
            param.setSysName(buf.toString());
        }
        //封装一个登录类, 有效期25分钟, 仿照之前的aie登录 json username xiaotingji password Abcd@12345
        //http://10.161.118.57:99/sunrun-api/login
        IPage<DowntimeRecord> objectPage = OarsPageUtil.startPage();
        SunrunTPage<DowntimeRecord> page = srService.getPageData(param, objectPage.getSize(), objectPage.getCurrent(), 0);
        if (page == null){
            return objectPage;
        }
        SunrunPage<DowntimeRecord> spage = page.getPage();
        List<DowntimeRecord> datas = spage.getData();
        objectPage.setRecords(datas);
        objectPage.setTotal(spage.getTotalCount());
        objectPage.setSize(spage.getPageSize());
        objectPage.setCurrent(spage.getCurrentPage());
        objectPage.setPages(spage.getTotalPage());

        fillDowntimeRecord(datas, param.getTimestampStart(), param.getTimestampEnd());
        //要记得处理偶尔失败的情况!!!
        //然后再封装一个请求接口, 暂定使用写死v10的,分页的. http://10.161.118.57:99/sunrun-api/api/v8/findGjkByLkFaultRecord
        //甲乙班以时间段直接区分, 统计那个按上面规则算好是否之后根据filter直接算出每天的count写进每条里面,还是封装一个方法排序和填充吧

        return objectPage;
    }

    // TODO 策略: 查询出来的每条记录信息没有的用规则补上,有的直接匹配返回前端, List封装一个方法去补,后面导出也要复用
    /**
     * 规则:
     *相近时间的同一台设备或相邻输送机故障当一条，村田堆垛机任务完成时间超出预定值、装置通讯异常不计入，
     * 配方库除堆垛机外其它设备故障持续时间为0的不计入，另外设备开关机时间也可能会产生大量的故障信息，注意观察故障时间予以删除。
     * 成品库的数据、辅料库AGV的数据刚完成采集，需进一步研究确定如何筛选。利用公式得出故障数据。
     *每日故障数据分班次统计，以15:45为界分开，并标注好甲乙班,
     * 6、生产数据成品库为发货数（件），辅料库为出入库数（托），配方库为入库数（件）。
     * 数据来源：辅料库、配方库为班组每日发送的运行日报表、运行情况表，成品库为成品收发存日报。
     * 配方库在“配方库日报”sheet中，辅料库在“出入库情况”sheet中，成品库在月份sheet中。
     */
    private void fillDowntimeRecord(List<DowntimeRecord> datas, Long startDate, Long endDate){
        List<DowntimeRecord> needSave = new ArrayList<>();
        List<String> notInError = new ArrayList<>();
        notInError.add("通讯异常");
        List<String> ids = datas.parallelStream().map(DowntimeRecord::getId).collect(Collectors.toList());
        Map<String, DowntimeRecord> downtimeRecords = baseMapper.selectBatchIds(ids).parallelStream()
                .collect(Collectors.toMap(DowntimeRecord::getId, o->o));
        datas.parallelStream().forEach(o->{
            o.setDate(DateUtil.parse(o.getCreatetime(), DatePattern.NORM_DATETIME_FORMAT).getTime());
        });
        for (DowntimeRecord record :datas){
            if (!downtimeRecords.containsKey(record.getId())){
                //不存在,补时间和是否
                if ("0".equals(record.getLastTime()) || isInIgorne(notInError, record.getFaultDesc())){
                    record.setCount(Boolean.FALSE);
                }else {
                    record.setCount(Boolean.TRUE);
                }
                needSave.add(record);
            }else {
                DowntimeRecord have = downtimeRecords.get(record.getId());
                toOtherDowntimeRecord(record, have);
            }

        }
        saveBatch(needSave);
        countDataSize(datas, startDate, endDate);
    }

    private void countDataSize(List<DowntimeRecord> downtimeRecords, Long startDate, Long endDate){
        List<Long> days = SearchTextUtil.getbetweenDays(startDate, endDate);
        for (Long day :days){
            DateTime date = DateUtil.date(day);
            List<DowntimeRecord> dayDR = downtimeRecords.parallelStream().filter(o -> {
                return DateUtil.isSameDay(date, DateUtil.date(o.getDate()));
            }).collect(Collectors.toList());
            daySize(dayDR);
        }
    }
    private void daySize(List<DowntimeRecord> dayDR){
        Map<String, List<DowntimeRecord>> collect = dayDR.parallelStream().collect(Collectors.groupingBy(DowntimeRecord::getClassName));
        for (String className:collect.keySet()){
            List<DowntimeRecord> downtimeRecords = collect.get(className);
            int size = downtimeRecords.parallelStream().filter(
                    DowntimeRecord::getCount
            ).collect(Collectors.toList()).size();
            downtimeRecords.parallelStream().forEach(o->{
                o.setCountNum(size);
            });
        }
    }

    private Boolean isInIgorne(List<String> ignores, String data){
        Boolean res = Boolean.FALSE;
        for (String m :ignores){
            if (StrUtil.contains(data, m)){
                res =Boolean.TRUE;
                return res;
            }
        }
        return res;
    }

    private void toOtherDowntimeRecord(DowntimeRecord origin, DowntimeRecord fill){
        origin.setLastTime(fill.getLastTime());
        origin.setCount(fill.getCount());
        origin.setRemark(fill.getRemark());
    }

    @Override
    public List<DowntimeRecord> list(DowntimeCheckParam param) {
        //同上只是换一个接口,不走分页接口.
        return null;
    }

    @Override
    public List<DowntimeCountRes> countingTotal(Long monthTimestamp) {
        return null;
    }

    @Override
    public String editDowntimeCountable(DowntimeRecord record) {
        saveOrUpdate(record);
        return record.getId();
    }

    @Override
    public List<DowntimeRecord> getEquipDataList() {
        List<DowntimeRecord> res = new ArrayList<>();
        SunrunTPage<DowntimeRecord> downtimeRecordSunrunTPage = srService.equIdDataList(0);
        if (downtimeRecordSunrunTPage!=null){
            res = downtimeRecordSunrunTPage.getList();
        }
        return res;
    }
}
