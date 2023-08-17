package cn.gzsunrun.downtime.entity;

import lombok.Data;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/16
 * @time: 14:59
 */
@Data
public class DowntimeSaveParam {
    private String id;

    private String remark;

    private Boolean count;

    private List<String> fileBaseIds;

}
