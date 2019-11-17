package cn.edu.tsinghua.thutickets.controller;

import cn.edu.tsinghua.thutickets.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @RequestMapping("")
    public String index() {
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "password", required = false) String password,
                        Map<String, Object> map) {
        System.out.println(name);
        System.out.println(password);
        if (service.checkAdmin(name, password)) {
            map.put("message", "登录成功");
            return "login";
        }
        else {
            map.put("message", "密码错误");
            return "login";
        }

    }
}
