package cn.gzsunrun.downtime.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/16
 * @time: 9:14
 */
@Data
@TableName("dt_down_time_record")
@ApiModel(value = "故障统计标记数据", description = "故障数据")
public class DowntimeRecord {

    @TableId
    private String id;

    @ApiModelProperty("库名")
    @TableField(exist = false)
    private String sys;
    @ApiModelProperty("设备编号")

    @TableField(exist = false)
    private String equId;
    @ApiModelProperty("故障编号")

    @TableField(exist = false)
    private String faultId;

    @TableField(exist = false)
    @ApiModelProperty("故障描述")
    private String faultDesc;
    @ApiModelProperty("创建时间")
    @TableField(exist = false)
    private String createtime;
    @TableField(exist = false)
    private Long date;
    @ApiModelProperty("持续时间")
    @TableField(exist = false)
    private String lastTime;
    @ApiModelProperty("库名中文")
    @TableField(exist = false)
    private String sysName;
    @ApiModelProperty("是否统计")
    private Boolean count;
    @ApiModelProperty("统计故障次数")
    @TableField(exist = false)
    private Integer countNum;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("班组名")
    @TableField(exist = false)
    private String className;

    @ApiModelProperty("图片组")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> fileBaseIds;


}
