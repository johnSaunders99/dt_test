package cn.gzsunrun.downtime.controller.downtime;

import cn.gzsunrun.downtime.entity.DowntimeCheckParam;
import cn.gzsunrun.downtime.entity.DowntimeRecord;
import cn.gzsunrun.downtime.entity.DowntimeSaveParam;
import cn.gzsunrun.downtime.service.DowntimeService;
import cn.gzsunrun.oars.dbutils.entity.ResultPage;
import cn.gzsunrun.oars.dbutils.page.OarsPageUtil;
import cn.gzsunrun.oars.entity.OarsApiResponse;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/15
 * @time: 17:30
 */
@Api(tags = "【停机小系统】小停机应用 前端控制器")
@RestController
@RequestMapping("/downtime")
@RequiredArgsConstructor
@Slf4j
public class DowntimeController {

    private final DowntimeService downtimeService;

    @ApiOperation(value = "分页获取故障采集情况列表")
    @GetMapping("/page")
    public OarsApiResponse<ResultPage<DowntimeRecord>> page(DowntimeCheckParam param){
        try {

            return OarsApiResponse.success(OarsPageUtil.pageResult(downtimeService.page(param)));
        } catch (Exception e){
            e.printStackTrace();
            return OarsApiResponse.fail(e.getMessage());
        }
    }
    @ApiOperation(value = "获取故障采集的设备列表")
    @GetMapping("/equIds")
    public OarsApiResponse<List<DowntimeRecord>> equIds(){
        try {

            return OarsApiResponse.success(downtimeService.getEquipDataList());
        } catch (Exception e){
            e.printStackTrace();
            return OarsApiResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "更改故障是否采集")
    @PostMapping("/save")
    public OarsApiResponse<Boolean> save(@RequestBody DowntimeSaveParam param){
        try {
            DowntimeRecord record = new DowntimeRecord();
            record.setId(param.getId());
            record.setCount(param.getCount());
            record.setRemark(param.getRemark());
            record.setFileBaseIds(param.getFileBaseIds());
            if (param.getId()==null){
                throw new RuntimeException("请选择标记记录!");
            }
            try {
                DowntimeRecord byId = downtimeService.getById(param.getId());
                if (byId!=null){
                    List<String> fileBaseIds = byId.getFileBaseIds();
                    if (StrUtil.isBlank(param.getRemark())){
                        //不改remark的话保持不变
                        record.setFileBaseIds(fileBaseIds);
                    }
                }
            }catch (Exception e){
                //找不到 ignore
            }
            return OarsApiResponse.success(downtimeService.saveOrUpdate(record));
        } catch (Exception e){
            e.printStackTrace();
            return OarsApiResponse.fail(e.getMessage());
        }
    }


    @ApiOperation(value = "导出表格数据")
    @GetMapping("/export/list")
    public OarsApiResponse<ResultPage<DowntimeRecord>> export(DowntimeCheckParam param){
        try {

            return OarsApiResponse.success(OarsPageUtil.pageResult(downtimeService.page(param)));
        } catch (Exception e){
            e.printStackTrace();
            return OarsApiResponse.fail(e.getMessage());
        }
    }







}
