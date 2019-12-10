package cn.edu.tsinghua.thutickets.controller;

import cn.edu.tsinghua.thutickets.entity.Event;
import cn.edu.tsinghua.thutickets.entity.Ticket;
import cn.edu.tsinghua.thutickets.service.UserService;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.edu.tsinghua.thutickets.common.Result;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "rawData", required = false) String rawData,
                        @RequestParam(value = "signature", required = false) String signature,
                        @RequestParam(value = "encrypteData", required = false) String encrypteData,
                        @RequestParam(value = "iv", required = false) String iv){

        JSONObject idJson = userService.getOpenidAndSessionKey(code);
        JSONObject rawDataJson = JSON.parseObject(rawData);
        String statusKey = userService.updateUserInfo(idJson, rawDataJson);
        return Result.buildOK(statusKey);
    }

    @PostMapping("/verify")
    public Result verify(@RequestParam(value = "skey", required = false) String skey,
                         @RequestParam(value = "token", required = false) String token) {
        String studentid = userService.verifyStudent(skey, token);
        if (studentid != null) {
            return Result.buildOK(studentid);
        }
        else return Result.buildError("Fail to verify student.");
    }

    @GetMapping("/events/list")
    public Result getEventList(@RequestParam(value = "expired", required = false) Integer expired) {
        Object eventList = userService.listEvents(expired);
        return Result.buildOK(eventList);
    }

    @GetMapping("/events/detail")
    public Result getEventDetail(@RequestParam(value = "eventid", required = true) String eventid) {
        Event event = userService.getEvent(eventid);
        if (event != null) return Result.buildOK(event);
        else return Result.buildError("Fail to fetch event detail.");
    }

    @GetMapping("/buy-ticket")
    public Result buyTicket(@RequestParam(value = "skey", required = true) String skey,
                             @RequestParam(value = "eventid", required = true) String eventid) {
        String ticketid = userService.buyTicket(skey, eventid);
        if (ticketid != null) return Result.buildOK(ticketid);
        else return Result.buildError("Fail to buy ticket.");
    }

    @GetMapping("/use-ticket")
    public Result useTicket(@RequestParam(value = "skey", required = true) String skey,
                             @RequestParam(value = "ticketid", required = true) String ticketid,
                             @RequestParam(value = "eventid", required = true) String eventid) {
        if (userService.useTicket(skey, ticketid, eventid)) return Result.buildOK(ticketid);
        else return Result.buildError("Fail to use ticket.");
    }

    @GetMapping("/tickets/list")
    public Result getTicketList(@RequestParam(value = "skey", required = true) String skey,
                                 @RequestParam(value = "validation", required = false) Integer validation) {
        Object eventList = userService.listTickets(skey, validation);
        if (eventList != null) return Result.buildOK(eventList);
        else return Result.buildError("Fail to fetch ticket list.");
    }

    @GetMapping("/tickets/detail")
    public Result getEventDetail(@RequestParam(value = "skey", required = true) String skey,
                                  @RequestParam(value = "ticketid", required = true) String ticketid) {
        Ticket ticket = userService.getTicket(skey, ticketid);
        if (ticket != null) return Result.buildOK(ticket);
        else return Result.buildError("Fail to fetch ticket detail.");
    }

    @GetMapping("/tickets/delete")
    public Result deleteTicket(@RequestParam(value = "skey", required = true) String skey,
                                @RequestParam(value = "ticketid", required = true) String ticketid) {
        if (userService.deleteTicket(skey, ticketid)) return Result.buildOK(ticketid);
        else return Result.buildError("Fail to delete ticket.");
    }
}
