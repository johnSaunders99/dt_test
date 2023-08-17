package cn.gzsunrun.downtime.entity.excel;

/**
 * *@description: 导出通用实体类
 *
 * @author : john saunders
 * @date : 2020-11-26 10:50
 **/

import lombok.Data;

import java.io.Serializable;


@Data
public class ExcelVo implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SRC_ID = "src_id";
    public static final String ORG_ID = "org_id";
}
