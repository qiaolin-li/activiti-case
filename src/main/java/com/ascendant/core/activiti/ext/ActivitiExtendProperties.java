package com.ascendant.core.activiti.ext;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *
 *  因为Activiti 的配置类所配置的内容十分有限，所以我自己写了一个扩展类，来扩展
 *  其他可能会用道德配置
 * @author qiaolin
 * @version 2018/10/22
 **/

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.activiti.ext")
public class ActivitiExtendProperties {


    // 流程定义缓存数量
    private int processDefinitionCacheLimit;

    // 是否自动创建数据库
    private String databaseSchemaUpdate = "false";

    // 流程图字体配置
    private String activityFontName = "宋体";
    private String labelFontName = "宋体";

}
