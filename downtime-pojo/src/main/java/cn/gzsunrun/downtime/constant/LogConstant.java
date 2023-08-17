package cn.gzsunrun.downtime.constant;


/**
 * 日志常量
 */
public interface LogConstant {
    /**
     * 日志服务类型 考勤
     */
    String USER_LOG_SERVICE_ATTENDANCE="attendance";
    /**
     * 日志服务类型 饭堂
     */
    String USER_LOG_SERVICE_CANTEEN="canteen";

    /***********************************************考勤系统 相关业务常量**************************************************/
    /**
     * 业务，人员管理
     */
    String USER_LOG_BUSINESS_PERSON="person";
    /**
     * 业务，假期管理
     */
    String USER_LOG_BUSINESS_ANNUAL_LEAVE="annualLeave";
    /**
     * 业务，假期类型管理
     */
    String USER_LOG_BUSINESS_ANNUAL_LEAVE_TYPE="annualLeaveType";
    /**
     * 业务，考勤
     */
    String USER_LOG_BUSINESS_ATT_CLOCK="atteClock";
    /**
     * 业务，排班
     */
    String USER_LOG_BUSINESS_SHIFT_USER="shiftUser";
    /**
     * 业务，班次
     */
    String USER_LOG_BUSINESS_SHIFT="shift";
    /**
     * 业务，打卡次数
     */
    String USER_LOG_BUSINESS_ROLE_COUNT="roleCount";
    /**
     * 业务，异动状态
     */
    String USER_LOG_BUSINESS_TRANSFER="transferInfo";
    /**
     * 业务，打卡范围
     */
    String USER_LOG_BUSINESS_DEVICE_SCOPE="deviceScope";
    /**
     * 业务，节假日
     */
    String USER_LOG_BUSINESS_HOLIADAY="holiday";
    /**
     * 业务，大类岗位管理
     */
    String USER_LOG_BUSINESS_MANAGE="ManagePositionRel";
    /**
     * 业务，班次规则
     */
    String USER_LOG_BUSINESS_SHIFT_GOURP_REL="shiftGroupRel";
    /**
     * 业务，欠班
     */
    String USER_LOG_BUSINESS_SHIFT_LACK="shiftLack";
    /**
     * 业务，加班
     */
    String USER_LOG_BUSINESS_SHIFT_WORK_OVER_TIME="shiftWorkOver";
    /**
     * 业务，请假
     */
    String USER_LOG_BUSINESS_SHIFT_LEAVE="shiftLeave";
    /**
     * 业务，饭堂二维码
     */
    String USER_LOG_BUSINESS_QR_CODE="qrCode";

    /**
     * 考勤设备用户同步状态
     */
    //同步失败
    int SYNC_PERSON_DEVICE_STATUS_SAVE_FIAL=0;
    //同步成功
    int SYNC_PERSON_DEVICE_STATUS_SAVE_SUC=1;
    //删除失败
    int SYNC_PERSON_DEVICE_STATUS_DEL_FIAL=2;
    //删除成功
    int SYNC_PERSON_DEVICE_STATUS_DEL_SUC=3;


    /***********************************************行政系统 相关业务常量**************************************************/

    String USER_LOG_CANTEEN_GOODS = "goods";
    String USER_LOG_CANTEEN_GOODS_TYPE = "goodsType";
    String USER_LOG_CANTEEN_GOODS_UNIT = "goodsUnit";



    /**
     * 操作：新增
     */
    String USER_LOG_OPERATE_INSERT="insert";
    /**
     * 操作：修改
     */
    String USER_LOG_OPERATE_UPDATE="update";
    /**
     * 操作：删除
     */
    String USER_LOG_OPERATE_DELETE="delete";
    /**
     * 操作：使用
     */
    String USER_LOG_OPERATE_USED="used";
    /**
     * 操作：授权
     */
    String USER_LOG_OPERATE_PERMISSION="permission";
    /**
     * 同步
     */
    String USER_LOG_OPERATE_SYNC="sync";
    /**
     * 导入
     */
    String USER_LOG_OPERATE_IMPORT="import";




    /**
     * 权限 角色分配用户
     */
    String USER_LOG_BUSINESS_PERMISSION_ROLE_USER="perRoleUser";
    /**
     * 权限 角色分配资源
     */
    String USER_LOG_BUSINESS_PERMISSION_ROLE_RESOURCE="perRoleResource";


    /**
     * 储存的filebaseId
     */
    String FILE_BASE_IMPORT_ID = "import_fileBase_id";
}
