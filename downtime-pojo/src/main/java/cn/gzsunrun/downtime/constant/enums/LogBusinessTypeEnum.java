package cn.gzsunrun.downtime.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 日志业务类型 枚举类
 *
 * @author SimonMai
 */
public enum LogBusinessTypeEnum {

    /*****************  考勤业务 枚举值  **************/
    AttendancePerson("person", "考勤-人员管理"),
    AttendanceAnnualLeave("annualLeave", "考勤-假期管理"),
    AttendanceAnnualLeaveType("annualLeaveType", "考勤-假期类型管理"),
    AttendanceAtteClock("atteClock", "考勤-考勤"),
    AttendanceShiftUser("shiftUser", "考勤-排班"),
    AttendanceShift("shift", "考勤-班次"),
    AttendanceRoleCount("roleCount", "考勤-打卡次数"),
    AttendanceTransferInfo("transferInfo", "考勤-异动状态"),
    AttendanceDeviceScope("deviceScope", "考勤-打卡范围"),
    AttendanceHoliday("holiday", "考勤-节假日"),
    AttendanceManagePositionRel("managePositionRel", "考勤-大类岗位管理"),
    AttendanceShiftGroupRel("shiftGroupRel", "考勤-班次规则"),
    AttendanceShiftLack("shiftLack", "考勤-欠班"),
    AttendanceShiftWorkOver("shiftWorkOver", "考勤-加班"),
    AttendanceShiftLeave("shiftLeave", "考勤-请假"),
    AttendanceReportShift("reportShift", "考勤-汇总审批"),
    ATTBindUserChild("userchildren", "考勤-育儿假"),
    AttendanceUerParent("userParent", "考勤-用户父母信息"),

    PerRoleUser("perRoleUser", "考勤-角色分配用户"),
    PerRoleResource("perRoleResource", "考勤-角色分配资源"),
    AttendanceOARSUSER("user", "考勤-用户信息"),
    AttendanceOARSDEPT("dept", "考勤-部门"),
    AttendanceOARSGROUP("group", "考勤-班组"),


    /*****************  行政业务 枚举值  **************/
    CanteenQrCode("qrCode", "行政-饭堂二维码"),
    CanteenGoods("goods", "行政-食材"),
    CanteenGoodsType("goodsType", "行政-食材类型"),
    CanteenGoodsUnit("goodsUnit", "行政-食材单位"),
    CanteenCostActualUsed("actualUsed", "行政-食材实际用量"),
    CanteenCostExtraGoods("extraGoods", "行政-额外临时食材"),
    CanteenComment("comment", "行政-用餐评价"),
    CanteenMealTime("mealTime", "行政-用餐时间"),
    CanteenDiningManage("diningManage", "行政-报餐管理"),
    CanteenDish("dish", "行政-菜品"),
    CanteenDishType("dishType", "行政-菜品标签"),
    CanteenDishTypeGroup("dishTypeGroup", "行政-菜品标签分类"),
    CanteenGoodsCost("goodsCost", "行政-食材价格"),
    CanteenInboundOutboundRecord("inboundOutboundRecord", "行政-食材出入库"),
    CanteenKitchenWaste("kitchenWaste", "行政-厨余分析"),
    CanteenMarket("market", "行政-市场管理"),
    CanteenMenuGroup("menuGroup", "行政-菜单组"),
    CanteenMenuManage("menuManage", "行政-菜单搭配管理"),
    CanteenMenu("menu", "行政-菜单"),
    CanteenTare("tare", "行政-皮重管理"),
    CanteenWarehouse("warehouse", "行政-仓库管理"),
    CanteenPurchaseRecord("purchaseRecord", "行政-采购计划"),
    CanteenInboundOutboundRecordApprove("inboundOutboundRecordApprove", "行政-出入库修正"),
    Unknown("unknown", "未知业务类型");

    @EnumValue
    private final String type;

    @JsonValue
    private final String typeName;

    LogBusinessTypeEnum(String type, String typeName){
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
    public static LogBusinessTypeEnum create(String type){
        LogBusinessTypeEnum[] enums = LogBusinessTypeEnum.values();
        for (LogBusinessTypeEnum e : enums) {
            if (type.equals(e.getType())){
                return e;
            }
        }
        return Unknown;
    }

}
