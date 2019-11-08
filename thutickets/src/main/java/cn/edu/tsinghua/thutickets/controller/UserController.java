package cn.edu.tsinghua.thutickets.controller;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.edu.tsinghua.thutickets.common.HttpClientUtil;
import cn.edu.tsinghua.thutickets.common.Result;
import cn.edu.tsinghua.thutickets.dao.UserMapper;
import cn.edu.tsinghua.thutickets.entity.User;
import org.springframework.web.client.RestTemplate;


@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "rawData", required = false) String rawData,
                        @RequestParam(value = "signature", required = false) String signature,
                        @RequestParam(value = "encrypteData", required = false) String encrypteData,
                        @RequestParam(value = "iv", required = false) String iv){
        System.out.println("code: "+code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxa8353d2eedd5a43f");
        param.put("secret", "7ff010250abce227c2a929313abe3d49");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String res = HttpClientUtil.doGet(url, param);

        JSONObject rawDataJson = JSON.parseObject(rawData);
        JSONObject idJson = JSON.parseObject(res);
        String status_key = this.updateUserInfo(idJson, rawDataJson);
        return Result.buildOK(status_key);
    }

    @PostMapping("/user/verification")
    public Result verification(@RequestParam(value = "token", required = false) String token) {
        System.out.println("token:" + token);
        //设置请求参数
        String jsonParam = "{\"token\":" + "\"" + token + "\"}";
        //执行请求
        String res = HttpClientUtil.doPostJson("https://alumni-test.iterator-traits.com/fake-id-tsinghua-proxy/api/user/session/token", jsonParam);
        System.out.println(res);
        JSONObject studentInfo = JSON.parseObject(res);
        /*JSONObject errorInfo = JSON.parseObject(JSON.parseObject(studentInfo.getString("user")).getString("error"));
        String status = errorInfo.getString("message");
        if (status == "success") {
            //身份验证验证成功
            String card = JSON.parseObject(studentInfo.getString("user")).getString("card");
            System.out.println(card);

        }

        else {
            //身份验证失败
            return Result.buildError("verification failed.");
        }*/
        String card = "";
        return Result.buildOK(card);
    }

    private String updateUserInfo(JSONObject idJson, JSONObject rawDataJson) {
        String openid = idJson.getString("openid");
        String session_key = idJson.getString("session_key");
        String status_key = UUID.randomUUID().toString();
        String nickName = rawDataJson.getString("nickName");
        String gender = rawDataJson.getString("gender");
        String language = rawDataJson.getString("language");
        String province = rawDataJson.getString("province");
        String country = rawDataJson.getString("country");
        String avatarUrl = rawDataJson.getString("avatarUrl");

        User user = this.userMapper.selectById(openid);
        boolean isNewUser = user == null;
        if(isNewUser) System.out.println("is new");
        if(isNewUser) {
            user = new User();
            user.setOpenid(openid);
            user.setCreate_time(new Date());
        }

        user.setSession_key(session_key);
        user.setStatus_key(status_key);
        user.setNick_name(nickName);
        user.setGender(Integer.parseInt(gender));
        user.setLanguage(language);
        user.setProvince(province);
        user.setCountry(country);
        user.setAvatar_url(avatarUrl);
        user.setLast_visit_time(new Date());

        if(isNewUser) {
            this.userMapper.insert(user);
        }
        else {
            this.userMapper.updateById(user);
        }
        return status_key;
    }


}
