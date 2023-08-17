package cn.gzsunrun.downtime.entity.sub;

import lombok.Data;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 2:39
 */
@Data
public class ClassDayCountRes {
    private String className;
    private String sysName;
    private String describe;
    private List<DayCountBase> dayBase;
}
