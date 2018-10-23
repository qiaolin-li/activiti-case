package com.ascendant.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 核心配置
 * @author qiaolin
 * @version 2018/10/22
 **/

@Configuration
public class CoreConfiguration {



    /**
     *  阿里巴巴 Druid 数据源
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }


    /**
     *  事务管理器
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }



    /**
     *  druidServlet 管理页
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(statViewServlet, "/druid/*");

        Map<String, String> params = new HashMap<>();
        params.put("loginUsername", "admin");
        params.put("loginPassword", "123456");
        servletRegistrationBean.setInitParameters(params);
        return servletRegistrationBean;
    }

    /**
     *  druidServlet 拦截器
     * @return
     */
    @Bean
    public FilterRegistrationBean druidFilter(){
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(webStatFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> params = new HashMap<>();
        params.put("exclusions", "*.js,*.css,/druid/");
        filterRegistrationBean.setInitParameters(params);
        return filterRegistrationBean;
    }
}
