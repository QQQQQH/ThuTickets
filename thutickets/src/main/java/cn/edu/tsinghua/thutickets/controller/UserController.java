package cn.edu.tsinghua.thutickets.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

import cn.edu.tsinghua.thutickets.common.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.edu.tsinghua.thutickets.dao.UserMapper;
import cn.edu.tsinghua.thutickets.entity.User;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public String login(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "rawData", required = false) String rawData,
                        @RequestParam(value = "signature", required = false) String signature,
                        @RequestParam(value = "encrypteData", required = false) String encrypteData,
                        @RequestParam(value = "iv", required = false) String iv){
        System.out.println("code: "+code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wx8516e14eafc4c047");
        param.put("secret", "4222f2e019ff73cb845c3be51c4a5f27");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String res = HttpClientUtil.doGet(url, param);

        JSONObject rawDataJson = JSON.parseObject(rawData);
        JSONObject idJson = JSON.parseObject(res);

        String openid = idJson.getString("openid");
        String session_key = idJson.getString("session_key");

        User user = this.userMapper.selectById(openid);
        String status_key = UUID.randomUUID().toString();
        if(user == null) {
            String nickName = rawDataJson.getString("nickName");
            String gender = rawDataJson.getString("gender");
            String language = rawDataJson.getString("language");
            String province = rawDataJson.getString("province");
            String country = rawDataJson.getString("country");
            String avatarUrl = rawDataJson.getString("avatarUrl");

            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setSession_key(session_key);
            newUser.setStatus_key(status_key);
            newUser.setNick_name(nickName);
            newUser.setGender(Integer.parseInt(gender));
            newUser.setLanguage(language);
            newUser.setProvince(province);
            newUser.setCountry(country);
            newUser.setAvatar_url(avatarUrl);
            newUser.setCreate_time(new Date());
            newUser.setLast_visit_time(new Date());
            this.userMapper.insert(newUser);
        }
        return res;
    }
}
