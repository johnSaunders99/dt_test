package cn.gzsunrun.downtime.constant;

public interface RedisConstant {
    //同步进度
    String DEIVCE_SYNC_STATUS_USER_KEY="syucUserStatus";
    //用户的设备状态
    String USER_SYNC_STATUS_DEIVCE_KEY="userSyncStatusInDevice";

    String ATT_TRASFER_INFO_LIST_KEY = "attTranferInfoList";

    String ATT_TO_CANTEEN_LAST_TIME = "attToCanteenLastTime";

    /**
     * 转发消息所用  个人考勤
     */
    String FORWARD_ATTENDANCE_PERSONAL_DAILY_CHECK_IN_KEY = "attPersonalDailyTime";
    /**
     * 转发消息所用  个人休假加班处理记录
     */
    String FORWARD_ATTENDANCE_PERSONAL_SHIFT_CHECK_IN_KEY = "attPersonalShift";
    /**
     * 转发消息所用  个人考勤月度结余情况
     */
    String FORWARD_ATTENDANCE_PERSONAL_MONTH_CHECK_IN_KEY = "attPersonalMonthTime";
    /**
     * 转发消息所用  个人员工到岗情况
     */
    String FORWARD_ATTENDANCE_DEPART_MONTH_CHECK_IN_KEY = "attDepartMonthTime";
    /**
     * 设备在线的缓存
     */
    String ATT_DEVICE_NETTY_ONLINE = "deviceNettOnlineIp";
}
