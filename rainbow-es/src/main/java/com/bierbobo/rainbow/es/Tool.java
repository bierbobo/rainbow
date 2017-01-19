package com.bierbobo.rainbow.es;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
public class Tool {

    private static Logger logger = Logger.getLogger(Tool.class);

    //用集群名字，集群节点地址构建es client
    public static Client CLIENT = null;
    public static ElasticsearchTemplate ES_TEMPLATE;
    public 	static String INDEX_NAME = "spring_data_index";
    public 	static String TYPE_NAME = "spring_data_type";

    // agg_index/agg_type/_search
    // agg_index/_mapping

    static {
        try {
            CLIENT = getClient("jiesi-1", "192.168.200.196", 9303);
            ES_TEMPLATE = new ElasticsearchTemplate(CLIENT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }



    /**
     * 创建es client
     * clusterName:集群名字
     * nodeIp:集群中节点的ip地址
     * nodePort:节点的端口
     * @return
     * @throws java.net.UnknownHostException
     */
    public static Client getClient(String clusterName, String nodeIp, int nodePort) throws UnknownHostException {
        //设置集群的名字
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", false)
//		        .put("number_of_shards", 1)
//		        .put("number_of_replicas", 0)
                .build();

        //创建集群client并添加集群节点地址
        Client client = TransportClient.builder().settings(settings).build()
//				.addTransportAddress(new InetSocketTransportAddress("192.168.200.195", 9370))
//				.addTransportAddress(new InetSocketTransportAddress("192.168.200.196", 9370))
//				.addTransportAddress(new InetSocketTransportAddress("192.168.200.197", 9370))
//				.addTransportAddress(new InetSocketTransportAddress("192.168.200.198", 9370))
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(nodeIp),
                                nodePort));

        return client;
    }

}
