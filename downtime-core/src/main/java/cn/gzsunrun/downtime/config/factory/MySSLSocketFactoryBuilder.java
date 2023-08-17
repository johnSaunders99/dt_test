package cn.gzsunrun.downtime.config.factory;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.ssl.SSLSocketFactoryBuilder;
import com.dtflys.forest.ssl.TrustAllManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

/**
 * 自定义 SSLSocketFactory 构造器
 */
public class MySSLSocketFactoryBuilder implements SSLSocketFactoryBuilder {

    /**
     * 获取SSL Socket Factory
     */
    @Override
    public SSLSocketFactory getSSLSocketFactory(ForestRequest request, String protocol) throws Exception {
        SSLContext instance = SSLContext.getInstance(protocol);
        instance.init(null,
                new TrustManager[]{new TrustAllManager()},
                new SecureRandom());
        return instance.getSocketFactory();
    }
}
