package cn.gzsunrun.downtime.client;

import cn.gzsunrun.downtime.config.factory.MySSLSocketFactoryBuilder;
import cn.gzsunrun.downtime.entity.DowntimeCheckParam;
import cn.gzsunrun.downtime.entity.DowntimeClientParam;
import cn.gzsunrun.downtime.entity.DowntimeRecord;
import cn.gzsunrun.oars.entity.OarsApiResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;

import java.util.Map;

/**
 * @author eamon
 */
@BaseRequest(
        baseURL = "${sunrunUrl}",
        contentType = "application/json",
        readTimeout = 60000,
        sslProtocol = "TLS",
        connectTimeout = 60000
)
public interface Sunrunclient {

    /**
     * json通用请求
     *
     * @param
     * @return
     */
    @PostRequest(value = "/login", contentType = "application/json"
    )
    @SSLSocketFactoryBuilder(value = MySSLSocketFactoryBuilder.class)
    <T> ForestResponse<OarsApiResponse<String>> login(@JSONBody("username") String msgId, @JSONBody T body, @Header Map<String, Object> headerMap);


    /**
     * json通用请求
     *
     * @param
     * @return
     */
    @PostRequest(value = "/login", contentType = "application/json"
    )
    @SSLSocketFactoryBuilder(value = MySSLSocketFactoryBuilder.class)
    <T> ForestResponse<SunrunApiResponse<SunrunTPage>> login(@JSONBody("username") String userName, @JSONBody("password") String password, @Header Map<String, Object> headerMap);


    /**
     * json通用请求
     *
     * @param
     * @return
     */
    @PostRequest(value = "/api/v11/findGjkByLkFaultRecord", contentType = "application/json"
    )
    @SSLSocketFactoryBuilder(value = MySSLSocketFactoryBuilder.class)
    <T> ForestResponse<SunrunApiResponse<SunrunTPage<DowntimeRecord>>> pageGjkFaultRecord(@Query("token") String token ,@JSONBody DowntimeClientParam body, @Header Map<String, Object> headerMap);


}
