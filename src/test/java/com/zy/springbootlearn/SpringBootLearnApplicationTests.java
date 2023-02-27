package com.zy.springbootlearn;

import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootLearnApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {

    }

    //测试逻辑删除
    @Test
    void testDeleted(){
        userMapper.deleteById(3);
    }

    //测试添加数据
    @Test
    void testInsert(){
        User user = new User();
        user.setUName("Lester");
        user.setUPassword("12345");
        user.setUNickname("白银用户");
        user.setUEmail("Jaylin28@gmail.com");
        user.setUPhone("19787714137");
        user.setUAddress("Lake Florence 510 Gracie Springs");
        userMapper.insert(user);
    }

    @Test
    void testInsertMany(){
        for (int i=0;i<20;i++){
            User user = new User();
            user.setUName("赵"+i);
            user.setUPassword("12345");
            user.setUNickname("白银用户");
            user.setUEmail("Jaylin28@gmail.com");
            user.setUPhone("19787714137");
            user.setUAddress("Lake Florence 510 Gracie Springs");
            userMapper.insert(user);
        }
    }

}
