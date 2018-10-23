package com.ascendant.core.config;

import com.ascendant.common.util.IdGen;
import com.ascendant.core.activiti.ext.ActivitiExtendProperties;
import com.ascendant.core.activiti.ext.GroupServiceFactory;
import com.ascendant.core.activiti.ext.UserServiceFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activiti 扩展配置
 * @author qiaolin
 * @version 2018/10/22
 **/

@Configuration
@ComponentScan({"org.activiti.rest.diagram","org.activiti.rest.editor"})
@EnableConfigurationProperties(ActivitiExtendProperties.class)
public class ActivitiConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ActivitiExtendProperties properties;

    @Bean
    public ProcessEngineConfigurationConfigurer processEngineConfigurationConfigurer(IdGen idGen){
        ProcessEngineConfigurationConfigurer configurer = new ProcessEngineConfigurationConfigurer() {

            @Override
            public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
                processEngineConfiguration.setActivityFontName(properties.getActivityFontName());
                processEngineConfiguration.setLabelFontName(properties.getLabelFontName());
                processEngineConfiguration.setCustomSessionFactories(getCustomSessionFactories());
                processEngineConfiguration.setBeans(getBeans());
                processEngineConfiguration.setIdGenerator(idGen);
                processEngineConfiguration.setDatabaseSchemaUpdate(properties.getDatabaseSchemaUpdate());

            }

        };

        return configurer;
    }

    /**
     *  用户自定义权限工厂
     * @return
     */
    private List<SessionFactory> getCustomSessionFactories(){
        List<SessionFactory> sessionFactoryList = new ArrayList<>();
        // TODO 等用户权限做好了再开启这两个
        //sessionFactoryList.add(new GroupServiceFactory());
        //sessionFactoryList.add(new UserServiceFactory());
        return sessionFactoryList;
    }

    /**
     *  需要spring 代理的bean 请在该方法中加入
     * @return
     */
    private Map<Object, Object> getBeans(){
        Map<Object, Object> beans = new HashMap<>();

        return beans;
    }



}
