package com.ascendant.core.activiti.ext;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

import java.util.List;

/**
 * 自定义组服务对象工厂
 * @author qiaolin
 * @version 2018/10/22
 **/
public class GroupServiceFactory extends GroupEntityManager implements SessionFactory{

    // 返回原始的组管理类型
    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    // 返回自定义的组管理对象
    @Override
    public Session openSession() {
        return this;
    }

    /**
     *  根据用户Id查询用户得角色并转换成 Activiti的 Group对象
     * @param userId
     * @return
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        // TODO 这里需要返回用户的角色
        return super.findGroupsByUser(userId);
    }

}
