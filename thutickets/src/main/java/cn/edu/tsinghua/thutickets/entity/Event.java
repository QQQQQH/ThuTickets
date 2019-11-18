package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("event")
public class Event {
    @TableId(value = "eventid",type = IdType.INPUT)
    private String eventid;
    private String title;
    private String date;
    private String time;
    private String text;
    private String img_path;
    private Date create_time;
}
