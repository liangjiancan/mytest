package cn.solr;

import cn.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;

import java.util.List;

/**
 * solr的例子
 */
public class SolrTest {
    /**
     * 定义SolrServer
     */
    private SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

    /**
     * 添加或修改索引库
     */
    @Test
    public  void  saveOrUpdate() throws Exception{
        Product product = new Product();
        //根据pid来操作,如果操作则修改,不存在则新增
        product.setPid("8000");
        product.setName("iphone8");
        product.setCatalogName("手机");
        product.setPrice(8000d);
        product.setDescription("苹果手机还不错哦！");
        product.setPicture("666.jpg");
        //得到响应对象
        UpdateResponse updateResponse = solrServer.addBean(product);
        //提交事务
        solrServer.commit();
    }

    /**
     * 删除索引库
     */
    @Test
    public  void  delete() throws Exception{
        //得到响应对象
        //UpdateResponse updateResponse = solrServer.deleteById("8000");

        //根据索引的字段删除
        //UpdateResponse updateResponse = solrServer.deleteById("name:手机");

        //删除全部
        UpdateResponse updateResponse = solrServer.deleteByQuery("*:*");
        //提交事务
        solrServer.commit();
    }

    /**
     * 查询索引库
     */
    @Test
    public void find() throws Exception{
        //创建查询对象:查询全部
        SolrQuery solrQuery = new SolrQuery("*:*");
        //分页开始记录数
        solrQuery.setStart(5);
        //页大小
        solrQuery.setRows(5);

        //查询其他的条件
        //SolrQuery solrQuery = new SolrQuery("keywords:手机");

        //查询操作
        QueryResponse response = solrServer.query(solrQuery);
        System.out.println("总命中的记录数:"+ response.getResults().getNumFound());

        //获取文档
        List<Product> productList = response.getBeans(Product.class);
        for (Product product : productList) {
            System.out.println("------------------");
            System.out.println(product);
        }
    }
}
