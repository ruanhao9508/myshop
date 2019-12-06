package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/6 19:36
 **/
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加到索引库
     * @param goods
     * @return
     */
    @Override
    public int insertSolr(Goods goods) {

        //每一个document相当于一条记录
        SolrInputDocument document=new SolrInputDocument();
        document.setField("id",goods.getId()+"");
        document.addField("subeject",goods.getSubject());
        document.addField("info",goods.getInfo());
        document.addField("price",goods.getPrice().doubleValue());
        document.addField("save",goods.getSave());
        document.addField("image",goods.getFmImage());

        try {
            solrClient.add(document);
            //所有的增删改都需要提交(commit)
            solrClient.commit();

            return  1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据关键字查询索引库
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchSolr(String keyword) {
        return null;
    }
}
