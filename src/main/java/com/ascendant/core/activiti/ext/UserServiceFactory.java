package com.ascendant.core.activiti.ext;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

import java.util.List;
import java.util.Map;

/**
 * 自定义用户服务对象工厂
 * @author qiaolin
 * @version 2018/10/22
 **/
public class UserServiceFactory extends UserEntityManager implements SessionFactory{

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return this;
    }


    @Override
    public User findUserById(String userId) {
        // TODO 这里需要去用户表查询数据并转换成Activiti 的 User对象

        return super.findUserById(userId);
    }


    @Override
    public List<Group> findGroupsByUser(String userId) {
        // TODO 这里需要查询该用户Id的角色有哪些 ，并转换

        return super.findGroupsByUser(userId);
    }




    @Override
    public void deleteUser(String userId) {
        throw new RuntimeException("not implement method.");
    }

    public void insertUser(User user) {
        throw new RuntimeException("not implement method.");
    }

    public void updateUser(UserEntity updatedUser) {
        throw new RuntimeException("not implement method.");
    }

    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }

    public UserQuery createNewUserQuery() {
        throw new RuntimeException("not implement method.");
    }

    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new RuntimeException("not implement method.");
    }

    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new RuntimeException("not implement method.");
    }

    public Boolean checkPassword(String userId, String password) {
        throw new RuntimeException("not implement method.");
    }

    public List<User> findPotentialStarterUsers(String proceDefId) {
        throw new RuntimeException("not implement method.");
    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method.");
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }

}
