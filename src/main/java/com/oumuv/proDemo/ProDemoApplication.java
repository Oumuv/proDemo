package com.oumuv.proDemo;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//        (exclude = SolrAutoConfiguration.class)
public class ProDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProDemoApplication.class, args);
    }

//    @Bean
//    public SolrClient solrClient() {
//        System.out.println("自定义配置SolrClient");
//        HttpSolrClient httpSolrClient = new HttpSolrClient("http://192.168.6.128:8081/solr/index.html");
//        return httpSolrClient;
//    }


}
