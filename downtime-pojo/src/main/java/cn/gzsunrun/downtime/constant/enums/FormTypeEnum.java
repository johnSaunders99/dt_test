package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/5/23
 * @time: 16:08
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum  FormTypeEnum {

    SELECT("SELECT", new ArrayList<String>(){{
    add("checkbox");
    add("radio");
    add("select");
    }}),
    WRITE("WRITE",new ArrayList<String>()
    {{
        add("input");
        add("textarea");
    }});


    @EnumValue
    @JsonValue
    private String typeName;


    private List<String> types;


    /**
     * 枚举入参注解
     *
     * @param value
     * @return
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FormTypeEnum getByValue(String value) {
        for (FormTypeEnum statusEnum : values()) {
            if (statusEnum.getTypeName().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}
