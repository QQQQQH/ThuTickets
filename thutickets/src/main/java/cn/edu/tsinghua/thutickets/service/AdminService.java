package cn.edu.tsinghua.thutickets.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    boolean checkAdmin(String name, String password);
    public boolean uploadEvent(String title, String date,
                               String time, String text,
                               MultipartFile img);
};
