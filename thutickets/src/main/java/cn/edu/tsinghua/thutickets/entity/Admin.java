package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("admin")
public class Admin {
    @TableId(value = "admin_username", type = IdType.INPUT)
    private String adminUsername;
    @TableField(value = "admin_password", exist = true)
    private String adminPassword;
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;
    @TableField(value = "last_visit_time", exist = true)
    private Timestamp lastVisitTime;
}
