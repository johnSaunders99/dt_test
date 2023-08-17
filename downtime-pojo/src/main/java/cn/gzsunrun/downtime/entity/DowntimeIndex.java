package cn.gzsunrun.downtime.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 2:48
 */
@Data

@TableName("dt_down_time_index")
@ApiModel(value = "每月库统计线数据", description = "每月库统计线数据")
public class DowntimeIndex {
    private String id;
    private Long date;
    @TableField(exist = false)
    private String dateStr;
    private String sys;
    private String sysName;
    @ApiModelProperty("字符串存小数数字,指标线,按一个月设置,默认")
    private String positiveLine;
    @ApiModelProperty("字符串存小数数字,指标线,按一个月设置,默认")
    private String negetiveLine;
}
