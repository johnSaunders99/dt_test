package cn.gzsunrun.downtime;

import cn.gzsunrun.oars.tool.extra.spring.util.EnableOarsSpringUtil;
import cn.gzsunrun.tool.message.annotation.SunrunMessageApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 服务启动类
 *
 * @author SimonMai
 */
//取消自动加载数据库
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableOarsSpringUtil
//事务启动
@EnableTransactionManagement
//定时器
@EnableScheduling
@SunrunMessageApi
public class AttendanceApp {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApp.class, args);
        System.out.println("=======spring启动成功======");

    }
}
