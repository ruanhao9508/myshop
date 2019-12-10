package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auth RuanHao
 * @Time 2019/12/7 17:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyPage {

    private Integer currePage=1;//当前页
    private Long pageSize=5L;//每页显示记录数
    private String url;
}
