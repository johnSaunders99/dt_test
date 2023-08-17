package cn.gzsunrun.downtime.entity;

import lombok.Data;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/16
 * @time: 14:59
 */
@Data
public class DowntimeCheckParam {
    private String sysName;

    private String className;

    private String equId;

    private Long timestampStart;

    private Long timestampEnd;

}
