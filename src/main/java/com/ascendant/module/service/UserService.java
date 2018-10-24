package com.ascendant.module.service;

import com.ascendant.core.service.BaseServiceImpl;
import com.ascendant.module.entity.User;
import com.ascendant.module.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * 用户service
 * @author qiaolin
 * @version 2018/10/24
 **/

@Service
public class UserService extends BaseServiceImpl<User, UserMapper> {

}
