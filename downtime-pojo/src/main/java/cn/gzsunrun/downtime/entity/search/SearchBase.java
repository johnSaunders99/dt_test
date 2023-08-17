package cn.gzsunrun.downtime.entity.search;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author john saunders
 * @description:
 * @date: 2023/4/24
 * @time: 14:10
 */
@Data
public class SearchBase implements Serializable {
    /**
     * SQL示例:
     * ALTER TABLE `kq_company_user`
     * ADD COLUMN `search_text` varchar(1500) NULL COMMENT '搜索使用冗余字段' AFTER `card_no`;
     */
    @ApiModelProperty("查询预留字段,包含拼音/大写/原搜索字段")
    @TableField("search_text")
    private String searchText;
}
