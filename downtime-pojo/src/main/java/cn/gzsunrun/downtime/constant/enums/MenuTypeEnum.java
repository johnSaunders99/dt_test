package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 菜单类型 枚举类
 *
 * @author SimonMai
 */

public enum MenuTypeEnum {


    Breakfast("breakfast", "早餐"),
    Dinner("dinner", "正餐"),

    UnknownMenuType("unknownMenuType", "未知类型");

    @EnumValue
    private final String type;

    private final String typeName;

    MenuTypeEnum(String type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    @JsonCreator
    public static MenuTypeEnum create(String type){
        MenuTypeEnum[] types = MenuTypeEnum.values();
        for (MenuTypeEnum typeEnum : types) {
            if (type.equals(typeEnum.getType())){
                return typeEnum;
            }
        }
        return UnknownMenuType;
    }

}
