package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 16:54
 **/
@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    protected Integer id;
    protected Date createTime=new Date();
    protected Integer status=0;
}
