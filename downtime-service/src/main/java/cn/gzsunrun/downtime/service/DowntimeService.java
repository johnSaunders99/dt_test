package cn.gzsunrun.downtime.service;

import cn.gzsunrun.downtime.entity.DowntimeCheckParam;
import cn.gzsunrun.downtime.entity.DowntimeCountRes;
import cn.gzsunrun.downtime.entity.DowntimeRecord;
import cn.gzsunrun.oars.dbutils.entity.ResultPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/16
 * @time: 15:15
 */
public interface DowntimeService extends IService<DowntimeRecord> {

    IPage<DowntimeRecord> page(DowntimeCheckParam param);
    List<DowntimeRecord> list(DowntimeCheckParam param);
    List<DowntimeCountRes> countingTotal(Long monthTimestamp);
    String editDowntimeCountable(DowntimeRecord record);

    List<DowntimeRecord> getEquipDataList();




}
