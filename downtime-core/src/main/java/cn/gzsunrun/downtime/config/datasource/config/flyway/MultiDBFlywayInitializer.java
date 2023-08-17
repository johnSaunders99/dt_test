//package cn.gzsunrun.downtime.config.datasource.config.flyway;
//
///**
// * 参考的 https://gitee.com/Wilson-He/spring-boot-series/tree/master/spring-boot-flyway-demo
// * 感谢这位大哥
// * @author john saunders
// * @description:
// * @date: 2023/4/26
// * @time: 17:04
// */
//import cn.gzsunrun.downtime.config.datasource.config.flyway.properties.MultiFlywayProperties;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.util.JdbcUtils;
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import com.baomidou.dynamic.datasource.ds.ItemDataSource;
//import com.mysql.cj.jdbc.MysqlXADataSource;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//import javax.sql.XADataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Map;
//
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class MultiDBFlywayInitializer {
//    @Autowired
//    private MultiFlywayProperties flywayProperties;
//    private static final String IGNORE_EXCEPTION = "already exists";
//    private static final String SCHEMA_PATTERN = "{schema}";
//    private final DataSource dataSource;
//
//    @PostConstruct
//    public void init() {
//        try {
//            init((ItemDataSource)dataSource);
//        }catch (Exception e){
//            log.warn(e.getMessage(),e);
//            AtomikosDataSourceBean ds = (AtomikosDataSourceBean) dataSource;
//            try {
//                initXa(ds);
//            } catch (SQLException er) {
//                log.error("数据库初始化失败, 失败原因: "+er.getMessage());
//            }
//        }
//    }
//
//    public void initXa(AtomikosDataSourceBean ds) throws SQLException {
//        MysqlXADataSource realDataSource = (MysqlXADataSource) ds.getXaDataSource();
//        String url = realDataSource.getUrl();
//        String username = realDataSource.getUser();
//        String password = realDataSource.getPassword();
//
//        String driverClassName = JdbcUtils.getDriverClassName(url);
////        String driverClassName = "com.mysql.cj.jdbc.Driver";
//        if (flywayProperties.getInitSqls().isEmpty()) {
//            return;
//        }
////        realDataSource.getDatabaseName();
////        String schema =null;
////        if (url.contains("?")){
////         schema = url.substring()
////        };
//        int lastSplitIndex = 0;
//        int i = url.indexOf("?");
//        if (url.contains("?")){
//            String test = url.substring(0, i);
//            lastSplitIndex = test.lastIndexOf('/') + 1;
//        }else {
//            lastSplitIndex = url.lastIndexOf('/') + 1;
//        };
//        String schema = url.substring(lastSplitIndex, i);
//        String addressUrl = url.substring(0, lastSplitIndex);
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(addressUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driverClassName);
//        Connection connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        try {
//            for (String sql : flywayProperties.getInitSqls()) {
//                statement.executeUpdate(sql.contains(SCHEMA_PATTERN) ? sql.replace(SCHEMA_PATTERN, schema) : sql);
//            }
//        } catch (Exception e) {
//            // 异常信息包含 'already exists' 则忽略，否则抛出
//            if (!e.getLocalizedMessage().contains(IGNORE_EXCEPTION)) {
//                throw e;
//            }
//        } finally {
//            statement.close();
//            connection.close();
//            dataSource.close();
//        }
//        log.info(schema, "数据库{}初始化完成 ");
//    }
//
//
//    public void init(ItemDataSource ds) throws SQLException {
//        DruidDataSource realDataSource = (DruidDataSource) ds.getRealDataSource();
//        String url = realDataSource.getRawJdbcUrl();
//        String username = realDataSource.getUsername();
//        String password = realDataSource.getPassword();
//        String driverClassName = realDataSource.getDriverClassName();
//
//        if (flywayProperties.getInitSqls().isEmpty()) {
//            return;
//        }
//        int lastSplitIndex = url.lastIndexOf('/') + 1;
//        String schema = url.contains("?") ? url.substring(lastSplitIndex, url.indexOf("?")) : url.substring(lastSplitIndex);
//        String addressUrl = url.substring(0, lastSplitIndex);
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(addressUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driverClassName);
//        Connection connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        try {
//            for (String sql : flywayProperties.getInitSqls()) {
//                statement.executeUpdate(sql.contains(SCHEMA_PATTERN) ? sql.replace(SCHEMA_PATTERN, schema) : sql);
//            }
//        } catch (Exception e) {
//            // 异常信息包含 'already exists' 则忽略，否则抛出
//            if (!e.getLocalizedMessage().contains(IGNORE_EXCEPTION)) {
//                throw e;
//            }
//        } finally {
//            statement.close();
//            connection.close();
//            dataSource.close();
//        }
//        log.info(schema, "数据库{}初始化完成 ");
//    }
//}
