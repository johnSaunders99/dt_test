package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author john saunders
 * @description:
 * @date: 2023/4/28
 * @time: 10:19
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ICCardStatusEnum {

    OP(1, "启用"),
    LS(0, "挂失"),
    ROP(1, "复原"),
    BAN(0, "禁用");

    private int type;

    @EnumValue
    @JsonValue
    private String typeName;


    /**
     * 枚举入参注解
     *
     * @param value
     * @return
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ICCardStatusEnum getByValue(String value) {
        for (ICCardStatusEnum statusEnum : values()) {
            if (statusEnum.getTypeName().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
