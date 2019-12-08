package cn.edu.tsinghua.thutickets.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("ticket")
public class Ticket {
    @TableId(value = "ticketid",type = IdType.INPUT)
    private String ticketid;
    private String eventid;
    private String studentid;
    private Integer validation;
    @TableField(value = "event_date", exist = true)
    private String eventDate;
    @TableField(value = "event_time", exist = true)
    private String eventTime;
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;

    public String getCreateTime() { return createTime.toString(); }
}
