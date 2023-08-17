//package cn.gzsunrun.downtime.config.datasource.config.flyway;
//
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.flywaydb.core.Flyway;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Map;
//
///**
// * @author john saunders
// * @description:
// * @date: 2023/4/26
// * @time: 17:18
// */
//@Configuration
//@Slf4j
//public class FlywayConfig {
//    @Autowired
//    private DataSource dataSource;
//
//    private static final String SQL_LOCATION = "classpath:db/";
//
//    @Bean
//    public void migrate() {
//        log.warn("调用数据库生成工具");
//        if (dataSource instanceof AtomikosDataSourceBean){
//            AtomikosDataSourceBean ds = (AtomikosDataSourceBean) dataSource;
//            log.warn("正在执行多数据源生成数据库文件 " + ds);
//            Flyway flyway = Flyway.configure()
//                    .dataSource(ds)
//                    .locations(SQL_LOCATION+ds.getUniqueResourceName())
//                    .baselineOnMigrate(true)
//                    .load();
//            flyway.migrate();
//        }else {
//            DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//            Map<String, DataSource> dataSources = ds.getDataSources();
//            dataSources.forEach((k, v) -> {
//                try {
//                    log.warn("正在执行多数据源生成数据库文件 " + k);
//                    Flyway flyway = Flyway.configure()
//                            .dataSource(v)
//                            .locations(SQL_LOCATION+k)
//                            .baselineOnMigrate(true)
//                            .load();
//                    flyway.migrate();
//                }catch (Exception e){
//                    log.error("执行数据库文件错误:"+ e.getMessage());
//                }
//            });
//        }
//
//
//    }
//
//}
