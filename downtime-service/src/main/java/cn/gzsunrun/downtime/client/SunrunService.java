package cn.gzsunrun.downtime.client;

import cn.gzsunrun.downtime.constant.SysConst;
import cn.gzsunrun.downtime.entity.DowntimeCheckParam;
import cn.gzsunrun.downtime.entity.DowntimeClientParam;
import cn.gzsunrun.downtime.entity.DowntimeRecord;
import cn.gzsunrun.downtime.service.ISysConfigService;
import cn.gzsunrun.oars.dbutils.page.OarsPageUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtflys.forest.annotation.BindingVar;
import com.dtflys.forest.http.ForestResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * sunrun服务
 */
@Slf4j
@Getter
public class SunrunService {

    private Map<String, Object> headers;
    private Sunrunclient forestClient;
    private ISysConfigService configService;
    private Long lastTimeStamp;

    public SunrunService(ISysConfigService configService, Sunrunclient client, Map<String, Object> headers) {
        this.forestClient = client;
        this.headers = headers;
        this.configService = configService;
        this.lastTimeStamp = 0L;
    }


    @BindingVar("sunrunUrl")
    public String getUrl() {
        String url = configService.selectConfigByKey(SysConst.sunrunUrl);
        return url;
    }

    /**
     * 获取用户的token
     *
     * @return
     */
    private Map<String, Object> acRequestHeaders(Map<String, Object> headers) {
        long current = DateUtil.current();
        //30分钟重新登录
        if (current > lastTimeStamp + (30 * 60 * 1000) || headers.get("sessionid") == null) {
            log.info("尝试登录");
            try {
                Map<String, Object> login = login(headers);
                return login;
            } catch (Throwable e) {
                throw new RuntimeException("尝试sunrun登录失败!" + e.getMessage(), e);
            }
        }
        return headers;
    }


    private Map<String, Object> login(Map<String, Object> headers) throws Throwable {
        String acc = configService.selectConfigByKey(SysConst.sunrunUserName);
        String passwd = configService.selectConfigByKey(SysConst.sunrunPasswd);
        this.lastTimeStamp = DateUtil.current();
        //计算头部content-length
//        headers.put("Content-Length", JSONUtil.toJsonStr(accountJson).length());
        ForestResponse<SunrunApiResponse<SunrunTPage>> res = forestClient.login(acc, passwd, headers);
        if (!res.isSuccess()) {
            throw res.getException();
        }
        SunrunApiResponse<SunrunTPage> response = res.getResult();
        log.info("login response:" + response.toString());
        if (response.isSuccess()) {
            SunrunTPage data = response.getContent();
            String token = data.getToken();
            //重置头部信息
            headers.put("sessionid", data);
            headers.put("Cookie", "session_str=" + data);
            headers.put("token", token);
            this.headers = headers;
        } else {
            log.error(response.toString());
            throw new RuntimeException("sunrun login error:" + response.getMessage());
        }
        return headers;
    }


    /**
     * 获取sunrun内的设备名称
     *
     * @return
     */
    public SunrunTPage<DowntimeRecord> getPageData(DowntimeCheckParam param, Long pageSize, Long current, Integer retryTime) {
        if (retryTime == null){
            retryTime = 0;
        }
        DowntimeClientParam downtimeClientParam = new DowntimeClientParam();
        downtimeClientParam.setCname(param.getClassName());
        downtimeClientParam.setCurrentPage(current);
        downtimeClientParam.setEquid(param.getEquId());
        downtimeClientParam.setSys(param.getSysName());
        downtimeClientParam.setPageSize(pageSize);
        downtimeClientParam.setStime(DateUtil.date(param.getTimestampStart()).toString(DatePattern.NORM_DATETIME_FORMAT));
        downtimeClientParam.setEtime(DateUtil.date(param.getTimestampEnd()).toString(DatePattern.NORM_DATETIME_FORMAT));
        Map<String, Object> headers = acRequestHeaders(getHeaders());
        String token = "";
        if (headers.containsKey("token")){
            token = headers.get("token").toString();
        }
        ForestResponse<SunrunApiResponse<SunrunTPage<DowntimeRecord>>> sunrunApiResponseForestResponse = forestClient.pageGjkFaultRecord(token, downtimeClientParam, headers);
        if (!sunrunApiResponseForestResponse.isSuccess()){
            if (retryTime> 5){
                if (sunrunApiResponseForestResponse.getException()!=null){
                    throw new RuntimeException(sunrunApiResponseForestResponse.getException());
                }             else if ( sunrunApiResponseForestResponse.getResult()!=null && sunrunApiResponseForestResponse.getResult().getMessage()!=null){
                    throw new RuntimeException(sunrunApiResponseForestResponse.getResult().getMessage());
                }
            }else {
                int i = retryTime + 1;
                return getPageData(param, pageSize, current, i);
            }
        }else {
            SunrunApiResponse<SunrunTPage<DowntimeRecord>> result = sunrunApiResponseForestResponse.getResult();
            if (result.getCode() == 404){
                return null;
            }
            if (result.isSuccess()){
                return result.getContent();
            }else if (retryTime> 5){
                if (sunrunApiResponseForestResponse.getException()!=null){
                    throw new RuntimeException(sunrunApiResponseForestResponse.getException());
                }             else if ( sunrunApiResponseForestResponse.getResult()!=null && sunrunApiResponseForestResponse.getResult().getMessage()!=null){
                    throw new RuntimeException(sunrunApiResponseForestResponse.getResult().getMessage());
                }
            }else {
                int i = retryTime + 1;
                return getPageData(param, pageSize, current, i);
            }
        }
        return null;
    }
}
