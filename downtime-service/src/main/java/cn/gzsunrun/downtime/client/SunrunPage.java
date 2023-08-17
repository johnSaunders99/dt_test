package cn.gzsunrun.downtime.client;

import lombok.Data;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 10:37
 */
@Data
public class SunrunPage<T> {
    private List<T> data;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;


}
