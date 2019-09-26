package cn.edu.tsinghua.thutickets.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId(value = "openid",type = IdType.INPUT)
    private String openid;
    private String session_key;
    private String status_key;
    private String nick_name;
    private Integer gender;
    private String language;
    private String province;
    private String country;
    private String avatar_url;
    private Date create_time;
    private Date last_visit_time;
}
