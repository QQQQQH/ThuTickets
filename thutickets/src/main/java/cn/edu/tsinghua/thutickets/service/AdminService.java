package cn.edu.tsinghua.thutickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AdminService {
    boolean checkAdmin(String name, String password);
};
