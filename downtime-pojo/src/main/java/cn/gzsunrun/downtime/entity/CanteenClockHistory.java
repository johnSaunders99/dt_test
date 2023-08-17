package cn.gzsunrun.downtime.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 饭堂打卡记录
 *
 * @author hgz
 */
@Data
@TableName(value = "kq_canteen_clock_history", autoResultMap = true)
@ApiModel(value = "饭堂打卡记录", description = "饭堂打卡记录")
public class CanteenClockHistory implements Serializable {

    @TableId
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("设备编号")
    private String deviceCode;

    @ApiModelProperty("订单号")
    private String personNo;
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("是否成功")
    private Boolean status;

    @ApiModelProperty("0实时通知。1历史通知")
    private Integer realTime;

    @ApiModelProperty("来源：1：刷面，2：二维码, 3:IC卡, CANTEEN_ADD->6:外仓人员")
    private Integer fromType;

//    @ApiModelProperty("打卡人的名字")
//    @TableField(typeHandler = JacksonTypeHandler.class)
//    private EventLibMatInfo libMatInfo;

    @ApiModelProperty("记录创建时间戳")
    private Long created;
    @ApiModelProperty("打卡时间")
    private Long clickTime;
    private String photoId;

    @ApiModelProperty(value = "打卡人员所属部门ID")
    private String departId;

    @ApiModelProperty(value = "打卡人员所属班组ID")
    private String groupId;


    @ApiModelProperty(value = "执行SQL查询COUNT()函数的返回结果")
    @TableField(exist = false)
    private Integer count;
    @TableField(exist = false)
    private String clickDay;

}

