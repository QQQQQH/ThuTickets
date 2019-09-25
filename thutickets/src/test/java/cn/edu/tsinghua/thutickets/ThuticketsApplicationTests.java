package cn.edu.tsinghua.thutickets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.tsinghua.thutickets.dao.UserMapper;
import cn.edu.tsinghua.thutickets.entity.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThuticketsApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void contextLoads() {
        User user = new User();
        user.setOpenid("hahaha");
        user.setSession_key("131");
        user.setStatus_key("121");
        userMapper.insert(user);
    }

}
