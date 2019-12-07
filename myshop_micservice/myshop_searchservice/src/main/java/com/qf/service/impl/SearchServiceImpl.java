package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        document.addField("subject",goods.getSubject());
        document.addField("info",goods.getInfo());
        document.addField("price",goods.getPrice().doubleValue());
        document.addField("save",goods.getSave());
        document.addField("image",goods.getFmImage());

        try {
            //solrClient.xxx方法要chu
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

        SolrQuery query=new SolrQuery();
        if (keyword != null && !keyword.equals("")){
            query.setQuery("subject:"+keyword+" || info:"+keyword);
        }else{
            //keyword为空则搜索所有
            query.setQuery("*:*");
        }

        //设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<font color='red'>");//设置前缀
        query.setHighlightSimplePost("</font>");//设置后缀
        query.addHighlightField("subject");//设置那些字段有高亮信息(查出来展示的)

        try {
            //查询的响应
            QueryResponse response = solrClient.query(query);

            //获得高亮后的结果
            //Map<id,高亮的信息>
            //高亮信息-Map<高亮的字段,高亮内容集合>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //从响应中获取result结果集(ArrayList集合)
            SolrDocumentList results = response.getResults();
            //声明一个集合存从检索库查出来的数据
            List<Goods> goodsList=new ArrayList<>();
            for (SolrDocument document : results) {
                Goods goods=new Goods();
                goods.setId(Integer.parseInt((String) document.get("id")));
                goods.setFmImage((String) document.get("image"))
                        .setSubject((String) document.get("subject"))
                        .setPrice(BigDecimal.valueOf((double)document.get("price")))
                        .setSave((Integer) document.get("save"));

                //处理高亮结果
               if (highlighting.containsKey(goods.getId()+"")){
                   //商品有高亮信息,依次获取高亮的字段
                   Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                   List<String> subject = stringListMap.get("subject");
                   if (subject !=null){
                       goods.setSubject(subject.get(0));
                   }
               }

                goodsList.add(goods);
            }
            return goodsList;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
