package cn.gzsunrun.downtime.entity;

import cn.gzsunrun.downtime.entity.sub.ClassDayCountRes;
import cn.gzsunrun.downtime.entity.sub.DayCountBase;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 2:35
 */
@Data
@ApiModel
public class DowntimeCountRes {

    private String sysName;

    private String describe;

    private List<DayCountBase> totalBase;

    private List<ClassDayCountRes> details;

}
