package cn.edu.tsinghua.thutickets.service.impl;

import cn.edu.tsinghua.thutickets.service.AdminService;
import org.springframework.stereotype.Service;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Override
    public boolean checkAdmin(String name, String password) {
        if (name.equals("admin") && password.equals("123")) return true;
        else return false;
    }
};
