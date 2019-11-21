package cn.edu.tsinghua.thutickets.service;

import cn.edu.tsinghua.thutickets.entity.Event;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    boolean checkAdmin(String name, String password);
    boolean uploadEvent(String title, String date,
                               String time, String text,
                               MultipartFile img);
    IPage<Event> listEvents(int pageIndex);
};
