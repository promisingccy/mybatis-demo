package com.ccy.mybatisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/26 19:25
 * @Version 1.0
 **/

// EQUAL = "%s=#{%s}";
// NOT_EQUAL = "%s&lt;&gt;#{%s}";
// LIKE = "%s LIKE CONCAT('%%',#{%s},'%%')";
// LIKE_LEFT = "%s LIKE CONCAT('%%',#{%s})";
// LIKE_RIGHT = "%s LIKE CONCAT(#{%s},'%%')";

@Data //lombok-为类提供get、set、equals、hashCode、toString
//@TableName(value = "user") //mp-指定表名
@EqualsAndHashCode(callSuper = false) //消除@Data注解报错
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.AUTO) //mp-表主键 AUTO数据库自增
    private Long id;
    @TableField(value = "name", condition = SqlCondition.LIKE) //mp-表字段 匹配符设置为 like
    private String name;

    @TableField(condition = "%s&lt;#{%s}") //mp-匹配符设置为<
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
