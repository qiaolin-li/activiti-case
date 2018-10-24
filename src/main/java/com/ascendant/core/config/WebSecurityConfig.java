package com.ascendant.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 *  由于发送Put请求啥的好像要权限认证，
 *  先设置某些url不认证
 * @author qiaolin
 * @version 2018/10/23
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf保护功能（跨域访问）
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/model/**").permitAll();//访问API下无需登录认证权限
    }

}
