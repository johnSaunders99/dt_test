package cn.gzsunrun.downtime.schedule;

import cn.gzsunrun.downtime.client.SunrunService;
import cn.gzsunrun.downtime.client.Sunrunclient;
import cn.gzsunrun.downtime.service.ISysConfigService;
import com.dtflys.forest.Forest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eamon
 */

@Configuration
@ConditionalOnBean(ISysConfigService.class)
@DependsOn({"downtimeDatasource"})
@RequiredArgsConstructor
public class ClientAutoConfig {

    private final ISysConfigService configService;

    /**
     * @return
     */
    @Bean
    public SunrunService sunrunService() {
        Sunrunclient client = Forest.client(Sunrunclient.class);
//        ForestConfiguration configuration = Forest.config();
//        String url = configService.selectConfigByKey(ConfigConstants.AIEURL);
        Map<String, Object> headers = new HashMap<>();
//        configuration.setVariableValue("senseAIEUrl",url);
//        headers.put("Host", IpUtils.getHostIp());
        return new SunrunService(configService, client, headers);
    }
}
