package cn.edu.tsinghua.thutickets.service.impl;

import cn.edu.tsinghua.thutickets.dao.EventMapper;
import cn.edu.tsinghua.thutickets.service.AdminService;
import cn.edu.tsinghua.thutickets.entity.Event;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {
//    private String localPath = "/home/ubuntu/thutickets/images/";
    private String localPath = "D:\\THU\\2019-2020_Autumn\\4-2 Software Engineering\\hw\\final\\ThuTickets\\thutickets\\images\\";
    private String imgPath = "~/images/";

    @Autowired
    private EventMapper eventMapper;

    @Override
    public boolean checkAdmin(String username, String password) {
        if (username.equals("admin") && password.equals("123")) return true;
        else return false;
    }

    @Override
    public boolean uploadEvent(String title, String date,
                               String time, String text,
                               MultipartFile inputImg) {
        Event event = new Event();
        String eventid = UUID.randomUUID().toString();
        String filename = inputImg.getOriginalFilename();
        int sepIndex = filename.lastIndexOf(".");
        if (sepIndex != -1) {
            String suffix = filename.substring(sepIndex);
            filename = eventid+suffix;
            String path = localPath+filename;
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
            event.setImgPath(imgPath+filename);
        }
        event.setEventid(eventid);
        event.setTitle(title);
        event.setDate(date);
        event.setTime(time);
        event.setText(text);
        event.setCreateTime(new Timestamp(new Date().getTime()));
        eventMapper.insert(event);
        return true;
    }

    @Override
    public IPage<Event> listEvents(int pageIndex) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("eventid");
        Page<Event> page = new Page<>(pageIndex, 2);

        IPage<Event> iPage = eventMapper.selectPage(page, queryWrapper);
        return iPage;
    }
};
