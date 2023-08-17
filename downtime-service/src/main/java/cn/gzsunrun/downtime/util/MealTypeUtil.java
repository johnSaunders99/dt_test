package cn.gzsunrun.downtime.util;

import cn.gzsunrun.downtime.constant.enums.MealTypeEnum;
import cn.gzsunrun.downtime.entity.CanteenClockHistory;
import cn.gzsunrun.downtime.entity.KqMealType;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author john saunders
 * @description:
 * @date: 2023/4/20
 * @time: 10:18
 */
public class MealTypeUtil {


    public static final Integer breakfastOffset = 10;
    public static final Integer lunchOffset = 15;


    public static Map<MealTypeEnum, Map<String,List<CanteenClockHistory>>> obtainMealTypeUserId(List<CanteenClockHistory> histories, Map<String,KqMealType> mealTypes){
        HashMap<MealTypeEnum, Map<String, List<CanteenClockHistory>>> res = new HashMap<>();
        for (MealTypeEnum typeEnum:
        MealTypeEnum.values()) {
            Map<String, List<CanteenClockHistory>> data = filterMealType(histories, typeEnum, mealTypes);
            res.put(typeEnum, data);
        }
        return res;
    }


    public static Map<MealTypeEnum, List<CanteenClockHistory>> obtainMealType(List<CanteenClockHistory> histories, Map<String,KqMealType> mealTypes){
        HashMap<MealTypeEnum, List<CanteenClockHistory>> res = new HashMap<>();
        for (MealTypeEnum typeEnum:
                MealTypeEnum.values()) {
            List<CanteenClockHistory> data = histories.parallelStream().filter(o -> {
                MealTypeEnum mealTypeEnum = MealTypeEnum.create(o.getClickTime(), mealTypes);
                return typeEnum.equals(mealTypeEnum);
            }).collect(Collectors.toList());
            res.put(typeEnum, data);
        }
        return res;
    }

    public static Map<String, List<CanteenClockHistory>> filterMealType(List<CanteenClockHistory> histories, MealTypeEnum type, Map<String,KqMealType> mealTypes){
        return histories.parallelStream().filter(o->{
            MealTypeEnum mealTypeEnum = MealTypeEnum.create(o.getClickTime(),mealTypes);
            return type.equals(mealTypeEnum);
        }).collect(Collectors.groupingBy(
                CanteenClockHistory::getUserId
        ));
    }



}
