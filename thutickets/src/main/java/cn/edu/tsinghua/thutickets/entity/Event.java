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

    public String getImgPath() {
        System.out.println(eventid);
        System.out.println(title);
        System.out.println(date);
        System.out.println(time);
        System.out.println(text);
        System.out.println(imgPath);
        System.out.println(createTime);
        return imgPath;
    }
    public String getCreateTime() { return createTime.toString(); }
}
