package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 16:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends  BaseEntity{

    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;

    @TableField(exist = false)
    private String fmImage;
    @TableField(exist = false)
    private List<String> otherImages;
}
