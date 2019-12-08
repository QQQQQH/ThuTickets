package cn.edu.tsinghua.thutickets.controller;

import java.sql.Timestamp;
import java.util.*;

import cn.edu.tsinghua.thutickets.dao.TicketMapper;
import cn.edu.tsinghua.thutickets.entity.Event;
import cn.edu.tsinghua.thutickets.entity.Ticket;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import cn.edu.tsinghua.thutickets.common.HttpClientUtil;
import cn.edu.tsinghua.thutickets.common.Result;
import cn.edu.tsinghua.thutickets.dao.UserMapper;
import cn.edu.tsinghua.thutickets.dao.EventMapper;
import cn.edu.tsinghua.thutickets.entity.User;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private TicketMapper ticketMapper;

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
        String statusKey = this.updateUserInfo(idJson, rawDataJson);
        System.out.println("statusKey: "+statusKey);
        return Result.buildOK(statusKey);
    }

    @PostMapping("/verify")
    public Result verify(@RequestParam(value = "skey", required = false) String skey,
                         @RequestParam(value = "token", required = false) String token) {
        System.out.println("skey: "+skey);
        JSONObject requestObject = new JSONObject();
        requestObject.put("token", token);
        String res = HttpClientUtil.doPostJson("https://alumni-test.iterator-traits.com/fake-id-tsinghua-proxy/api/user/session/token",
                requestObject.toString());
        JSONObject responseObject = JSON.parseObject(res);
        System.out.println(responseObject);
        JSONObject responseStatusObject = JSON.parseObject(responseObject.getString("error"));
        if (responseStatusObject.getString("message").equals("success")) {
            JSONObject studentInfoObject = JSON.parseObject(responseObject.getString("user"));
            String studentid = studentInfoObject.getString("card");
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("status_key", skey));
            if (user == null) return Result.buildError("Invalid skey.");
            user.setStudentid(studentid);
            return Result.buildOK(studentid);
        }
        else return Result.buildError("Fail to verify student identity.");
    }

    @GetMapping("/events")
    private Result eventInfo() {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        List<Event> eventList = eventMapper.selectList(queryWrapper);
        for (int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);
            String img = e.getImgPath();
            String temp = img.replace("~", "");
            e.setImgPath(temp);
        }
        Object json = JSON.toJSON(eventList);
        System.out.println("json string:");
        System.out.println(json);

        return Result.buildOK(json);
    }

    @GetMapping("/buy-ticket")
    private Result buyTicket(@RequestParam(value = "eventid", required = true) String eventid,
                             @RequestParam(value = "skey", required = true) String skey) {
        Event event = eventMapper.selectById(eventid);
        if (event == null) {
            // 活动id错误
            return Result.buildError("Event does not exist");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("status_key", skey));
        if (user == null) {
            // 用户学生号错误
            return Result.buildError("Invalid user info");
        }
        Ticket ticket = new Ticket();
        ticket.setTicketid(UUID.randomUUID().toString());
        ticket.setEventid(eventid);
        ticket.setStudentid(user.getStudentid());
        ticket.setValidation(1);
        ticket.setCreateTime(new Timestamp(new Date().getTime()));
        ticketMapper.insert(ticket);
        // 购票成功
        return Result.buildOK(ticket.getTicketid());
    }

    @GetMapping("/use-ticket")
    private Result useTicket(@RequestParam(value = "ticketid", required = true) String ticketid,
                             @RequestParam(value = "eventid", required = true) String eventid,
                             @RequestParam(value = "studentid", required = true) String studentid) {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("ticketid", ticketid));
        if (!ticket.getEventid().equals(eventid)) {
            // 活动id不匹配
            return Result.buildError("Invalid event info");
        }
        if (!ticket.getStudentid().equals(studentid)) {
            // 用户学生号不匹配
            return Result.buildError("Invalid user info");
        }
        ticket.setValidation(0);
        // 使用成功
        return Result.buildOK("使用成功！");

    }
    @GetMapping("/events/image")
    private Result imageInfo(@RequestParam(value = "id", required = false) String id) {
        Event e = this.eventMapper.selectById(id);
        String img = e.getImgPath();
        String temp = img.replace("~", "");
        e.setImgPath(temp);
        return Result.buildOK(e);
    }
    private String updateUserInfo(JSONObject idJson, JSONObject rawDataJson) {
        String openid = idJson.getString("openid");
        String sessionKey = idJson.getString("session_key");
        String statusKey = UUID.randomUUID().toString();

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
            user.setCreateTime(new Timestamp(new Date().getTime()));
        }

        user.setSessionKey(sessionKey);
        user.setStatusKey(statusKey);
        user.setNickName(nickName);
        user.setGender(Integer.parseInt(gender));
        user.setLanguage(language);
        user.setProvince(province);
        user.setCountry(country);
        user.setAvatarUrl(avatarUrl);
        user.setLastVisitTime(new Timestamp(new Date().getTime()));

        if(isNewUser) {
            this.userMapper.insert(user);
        }
        else {
            this.userMapper.updateById(user);
        }
        return statusKey;
    }
}
