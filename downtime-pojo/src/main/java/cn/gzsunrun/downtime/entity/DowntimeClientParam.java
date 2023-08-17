package cn.gzsunrun.downtime.entity;

import lombok.Data;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 10:48
 */
@Data
public class DowntimeClientParam {
    private String cname;

    private Long pageSize;

    private Long currentPage;

    private String sys;

    private String equid;

    private String desc;

    private String stime;

    private String etime;
}
