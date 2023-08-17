package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 休息日类型 枚举类
 *
 * @author SimonMai
 */

public enum HolidayTypeEnum {


    /**
     * 法定节假日
     */
    LegalHoliday(0, "法定节假日"),

    /**
     * 假期
     */
    Vacation(1, "假期"),

    /**
     * 未知类型
     */
    UnknownType(99, "未知类型");

    @EnumValue
    private final int type;

    private final String typeName;

    HolidayTypeEnum(int type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    @JsonValue
    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    @JsonCreator
    public static HolidayTypeEnum create(int type){
        HolidayTypeEnum[] types =  HolidayTypeEnum.values();
        for (HolidayTypeEnum typeEnum : types) {
            if (type == typeEnum.getType()){
                return typeEnum;
            }
        }
        return UnknownType;
    }
}
