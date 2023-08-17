package cn.gzsunrun.downtime.client;

/**
 * @author john saunders
 * @description:
 * @date: 2023/8/17
 * @time: 10:31
 */

import cn.gzsunrun.oars.exception.OarsException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(
        description = "响应报文格式"
)
public class SunrunApiResponse<T> {
    private static final Logger log = LoggerFactory.getLogger(SunrunApiResponse.class);
    @ApiModelProperty("返回状态码, 10000则请求成功")
    private int code;
    @ApiModelProperty("请求成功时，返回数据")
    private T content;
    @ApiModelProperty("请求失败时，返回错误信息")
    private String message;
    @ApiModelProperty("请求失败时，返回错误信息")
    private String detail;
    private String requestId;
    private String status;
    private Boolean messageState;

    public SunrunApiResponse() {
        this.code = 10000;
    }

    public SunrunApiResponse(T content) {
        this.code = 10000;
        this.content = content;
    }

    public SunrunApiResponse(int errCode, String errMessage) {
        this.code = errCode;
        this.message = errMessage;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == 10000 || this.code == 200;
    }

    public static <T> SunrunApiResponse<T> success() {
        return success(null);
    }

    public static <T> SunrunApiResponse<T> success(T content) {
        return success(content, false);
    }

    public static <T> SunrunApiResponse<T> success(T content, String message) {
        return success(content, message, false);
    }

    public static <T> SunrunApiResponse<T> success(T content, boolean messageState) {
        return success(content, "操作成功", messageState);
    }

    public static <T> SunrunApiResponse<T> success(String message, boolean messageState) {
        return success(null, message, messageState);
    }

    public static <T> SunrunApiResponse<T> fail(String message) {
        SunrunApiResponse<T> res = new SunrunApiResponse();
        res.setCode(-1);
        res.setMessage(message);
        return res;
    }

    public static <T> SunrunApiResponse<T> fail(int code, String message) {
        SunrunApiResponse<T> res = new SunrunApiResponse();
        res.setCode(code);
        res.setMessage(message);
        return res;
    }

    public static <T> SunrunApiResponse<T> success(T content, String message, boolean messageState) {
        SunrunApiResponse<T> res = new SunrunApiResponse();
        res.setCode(10000);
        res.setcontent(content);
        res.setMessage(message);
        res.setMessageState(messageState);
        return res;
    }

    public static <T> SunrunApiResponse<T> fail(int code, String message, String detail) {
        SunrunApiResponse<T> res = new SunrunApiResponse();
        res.setCode(code);
        res.setMessage(message);
        res.setDetail(detail);
        return res;
    }

    public void assertFailure() {
        if (!this.isSuccess()) {
            throw new OarsException(this.code, this.message);
        }
    }

    public int getCode() {
        return this.code;
    }

    public T getContent() {
        return this.content;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return this.status;
    }

    public Boolean getMessageState() {
        return this.messageState;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setcontent(final T content) {
        this.content = content;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setMessageState(final Boolean messageState) {
        this.messageState = messageState;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SunrunApiResponse)) {
            return false;
        } else {
            SunrunApiResponse<?> other = (SunrunApiResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$messageState = this.getMessageState();
                Object other$messageState = other.getMessageState();
                if (this$messageState == null) {
                    if (other$messageState != null) {
                        return false;
                    }
                } else if (!this$messageState.equals(other$messageState)) {
                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                label71: {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label71;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label71;
                    }

                    return false;
                }

                label64: {
                    Object this$detail = this.getDetail();
                    Object other$detail = other.getDetail();
                    if (this$detail == null) {
                        if (other$detail == null) {
                            break label64;
                        }
                    } else if (this$detail.equals(other$detail)) {
                        break label64;
                    }

                    return false;
                }

                Object this$requestId = this.getRequestId();
                Object other$requestId = other.getRequestId();
                if (this$requestId == null) {
                    if (other$requestId != null) {
                        return false;
                    }
                } else if (!this$requestId.equals(other$requestId)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SunrunApiResponse;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getCode();
        Object $messageState = this.getMessageState();
        result = result * 59 + ($messageState == null ? 43 : $messageState.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $detail = this.getDetail();
        result = result * 59 + ($detail == null ? 43 : $detail.hashCode());
        Object $requestId = this.getRequestId();
        result = result * 59 + ($requestId == null ? 43 : $requestId.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SunrunApiResponse(code=" + this.getCode() + ", content=" + this.getContent() + ", message=" + this.getMessage() + ", detail=" + this.getDetail() + ", requestId=" + this.getRequestId() + ", status=" + this.getStatus() + ", messageState=" + this.getMessageState() + ")";
    }
}

