package com.qf.myshop_goods;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 0:50
 **/

/**
 * rabbitMQ的配置类
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 提供者声明交换机
     * @return
     */
    @Bean
    public FanoutExchange getExchange(){
            return new FanoutExchange("goods_exchange");
    }
}
