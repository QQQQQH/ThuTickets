package cn.edu.tsinghua.thutickets.controller;

import cn.edu.tsinghua.thutickets.entity.Event;
import cn.edu.tsinghua.thutickets.service.AdminService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("")
    public String _() {
        return "redirect:/admin/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/events/upload")
    public String getUploadPage() { return "upload"; }

    @GetMapping("/events/list")
    public String eventsList(@RequestParam(value = "page", required = false) int pageIndex, Model model) {
        IPage<Event> eventsPage = adminService.listEvents(pageIndex);
        model.addAttribute("eventsPage", eventsPage);
        return "eventList";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        Map<String, Object> map,
                        HttpSession session) {
        if (adminService.checkAdmin(username, password)) {
            session.setAttribute("username", "admin");
            return "redirect:/admin/index";
        }
        else {
            session.setAttribute("username", "");
            map.put("msgError", "密码错误");
            return "login";
        }
    }

    @PostMapping("/events/upload")
    public String eventsUpload(@RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "eventDate", required = false) String eventDate,
                               @RequestParam(value = "eventTime", required = false) String eventTime,
                               @RequestParam(value = "location", required = false) String location,
                               @RequestParam(value = "purchaseDate", required = false) String purchaseDate,
                               @RequestParam(value = "purchaseTime", required = false) String purchaseTime,
                               @RequestParam(value = "ticketsLeft", required = false) Integer ticketsLeft,
                               @RequestParam(value = "text", required = false) String text,
                               @RequestParam(value = "inputImg", required = false) MultipartFile inputImg,
                         Map<String, Object> map) {
        if (adminService.uploadEvent(title, eventDate, eventTime, location,
                                     purchaseDate, purchaseTime, ticketsLeft,
                                     text, inputImg)) {
            map.put("msgSuccess", "上传成功");
            return "upload";
        }
        else {
            map.put("msgError", "上传失败");
            return "upload";
        }
    }

    @GetMapping("/events/detail")
    public String getEventDetail(@RequestParam(value = "eventid", required = true) String eventid,
                                 Map<String, Object> map) {
        Event event = adminService.getEvent(eventid);
        if (event != null) return "upload";
        else {
//            显示活动不存在
            return "index";
        }
    }
}
