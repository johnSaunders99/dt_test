package cn.gzsunrun.downtime.entity.sub;

import lombok.Data;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 2:37
 */
@Data
public class DayCountBase {
    private String dateStr;
    private Long date;
    private Integer count;
}
