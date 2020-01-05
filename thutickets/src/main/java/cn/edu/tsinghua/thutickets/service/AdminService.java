package cn.edu.tsinghua.thutickets.service;

import cn.edu.tsinghua.thutickets.entity.Event;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    boolean checkAdmin(String name, String password);
    boolean uploadEvent(String title, String eventDate,
                        String eventTime, String location,
                        String purchaseDate, String purchaseTime,
                        Integer ticketsLeft, String text,
                        MultipartFile inputImg);
    IPage<Event> listEvents(int pageIndex);
    Event getEvent(String eventid);
    boolean updateEvent(String eventid,
                        String title, String eventDate,
                        String eventTime, String location,
                        String purchaseDate, String purchaseTime,
                        Integer ticketsLeft, String text,
                        MultipartFile inputImg);
    boolean deleteEvent(String eventid);
};
