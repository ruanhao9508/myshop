package com.qf.myshop_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 1:43
 **/
@Configuration
public class RabbiMQConfig {

   /* @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("mail_exchange");
    }*/

   //DirectExchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Queue getQueue(){
        return new Queue("mail_queue");
    }

    /* @Bean
    public Binding getBinding(FanoutExchange getExchange,Queue getQueue){
        return BindingBuilder.bind(getQueue).to(getExchange);
    }*/

    @Bean
    public Queue getQueueMsg(){
        return new Queue("messages_queue");
    }

    @Bean
    public Binding getBindingEmail(){
        return BindingBuilder.bind(getQueue()).to(directExchange()).with("email");
    }

    @Bean
    public Binding getBindingMsg(){
        return BindingBuilder.bind(getQueueMsg()).to(directExchange()).with("messages");
    }
}
