package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 21:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Messages implements Serializable {

    private String phone;
    private String code;
}
