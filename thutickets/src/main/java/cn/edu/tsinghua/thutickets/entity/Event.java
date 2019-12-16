package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@TableName("event")
public class Event implements Comparable<Event> {
    @TableId(value = "eventid",type = IdType.INPUT)
    private String eventid;
    private String title;
    @TableField(value = "event_date", exist = true)
    private String eventDate;
    @TableField(value = "event_time", exist = true)
    private String eventTime;
    private String location;
    @TableField(value = "purchase_date", exist = true)
    private String purchaseDate;
    @TableField(value = "purchase_time", exist = true)
    private String purchaseTime;
    @TableField(value = "tickets_left", exist = true)
    private Integer ticketsLeft;
    private String text;
    @TableField(value = "img_path", exist = true)
    private String imgPath;
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;

    public String getImgPath() { return imgPath; }
    public String getCreateTime() { return createTime.toString(); }

    @Override
    public int compareTo(Event event) {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long eventTimestamp = format.parse(getEventDate()+" "+getEventTime()).getTime();
            long _eventTimestamp = format.parse(event.getEventDate()+" "+event.getEventTime()).getTime();
            if (_eventTimestamp-eventTimestamp >= 0) return 1;
            else return -1;
        } catch (ParseException e) {
            return -1;
        }
    }
}
