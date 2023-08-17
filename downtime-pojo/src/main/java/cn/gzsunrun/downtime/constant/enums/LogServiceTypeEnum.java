package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 日志服务类型 枚举类
 *
 * @author SimonMai
 */
public enum LogServiceTypeEnum {


    Canteen("canteen", "行政"),
    Attendance("attendance", "考勤"),
    Unknown("unknown", "未知业务类型");

    @EnumValue
    private final String type;

    @JsonValue
    private final String typeName;

    LogServiceTypeEnum(String type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    @JsonCreator
    public static LogServiceTypeEnum create(String type){
        LogServiceTypeEnum[] enums = LogServiceTypeEnum.values();
        for (LogServiceTypeEnum e : enums) {
            if (type.equals(e.getType())){
                return e;
            }
        }
        return Unknown;
    }

}
