package cn.gzsunrun.downtime.constant.enums;

import cn.gzsunrun.downtime.entity.KqMealType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public enum MealShowTypeEnum {



    HaveBreakfast("只展示食用早餐", Boolean.FALSE, 2, MealTypeEnum.Breakfast),
    HaveLunch("只展示食用午餐", Boolean.FALSE, 3,MealTypeEnum.Lunch),
    HaveDinner("只展示食用晚餐", Boolean.FALSE, 4,MealTypeEnum.Dinner),
    NotHaveBreakfast("只展示未食用早餐", Boolean.TRUE, -5, MealTypeEnum.Breakfast),
    NotHaveLunch("只展示未食用午餐", Boolean.TRUE, -6, MealTypeEnum.Lunch),
    NotHaveDinner("只展示未食用晚餐", Boolean.TRUE, -7, MealTypeEnum.Dinner),
    HaveMeal("只展示有用餐", Boolean.FALSE, 1, MealTypeEnum.Unknown),
    NotHaveMeal("只展示未用餐", Boolean.TRUE, -1, MealTypeEnum.Unknown),
    NoLimits("展示全部", null, 0, MealTypeEnum.Unknown);

    private String mealType;

    private Boolean beyond;

    @EnumValue
    @JsonValue
    private Integer type;

    private MealTypeEnum relateMeal;




    MealShowTypeEnum(String mealType, Boolean beyond, Integer type, MealTypeEnum relateMeal) {
        this.mealType = mealType;
        this.beyond = beyond;
        this.type = type;
        this.relateMeal = relateMeal;
    }
    /**
     * 枚举入参注解
     *
     * @param value
     * @return
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MealShowTypeEnum create(Integer value) {
        for (MealShowTypeEnum statusEnum : values()) {
            if (Objects.equals(statusEnum.getType(), value)) {
                return statusEnum;
            }
        }
        return null;
    }


    public static MealShowTypeEnum toShowType(Boolean useMeal, String type){
        if (useMeal == null && StrUtil.isBlank(type)){
            return MealShowTypeEnum.NoLimits;
        }
        if (useMeal==null){
            useMeal = true;
        }
        if (StrUtil.isBlank(type)){
            type = MealTypeEnum.Unknown.getType();
        }
        for (MealShowTypeEnum value : MealShowTypeEnum.values()) {
            Boolean beyond = value.getBeyond();
            if (beyond == null){
                continue;
            }
            if (useMeal){
                if (beyond){
                    continue;
                }
            }else {
                if (!beyond){
                    continue;
                }
            }
            if (value.getRelateMeal()!=null && value.getRelateMeal().getType().equals(type)){
                return value;
            }
        }
        return MealShowTypeEnum.NoLimits;

    }
}
