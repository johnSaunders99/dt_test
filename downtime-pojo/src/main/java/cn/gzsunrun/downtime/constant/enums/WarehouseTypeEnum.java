package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 仓库类型  枚举类
 *
 * @author SimonMai
 */
public enum WarehouseTypeEnum {

    /**
     * 原材料仓库
     */
    RawMaterialWarehouse(1, "原材料仓库"),

    /**
     * 半成品仓库
     */
    HalfMaterialWarehouse(2, "半成品仓库"),

    /**
     * 未知类型
     */
    UnknownType(99, "未知类型");

    @EnumValue
    private final int type;

    private final String typeName;

    WarehouseTypeEnum(int type, String typeName){
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
    public static WarehouseTypeEnum create(int type){
        WarehouseTypeEnum[] types = WarehouseTypeEnum.values();
        for (WarehouseTypeEnum typeEnum : types) {
            if (type == typeEnum.getType()){
                return typeEnum;
            }
        }
        return UnknownType;
    }

}
