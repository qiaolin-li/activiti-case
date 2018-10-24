package com.ascendant.module.mapper.test;

import com.ascendant.common.util.IdGen;
import com.ascendant.module.entity.User;
import com.ascendant.module.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author qiaolin
 * @version 2018/10/24
 **/


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setId(IdGen.uuid());
        user.setAge(18);
        user.setEmail("992004863@qq.com");
        user.setName("巧林");
        userMapper.insert(user);
    }

    @Test
    public void update(){
        User user = new User();
        user.setId("2");
        user.setAge(19);
        user.setEmail("110@qq.com");
        user.setName("肉丝");
        userMapper.update(user);
    }

    @Test
    public void delete(){
        userMapper.delete("1");
    }

    @Test
    public void deleteById(){
        User user = new User();
        user.setId("3");
        userMapper.delete(user);

    }

    @Test
    public void   findList(){
        PageHelper.startPage(1, 3);
        List<User> list = userMapper.findList(null);
        log.info("无条件 ------ start");
        list.forEach(l ->{
            log.info(l.toString());
        });
        log.info("无条件 ------ end");

        User user = new User();
        user.setAge(19);
        list = userMapper.findList(user);
        log.info("age=19 条件 ------ start");
        list.forEach(l ->{
            log.info(l.toString());
        });
        log.info("age=19 条件 ------ end");

        user.setName("巧林");

        list = userMapper.findList(user);
        log.info("age=19 and name=巧林 条件 ------ start");
        list.forEach(l ->{
            log.info(l.toString());
        });
        log.info("age=19 and name=巧林 条件 ------ end");

    }

    @Test
    public void   findById(){
        User byId = userMapper.findById("2");
        System.out.println(byId);
    }


    @Test
    public void findById2(){
        User user = new User();
        user.setId("2");
        userMapper.findById(user);
    }


}