package cn.gzsunrun.downtime.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class SysConst {
    private SysConst() {
        throw new RuntimeException("constant class can not be constructing into instance!");
    }

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     *
     */
    public static final String UTF8_LOW = "utf-8";
    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * UTC协调世界时
     */
    public static final String UTC = "UTC";
    /**
     * GMT+北京时
     */
    public static final String BEIJING_TIMEZONE = "GMT+8";
    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 资源映射路径 前缀
     */
    public static final String REDIS_LOCK_KEY = "redis_lock:";


    public static final String sunrunUserName = "sys.sunrun.username";

    public static final String sunrunPasswd = "sys.sunrun.password";

    public static final String sunrunUrl = "sys.sunrun.url";
}
