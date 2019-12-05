package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auth RuanHao
 * @Time 2019/12/4 20:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GoodsImages extends BaseEntity{
    private Integer gid;
    private String info;
    private String url;
    private Integer isfengmian=0;//0非封面 1封面
}
