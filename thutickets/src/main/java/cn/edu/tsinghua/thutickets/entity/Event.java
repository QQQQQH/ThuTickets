package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("event")
public class Event {
    @TableId(value = "eventid",type = IdType.INPUT)
    private String eventid;
    private String title;
    private String date;
    private String time;
    private String text;
    @TableField(value = "img_path", exist = true)
    private String imgPath;
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;

    public String getImgPath() { return imgPath; }
    public String getCreateTime() { return createTime.toString(); }
}
