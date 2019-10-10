/********************** 版权声明 *************************
 * 文件: EsClientProvider.java
 * 包名: com.hlframe.modules.ext.utils
 * 版权: 杭州华量软件
 * 职责:
 ********************************************************
 *
 * 创建者：Primo   创建时间：2018/7/18 10:32
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * com.hlframe.modules.ext.utils.EsClientProvider
 * @职责说明: Es客户端连接Provider
 * @author: Primo
 * @createTime: 2018/7/18 10:32
 */
public class EsClientProvider {

	private static final Logger logger = LoggerFactory.getLogger(EsClientProvider.class);
	
	private TransportClient client = null;		//连接客户端
    private volatile boolean inited = false;	//是否初始化
    
    public TransportClient get() {
        return this.client;
    }
 
    @PreDestroy
    public synchronized void close() {
        if (this.client != null) {
            this.client.close();
        }
    }

    /**
     * @方法名称: init
     * @实现功能: 初始化client
     * @param
     * @return
     * @Create by Primo at 2018/7/18 10:32
     * @throws
     */
    @PostConstruct
    public synchronized void init() {
//        if (!inited) {
        try {
        	//初始化配置信息
            Settings settings = Settings.builder()
            		.put("client.transport.sniff",true)	// 客户端嗅探整个集群的状态，把集群中其它机器的ip地址自动添加到客户端中，并且自动发现新加入集群的机器
            		.put("client", true)
            		.put("cluster.name", "elasticsearch")	//集群名称
                		.build();
                TransportClient client = TransportClient.builder().settings(settings).addPlugin(DeleteByQueryPlugin.class).build();
                this.client = client;
 
                //设置客户端地址
            client.addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName( "10.147.110.41"),
                    Integer.parseInt( "9300")
            ));
            this.inited = true;
        } catch (UnknownHostException e) {
        	logger.error(String.format("init search client err:=>msg:[%s]", e.getMessage()), e);
            if (client != null) {
                this.client.close();
            }
        }
//        }
    }
    
    public boolean inited(){
    	return inited;
    }
}
