package cn.gzsunrun.downtime.constant;

/**
 * 用户常量信息
 *
 * @author ruoyi
 */
public class UserConstants {
    private UserConstants() {
        throw new RuntimeException("constant class can not be constructing into instance!");
    }

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 是否菜单外链（是）
     */
    public static final String YES_FRAME = "0";

    /**
     * 是否菜单外链（否）
     */
    public static final String NO_FRAME = "1";

    /**
     * 菜单类型（目录）
     */
    public static final String TYPE_DIR = "M";

    /**
     * 菜单类型（菜单）
     */
    public static final String TYPE_MENU = "C";

    /**
     * 菜单类型（按钮）
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * Layout组件标识
     */
    public static final String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public static final String PARENT_VIEW = "ParentView";

    /**
     * 校验返回结果码
     */
    public static final String UNIQUE = "0";
    public static final String NOT_UNIQUE = "1";

    public static final String WEBSOCKET_BUSINESS_TAG = "_business";
}
