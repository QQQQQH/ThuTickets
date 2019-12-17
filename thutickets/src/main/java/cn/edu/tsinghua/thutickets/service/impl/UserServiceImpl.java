package cn.edu.tsinghua.thutickets.service.impl;

import cn.edu.tsinghua.thutickets.dao.EventMapper;
import cn.edu.tsinghua.thutickets.dao.TicketMapper;
import cn.edu.tsinghua.thutickets.dao.UserMapper;
import cn.edu.tsinghua.thutickets.entity.Event;
import cn.edu.tsinghua.thutickets.entity.Ticket;
import cn.edu.tsinghua.thutickets.entity.User;
import cn.edu.tsinghua.thutickets.service.UserService;
import cn.edu.tsinghua.thutickets.common.HttpClientUtil;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.sql.Timestamp;


@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TicketMapper ticketMapper;

    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public User getUserBySkey(String skey) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("status_key", skey));
    }

    @Override
    public JSONObject getOpenidAndSessionKey(String code) {
        System.out.println("code: "+code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxa8353d2eedd5a43f");
        param.put("secret", "7ff010250abce227c2a929313abe3d49");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String res = HttpClientUtil.doGet(url, param);
        return JSON.parseObject(res);
    }

    @Override
    public String updateUserInfo(JSONObject idJson, JSONObject rawDataJson) {
        String openid = idJson.getString("openid");
        String sessionKey = idJson.getString("session_key");
        String statusKey = UUID.randomUUID().toString();

        String nickName = rawDataJson.getString("nickName");
        String gender = rawDataJson.getString("gender");
        String language = rawDataJson.getString("language");
        String province = rawDataJson.getString("province");
        String country = rawDataJson.getString("country");
        String avatarUrl = rawDataJson.getString("avatarUrl");

        User user = userMapper.selectById(openid);
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

        if(isNewUser) userMapper.insert(user);
        else userMapper.updateById(user);
        return statusKey;
    }

    @Override
    public String verifyStudent(String skey, String token) {
        User user = getUserBySkey(skey);
        if (user == null) return null;

        JSONObject requestObject = new JSONObject();
        requestObject.put("token", token);
        String res = HttpClientUtil.doPostJson("https://alumni-test.iterator-traits.com/fake-id-tsinghua-proxy/api/user/session/token",
                requestObject.toString());
        JSONObject responseObject = JSON.parseObject(res);
        JSONObject responseStatusObject = JSON.parseObject(responseObject.getString("error"));
        if (responseStatusObject.getString("message").equals("success")) {
            JSONObject studentInfoObject = JSON.parseObject(responseObject.getString("user"));
            String studentid = studentInfoObject.getString("card");
            user.setStudentid(studentid);
            userMapper.updateById(user);
            return studentid;
        }
        else return null;
    }

    @Override
    public Object listEvents(Integer expired) {
        List<Event> eventList = eventMapper.selectList(new QueryWrapper<>());
        for (Event event: eventList) {
            if (expired == 0 || expired == 1) {
                try {
                    long eventTimestamp = format.parse(event.getEventDate()+" "+event.getEventTime()).getTime();
                    Date currentDate = new Date();
                    long currentTimestamp = currentDate.getTime();
                    if ((expired == 1 && currentTimestamp > eventTimestamp)
                            || (expired == 0 && currentTimestamp <= eventTimestamp)) {
                        eventList.remove(event);
                        continue;
                    }
                }
                catch (ParseException e) {
                    eventList.remove(event);
                    continue;
                }
            }
            String modifiedPath = event.getImgPath().replace("~", "");
            event.setImgPath(modifiedPath);
        }
        Collections.sort(eventList);
        Object jsonObject = JSON.toJSON(eventList);
        return jsonObject;
    }

    @Override
    public Event getEvent(String eventid) {
        Event event = eventMapper.selectById(eventid);
        if (event == null) return null;
        String modifiedPath = event.getImgPath().replace("~", "");
        event.setImgPath(modifiedPath);
        return event;
    }

    @Override
    public String buyTicket(String skey, String eventid) {
        User user = getUserBySkey(skey);
        if (user == null || user.getStudentid() == null) return null;

        Event event = eventMapper.selectById(eventid);
        if (event == null || event.getTicketsLeft() <= 0) return null;
        try {
            long eventTimestamp = format.parse(event.getEventDate()+" "+event.getEventTime()).getTime();
            long purchaseTimestamp = format.parse(event.getPurchaseDate()+" "+event.getPurchaseTime()).getTime();
            Date currentDate = new Date();
            long currentTimestamp = currentDate.getTime();
            if (currentTimestamp <= purchaseTimestamp || currentTimestamp >= eventTimestamp) {
                return null;
            }
        }
        catch (ParseException e) {
            return null;
        }
        event.setTicketsLeft(event.getTicketsLeft()-1);
        eventMapper.updateById(event);
        Ticket ticket = new Ticket();
        String ticketid = UUID.randomUUID().toString();
        ticket.setTicketid(ticketid);
        ticket.setStudentid(user.getStudentid());
        ticket.setValidation(1);
        ticket.setEventid(event.getEventid());
        ticket.setTitle(event.getTitle());
        ticket.setEventDate(event.getEventDate());
        ticket.setEventTime(event.getEventTime());
        ticket.setLocation(event.getLocation());
        ticket.setCreateTime(new Timestamp(new Date().getTime()));
        ticketMapper.insert(ticket);
        return ticketid;
    }

    @Override
    public boolean useTicket(String skey, String ticketid, String eventid) {
        User user = getUserBySkey(skey);
        if (user == null) return false;
        Ticket ticket = ticketMapper.selectById(ticketid);
        if (ticket == null || ticket.getValidation() == 0 || !ticket.getStudentid().equals(user.getStudentid())) return false;
        Event event = eventMapper.selectById(eventid);
        if (event == null || !ticket.getEventid().equals(event.getEventid())) return false;
        try {
            long eventTimestamp = format.parse(event.getEventDate()+" "+event.getEventTime()).getTime();
            Date currentDate = new Date();
            long currentTimestamp = currentDate.getTime();
            if (currentTimestamp < eventTimestamp) {
                return false;
            }
        }
        catch (ParseException e) {
            return false;
        }
        ticket.setValidation(0);
        ticketMapper.updateById(ticket);
        return true;
    }

    @Override
    public Object listTickets(String skey, Integer validation) {
        User user = getUserBySkey(skey);
        if (user == null) return null;

        List<Ticket> ticketList;
        if (validation == 0 || validation == 1) {
            ticketList = ticketMapper.selectList(new QueryWrapper<Ticket>().eq("validation", validation.toString()));
        }
        else{
            ticketList = ticketMapper.selectList(new QueryWrapper<>());
        }
        return JSON.toJSON(ticketList);
    }

    @Override
    public Ticket getTicket(String ticketid) {
        return ticketMapper.selectById(ticketid);
    }

    @Override
    public boolean deleteTicket(String skey, String ticketid) {
        User user = getUserBySkey(skey);
        if (user == null) return false;

        Ticket ticket = ticketMapper.selectById(ticketid);
        if (ticket == null) return false;
        ticketMapper.deleteById(ticket.getTicketid());
        Event event = eventMapper.selectById(ticket.getEventid());
        if (event != null) {
            event.setTicketsLeft(event.getTicketsLeft()+1);
            eventMapper.updateById(event);
        }
        return true;
    }
};
