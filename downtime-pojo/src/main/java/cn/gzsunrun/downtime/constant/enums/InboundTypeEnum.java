package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 出、入、退库操作 枚举类
 *
 * @author SimonMai
 */

public enum InboundTypeEnum {


    Inbound(0, "入库"),
    Outbound(1, "出库"),
    CancellingStocks(2, "退库"),

    UnknownType(99, "未知类型");

    @EnumValue
    private final int type;

    private final String typeName;

    InboundTypeEnum(int type, String typeName){
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
    public static InboundTypeEnum create(int type){
        InboundTypeEnum[] types = InboundTypeEnum.values();
        for (InboundTypeEnum typeEnum : types) {
            if (type == typeEnum.getType()){
                return typeEnum;
            }
        }
        return UnknownType;
    }

}
