package cn.gzsunrun.downtime.config.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"cn.gzsunrun.downtime.mapper"},
        sqlSessionTemplateRef = "deviceSessionTemplate")
@Slf4j
public class MybatisPlusConfigDowntime {
    @Autowired
    private Environment env;

    private static final String SQL_LOCATION = "classpath:db/";
    @Bean
    @Primary
    public DataSource downtimeDatasource() {
        //数据库链接
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(env.getProperty("spring.dynamic.datasource.downtime.jdbc-url"));
        mysqlXADataSource.setUser(env.getProperty("spring.dynamic.datasource.downtime.username"));
        mysqlXADataSource.setPassword(env.getProperty("spring.dynamic.datasource.downtime.password"));
        //事务管理
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setXaDataSource(mysqlXADataSource);
        dataSource.setTestQuery("SELECT 1");
        dataSource.setMaxIdleTime(30);
        dataSource.setBorrowConnectionTimeout(30);
        dataSource.setUniqueResourceName("downtime");
        log.warn("正在执行多数据源生成数据库文件 " + dataSource);
        Boolean baselinemigrate = Boolean.valueOf(env.getProperty("spring.flyway.baseline-on-migrate"));
        Boolean enable = Boolean.valueOf(env.getProperty("spring.dynamic.datasource.downtime.flyway"));
        if (enable){
            Flyway flyway = Flyway.configure()
                    .dataSource(mysqlXADataSource)
                    .locations(SQL_LOCATION+dataSource.getUniqueResourceName())
                    .baselineOnMigrate(baselinemigrate)
                    .createSchemas(Boolean.TRUE)
                    .load();
            flyway.migrate();
        }
        return dataSource;
    }

    @Bean("deviceTemplate")
    public JdbcTemplate primaryTemplate(){
        return new JdbcTemplate(downtimeDatasource());
    }

    /**
     * 分页插件
     */
    @Bean(name = "paginationInterceptorDevice")
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Primary
    @Bean(name = "sqlSessionFactoryDevice")
    public SqlSessionFactory sqlSessionFactoryDevice() throws Exception {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(downtimeDatasource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        mybatisPlus.setMapperLocations(resolver.getResources("classpath*:/mapper/device/*.xml"));
        mybatisPlus.setPlugins(new MybatisPlusInterceptor[]{
                paginationInterceptor()
        });
        return mybatisPlus.getObject();
    }
    @Primary
    @Bean(name = "deviceSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactoryDevice") SqlSessionFactory sqlSessionFactoryDevice) {
        // 配置Session模板
        return new SqlSessionTemplate(sqlSessionFactoryDevice);
    }

}
