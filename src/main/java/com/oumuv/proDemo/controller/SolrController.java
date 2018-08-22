package com.oumuv.proDemo.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *      solr控制器
 * @Author 欧银锋
 * @Date 2018/8/22 15:19
 **/
@Controller
@RequestMapping("solr")
public class SolrController {

    @Autowired
    private SolrClient solrClient;

    @RequestMapping("add")
    @ResponseBody
    public String addData(String name,Integer id) {
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.setField("id",id);
        solrInputFields.setField("name",name);
        try {
            solrClient.add(solrInputFields);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return "失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
        return "成功";
    }

    @RequestMapping("deleteById")
    @ResponseBody
    public String deleteData(Integer id) {
        try {
            solrClient.deleteById(id+"");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return "失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
        return "成功";
    }


    @RequestMapping("deleteAll")
    @ResponseBody
    public String deleteAllData() {
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return "失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
        return "成功";
    }

    @RequestMapping("getById")
    @ResponseBody
    public Object getById(Integer id) throws Exception {
        SolrDocument document = solrClient.getById(id+"");
        System.out.println(document);
        return document;
    }

    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Map<String, Map<String, List<String>>> search(){

        try {
            SolrQuery params = new SolrQuery();

            //查询条件, 这里的 q 对应 下面图片标红的地方
            params.set("q", "法");

            //过滤条件
//            params.set("fq", "product_price:[100 TO 100000]");

            //排序
//            params.addSort("product_price", SolrQuery.ORDER.asc);

            //分页
            params.setStart(0);
            params.setRows(20);

            //默认域
//            params.set("df", "product_title");

            //只查询指定域
//            params.set("fl", "id,product_title,product_price");

            //高亮
            //打开开关
            params.setHighlight(true);
            //指定高亮域
            params.addHighlightField("name");
            //设置前缀
            params.setHighlightSimplePre("<span style='color:red'>");
            //设置后缀
            params.setHighlightSimplePost("</span>");

            QueryResponse queryResponse = solrClient.query(params);

            SolrDocumentList results = queryResponse.getResults();

            long numFound = results.getNumFound();

            System.out.println(numFound);

            Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();

            for (SolrDocument result : results) {
                System.out.println(result.get("id"));
                System.out.println(result.get("name"));

                Map<String, List<String>> map = highlight.get(result.get("id"));
                List<String> list = map.get("name");
                System.out.println(list.get(0));

                System.out.println("------------------");
                System.out.println();
            }
            return highlight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
