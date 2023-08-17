package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author john saunders
 * @description:
 * @date: 2023/7/13
 * @time: 17:33
 */

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ApproveResultEnum {
    pass(1, "已通过"),
    approving(0, "待审批"),
    reject(2, "已驳回"),
    undo(-1, "已撤回");

    @EnumValue
    private Integer type;

    @JsonValue
    private String typeName;


    /**
     * 枚举入参注解
     *
     * @param value
     * @return
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ApproveResultEnum create(Integer value) {
        for (ApproveResultEnum statusEnum : values()) {
            if (Objects.equals(statusEnum.getType(), value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
