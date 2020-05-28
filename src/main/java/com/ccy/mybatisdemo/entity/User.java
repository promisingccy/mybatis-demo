package com.ccy.mybatisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/26 19:25
 * @Version 1.0
 **/
@Data //lombok-为类提供get、set、equals、hashCode、toString
//@TableName(value = "user") //mp-指定表名
public class User {
//    @TableId(value = "id") //mp-表主键
    private Long id;
//    @TableField(value = "name") //mp-表字段
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    //逻辑字段 非表字段 备注
//    private String remark;//此时插入失败 因为没有此字段
//    private transient String remark;//插入成功 未序列化
//    private static String remark;//插入成功 未序列化
    @TableField(exist = false) //mp-字段 exist是否表中存在
    private String remark;

}
