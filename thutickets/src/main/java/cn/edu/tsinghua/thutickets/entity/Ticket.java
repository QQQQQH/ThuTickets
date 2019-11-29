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
    @TableField(value = "create_time", exist = true)
    private Timestamp createTime;

    public String getCreateTime() { return createTime.toString(); }
}
