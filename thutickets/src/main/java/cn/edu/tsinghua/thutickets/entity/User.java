package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("user")
public class User {
    @TableId(value = "openid", type = IdType.INPUT)
    private String openid;
    // private String session_key;
    @TableField(value = "session_key", exist = true)
    private String sessionKey;
    // private String status_key;
    @TableField(value = "status_key", exist = true)
    private String statusKey;
    // private String nick_name;
    private String studentid;
    @TableField(value = "nick_name", exist = true)
    private String nickName;
    private Integer gender;
    private String language;
    private String province;
    private String country;
    // private String avatar_url;
    @TableField(value = "avatar_url", exist = true)
    private String avatarUrl;
    // private Date create_time;
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;
    // private Date last_visit_time;
    @TableField(value = "last_visit_time", exist = true)
    private Timestamp lastVisitTime;
}
