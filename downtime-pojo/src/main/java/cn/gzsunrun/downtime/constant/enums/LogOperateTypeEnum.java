package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 日志操作类型 枚举类
 *
 * @author SimonMai
 */
public enum LogOperateTypeEnum {


    Insert("insert", "新增"),
    Update("update", "修改"),
    Delete("delete", "删除"),
    Used("used", "使用"),
    Permission("permission", "授权"),
    ResetNormal("resetNormal", "改为正常"),
    Sync("sync", "同步"),
    Import("import", "导入"),
    Export("export", "导出"),
    Lock("lock", "锁定"),
    SYSCREATE("sysCreate", "系统生成"),
    SYSDELETE("sysDelete", "关联删除"),
    Unknown("unknown", "未知操作类型");

    @EnumValue
    private final String type;

    @JsonValue
    private final String typeName;

    LogOperateTypeEnum(String type, String typeName){
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
    public static LogOperateTypeEnum create(String type){
        LogOperateTypeEnum[] enums = LogOperateTypeEnum.values();
        for (LogOperateTypeEnum e : enums) {
            if (type.equals(e.getType())){
                return e;
            }
        }
        return Unknown;
    }

}
