package com.qf.listener;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 1:00
 **/

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ的监听器 -接收MQ中的消息
 */
@Component//让spring扫到
public class RabbitMQListener {

    @Autowired
    private ISearchService searchService;
    //监听那一个队列,这里没有队列要自己创建队列,并于交换机绑定并且监听这个队列
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(value = "goods_exchange",type = "fanout"),
            value = @Queue(name = "search_queue")))
    public void msgHandler(Goods goods){//传过来的收一个goods,就用Goods接受
//        System.out.println("搜索服务接收到队列的信息为:"+goods);
        searchService.insertSolr(goods);
    }
}
