package cn.edu.tsinghua.thutickets.service.impl;

import cn.edu.tsinghua.thutickets.dao.AdminMapper;
import cn.edu.tsinghua.thutickets.dao.EventMapper;
import cn.edu.tsinghua.thutickets.entity.Admin;
import cn.edu.tsinghua.thutickets.service.AdminService;
import cn.edu.tsinghua.thutickets.entity.Event;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Value("${img.dir}")
    private String imgDir;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public boolean checkAdmin(String username, String password) {
        Admin admin = adminMapper.selectById(username);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            admin.setLastVisitTime(new Timestamp(new Date().getTime()));
            adminMapper.updateById(admin);
            return true;
        }
        else return false;
    }

    @Override
    public boolean uploadEvent(String title, String eventDate,
                               String eventTime, String location,
                               String purchaseDate, String purchaseTime,
                               Integer ticketsLeft, String text,
                               MultipartFile inputImg) {
        Event event = new Event();
        String eventid = UUID.randomUUID().toString();
        String filename = inputImg.getOriginalFilename();
        int sepIndex = filename.lastIndexOf(".");
        if (sepIndex != -1) {
            String suffix = filename.substring(sepIndex);
            filename = eventid+suffix;
            String path = imgDir+filename;
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                inputImg.transferTo(file);
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            String imgPath = "~/images/";
            event.setImgPath(imgPath +filename);
        }
        event.setEventid(eventid);
        event.setTitle(title);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setLocation(location);
        event.setPurchaseDate(purchaseDate);
        event.setPurchaseTime(purchaseTime);
        event.setTicketsLeft(ticketsLeft);
        event.setText(text);
        event.setCreateTime(new Timestamp(new Date().getTime()));
        eventMapper.insert(event);
        return true;
    }

    @Override
    public IPage<Event> listEvents(int pageIndex) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("event_date", "event_time");
        Page<Event> page = new Page<>(pageIndex, 2);
        IPage<Event> iPage = eventMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public Event getEvent(String eventid) {
        return eventMapper.selectById(eventid);
    }

    @Override
    public boolean deleteEvent(String eventid) {
        Event event = eventMapper.selectById(eventid);
        if (event == null) return false;
        try {
            File file = new File(".."+event.getImgPath().substring(1));
            if (file.exists()) file.delete();
        }
        catch (Exception ignored){}
        eventMapper.deleteById(event.getEventid());
        return true;
    }
};
