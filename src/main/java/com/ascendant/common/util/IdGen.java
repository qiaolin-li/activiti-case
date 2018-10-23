package com.ascendant.common.util;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 * uuid生成工具类
 * @author qiaolin
 * @version 2018/10/22
 **/

@Component
@Lazy(false)
public class IdGen implements IdGenerator{


    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    @Override
    public String getNextId() {
        return uuid();
    }



}
