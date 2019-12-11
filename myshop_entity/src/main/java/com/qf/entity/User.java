package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 21:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User extends BaseEntity{

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
}
