package cn.gzsunrun.downtime.constant;

/**
 * 流程相关的常量
 */
public interface ProcessConstant {
    String PROCESS_STATUS_GROUP="group";
    String PROCESS_STATUS_DEPT="dept";
    String PROCESS_STATUS_FACT="fact";


    String PROCESS_STATUS_FINISH="finish";
    String PROCESS_STATUS_HANDING="handing";

    //汇总
    String REPORT_TYPE_SUMMARY="summary";
    //明细
    String REPORT_TYPE_SHIFT="shift";



    String TASK_STATUS_HANDING="handing";
    String TASK_STATUS_AGREE="agree";
    String TASK_STATUS_UNAGREE="unagree";
    String TASK_STATUS_REJECT="reject";


    /**
     * 按钮状态
     */
    String REPORT_PROCESS_BTN_HIDDEN="hidden";
    String REPORT_PROCESS_BTN_SEND="send";
    String REPORT_PROCESS_BTN_READ="read";
}
