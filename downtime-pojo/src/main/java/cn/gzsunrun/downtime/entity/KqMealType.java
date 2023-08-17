package cn.gzsunrun.downtime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @author john saunders
 * @description:
 * @date: 2023/6/28
 * @time: 14:38
 */
@Data
@TableName(value = "kq_meal_type", autoResultMap = true)
@ApiModel(value = "用餐时间类型", description = "用餐时间类型表")
@FieldNameConstants
public class KqMealType implements Serializable {

    @TableId
    private Integer id;

    private Long createdTime;

    private Long updatedTime;

    private String creator;

    private String updater;

    private String mealType;

    private Long startTime;

    private Long endTime;

    private String mealTypeName;
}
