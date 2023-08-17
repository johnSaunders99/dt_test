package cn.gzsunrun.downtime.util;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.http.webservice.SoapUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class SoapUtils {



    public static void main(String[] args) {
        SoapClient client = SoapUtil.createClient("http://192.168.10.93:9999/BDBDCDJ/aaa?wsdl")
                .setMethod("test", "http://impl.service.cn.guangzou.mjl/")
                .setParam("name", "jack");

        String result = client.send(true);
        System.out.println(result);

        Map<String, Object> map = XmlUtil.xmlToMap(result);

        System.out.println(JSONObject.toJSONString(map));

    }

}
