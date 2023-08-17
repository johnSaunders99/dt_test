package cn.gzsunrun.downtime.constant.enums;

import cn.gzsunrun.downtime.entity.KqMealType;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Date;
import java.util.List;
import java.util.Map;

public enum MealTypeEnum {



    Breakfast("早餐", "breakfast", -7200000L, 5400000L),
    Lunch("午餐", "lunch", 9000000L, 21600000L),
    Dinner("晚餐", "dinner", 31200000L, 43200000L),

    Unknown("未知用餐类型", "unknown", 999999999999L, 0L);

    private final String mealType;

    private final String type;

    private final long startTime;

    private final long endTime;

    MealTypeEnum(String mealType, String type, long startTime, long endTime){
        this.mealType = mealType;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMealType() {
        return mealType;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    public long getStartTime(Map<String,KqMealType> mealTypes) {
        if (!mealTypes.containsKey(type)){
            //返回默认值
            return startTime;
        }
        KqMealType kqMealType = mealTypes.get(type);
        return kqMealType.getStartTime();
    }

    public long getEndTime(Map<String,KqMealType> mealTypes) {
        if (!mealTypes.containsKey(type)){
            //返回默认值
            return endTime;
        }
        KqMealType kqMealType = mealTypes.get(type);
        return kqMealType.getEndTime();
    }

    @JsonCreator
    public static MealTypeEnum create(long timestamp, Map<String,KqMealType> mealTypes){
        Date date = DateUtil.date(timestamp);
        long time = DateUtil.parse(DateUtil.formatTime(date), "HH:mm:ss").getTime();

        MealTypeEnum[] types = MealTypeEnum.values();
        for (MealTypeEnum typeEnum : types) {
            if (mealTypes.containsKey(typeEnum.getType())){
                KqMealType kqMealType = mealTypes.get(typeEnum.getType());
                if ((kqMealType.getStartTime() <= time) && (kqMealType.getEndTime() >= time)){
                    return typeEnum;
                }
            }
        }
        return Unknown;
    }

    /**
     * 根据类型 获取枚举值
     *
     * @param type
     * @return
     */
    public static MealTypeEnum createByType(String type){
        MealTypeEnum[] types = MealTypeEnum.values();
        for (MealTypeEnum typeEnum : types) {
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return Unknown;
    }

}
