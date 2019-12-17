package cn.edu.tsinghua.thutickets.service;

import cn.edu.tsinghua.thutickets.entity.Ticket;
import cn.edu.tsinghua.thutickets.entity.User;
import cn.edu.tsinghua.thutickets.entity.Event;
import com.alibaba.fastjson.JSONObject;

public interface UserService {
     User getUserBySkey(String skey);
     JSONObject getOpenidAndSessionKey(String code);
     String updateUserInfo(JSONObject idJson, JSONObject rawDataJson);
     String verifyStudent(String skey, String token);
     Object listEvents(Integer expired);
     Event getEvent(String eventid);
     String buyTicket(String skey, String eventid);
     boolean useTicket(String skey, String ticketid, String eventid);
     Object listTickets(String skey, Integer validation);
     Ticket getTicket(String ticketid);
     boolean deleteTicket(String skey, String ticketid);
};
