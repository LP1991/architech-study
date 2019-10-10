/********************** 版权声明 *************************
 * 文件: EsUtil.java
 * 包名: com.hlframe.modules.ext.utils
 * 版权: 杭州华量软件 
 * 职责:
 ********************************************************
 *
 * 创建者：Primo   创建时间：2018/7/17 16:39
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.es;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** 
 * com.hlframe.modules.ext.utils.EsUtil 
 * 
 * @author: Primo
 * @createTime: 2018/7/17 16:39
 */
public class EsUtil {
	
	private static Logger logger = LoggerFactory.getLogger(EsUtil.class);
	private static EsClientProvider provider = new EsClientProvider();
	
	static{
		provider.init();
	}

	/**
	 * @方法名称: getClient
	 * @实现功能: 获取elasticSearch 连接客户端
	 * @param
	 * @return
	 * @Create by Primo at 2018/7/17 16:40
	 * @throws Exception
	 */
	public static Client getClient() throws Exception {
		Client client = provider.get();
    	if(null==client){
    		provider.init();
    		client = provider.get();
    	}
    	return client;
	}

	/**
	 * @方法名称: createEsClient
	 * @实现功能: 创建ES客户端连接
	 * @param
	 * @return
	 * @Create by Primo at 2018/7/17 16:40
	 * @throws
	 */
	public static Client createEsClient() throws Exception {
        try {
            //创建ES客户端连接
            Client client = TransportClient.builder().addPlugin(DeleteByQueryPlugin.class).build()
                    .addTransportAddress(new InetSocketTransportAddress(
                    		InetAddress.getByName( "10.147.110.41"),
                    		Integer.parseInt("9300")));
            return client;
		} catch (Exception e) {
			logger.error("ElasticSearch client error!", e);
		}
        return null;
    }
	

	/**
	 * @方法名称: checkIndex
	 * @实现功能: 检查索引是否存在
	 * @param client ES客户端
	 * @param indexName 索引名称
	 * @return
	 * @Create by Primo at 2018/7/18 10:33
	 * @throws
	 */
	public static boolean checkIndex(Client client, String indexName) throws Exception {
		IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);
		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
		return inExistsResponse.isExists();
	}

	/**
	 * @方法名称: creataIndex
	 * @实现功能: 创建索引库
	 * @param client ES客户端
	 * @param indexName  索引名称
	 * @return
	 * @Create by Primo at 2018/7/18 10:34
	 * @throws
	 */
	public static void creataIndex(Client client, String indexName) throws Exception {
		Assert.hasText(indexName);
		// 检查索引是否存在
		if (checkIndex(client, indexName)) {
			logger.info("Index[" + indexName + "] is already exists!");
			return;
		}

		// 创建索引 方法1
//		CreateIndexResponse cIndexResponse = client.admin().indices().prepareCreate(indexName).setSettings("{\"analysis\":{\"analyzer\":{\"ik\":{\"tokenizer\":\"ik\"}}}}").execute().actionGet();
		CreateIndexResponse cIndexResponse = client.admin().indices().prepareCreate(indexName).execute().actionGet();
		// 方法2
		// CreateIndexResponse cIndexResponse = client.admin().indices().create(new CreateIndexRequest("pjd")).actionGet();
		if (cIndexResponse.isAcknowledged()) {
			logger.info("Success Create index[" + indexName + "]!");
		} else {
			logger.error("Fail to create index[" + indexName + "]!");
		}
		//新建mapping
	}

	/**
	 * @方法名称: deleteIndex
	 * @实现功能: 删除索引
	 * @param client ES客户端
	 * @param indexName	索引名称
	 * @return
	 * @Create by Primo at 2018/7/18 10:34
	 * @throws
	 */
	public static void deleteIndex(Client client, String indexName) throws Exception {
		Assert.hasText(indexName);
		// 检查索引是否存在
		if (checkIndex(client, indexName)) {
			//删除索引
			DeleteIndexResponse deleteResponse = client.admin().indices().prepareDelete(indexName).execute().actionGet();
			if (deleteResponse.isAcknowledged()) {
				logger.info("delete index {} successfully!", indexName);
			} else {
				logger.error("Fail to delete index {}", indexName);
			}
		}
	}

	/**
	 * @方法名称: deleteType
	 * @实现功能: 删除索引的类型; note : sudo bin/plugin install delete-by-query .
	 * @param client ES客户端
	 * @param indexName	索引名
	 * @param typeName	类型名
	 * @return
	 * @Create by Primo at 2018/7/18 10:35
	 * @throws
	 */
	public static void deleteType(Client client, String indexName, String typeName) throws Exception {
//		client.prepareDelete().setIndex(indexName).setType(typeName).execute().actionGet();
		String deleteByquery = "{\"query\": {\"match_all\": {}}}";
		DeleteByQueryResponse response =  new DeleteByQueryRequestBuilder(client,
				DeleteByQueryAction.INSTANCE)
				.setIndices(indexName)
				.setTypes(typeName)
				.setSource(deleteByquery)
				.execute()
				.actionGet();
	}

	/**
	 * @方法名称: deleteType
	 * @实现功能: 删除索引的类型. note : sudo bin/plugin install delete-by-query .
	 * @param client ES客户端
	 * @param indexName	索引名
	 * @param typeName	类型名
	 * @return
	 * @Create by Primo at 2018/7/18 10:36
	 * @throws
	 */
	public static void deleteById(Client client, String indexName, String typeName,String id) throws Exception {
//		client.prepareDelete().setIndex(indexName).setType(typeName).execute().actionGet();
		String deleteByquery = "{\"query\": {\"match\": { \"id\": \""+id+"\"}}}";
		System.out.println(deleteByquery);
		DeleteByQueryResponse response =  new DeleteByQueryRequestBuilder(client,
				DeleteByQueryAction.INSTANCE)
				.setIndices(indexName)
				.setTypes(typeName)
				.setSource(deleteByquery)
				.execute()
				.actionGet();
	}

	/**
	 * @方法名称: queryPageFromSize
	 * @实现功能: FromSize模式下获取指定分页的数据
	 * @param indexName	索引名
	 * @param typeName	类型名
	 * @param pageSize	每页数量
	 * @param pageNum	页数
	 * @param qb		查询条件
	 * @return
	 * @Create by Primo at 2018/7/18 10:39
	 * @throws
	 */
	public static List<Map<String, Object>> queryPageFromSize(Client client, String indexName, String typeName,int pageSize, int pageNum, QueryBuilder qb) throws Exception {
		Assert.isTrue(pageNum > 0);
		int startNum = (pageNum - 1) * pageSize;
		// 定义每页数据
		pageSize = pageSize <= 0 ? 20
				: pageSize;
		// 数据总量
		// long count =
		// client.prepareCount(indexName).setTypes(TypeName).execute().actionGet().getCount();
		long count = client.prepareSearch(indexName).setTypes(typeName).execute().actionGet().getHits().totalHits();
		// 分页记录不能超过总数
		Assert.isTrue(startNum <= count);

		if (null == qb) {
			qb = QueryBuilders.matchAllQuery();
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 分页查询 添加到列表
		SearchResponse response = client.prepareSearch(indexName).setTypes(typeName).setQuery(qb).setFrom(startNum)
				.setSize(pageSize).execute().actionGet();
		for (SearchHit sh : response.getHits().getHits()) {
			dataList.add(sh.getSource());
		}
		return dataList;
	}

	/**
	 * @方法名称: queryPageScroll
	 * @实现功能:  scroll模式下获取指定分页的数据
	 * @param indexName	索引名
	 * @param TypeName	类型名
	 * @param pageSize	每页数量
	 * @param pageNum	页数
	 * @param qb		查询条件
	 * @return
	 * @Create by Primo at 2018/7/18 10:41
	 * @throws
	 */
	public static List<Map<String, Object>> queryPageScroll(Client client, String indexName, String TypeName,int pageSize, int pageNum, QueryBuilder qb) throws Exception {
		Assert.isTrue(pageNum > 0);
		int startNum = (pageNum - 1) * pageSize;
		pageSize = pageSize <= 0 ? Integer.parseInt( "20"): pageSize;

		if (null == qb) { // 默认全部检索
			qb = QueryBuilders.matchAllQuery();
		}
		// scroll模式 检索
		SearchResponse response = client.prepareSearch(indexName)
				// .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
				 //设置排序
				.setTypes(TypeName).setSearchType(SearchType.SCAN).setQuery(qb).setScroll(new TimeValue(60000))
				.setSize(pageSize).setFrom(startNum).execute().actionGet();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (SearchHit sh : response.getHits().getHits()) {
			dataList.add(sh.getSource());
		}
		return dataList;
	}







	/**
	 * @方法名称: createMetaDataMapping
	 * @实现功能:  创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引 ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
	 * @param indices 索引名称；
	 * @param mappingType 索引类型
	 * @return
	 * @Create by Primo at 2018/7/18 10:41
	 * @throws
	 */
	public static void createMetaDataMapping(Client client,String indices,String mappingType)throws Exception{
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
				.startObject(mappingType)
				.startObject("properties")
//				.startObject("metadataCode").field("type", "string")
//				.field("analyzer","ik").field("search_analyzer","caseSensitive").endObject()
				.startObject("metadataName").field("type", "string")
				.field("analyzer","ik").field("search_analyzer","ik_max_word").endObject()
				.startObject("metadataDesc").field("type", "string")
				.field("analyzer","ik").field("search_analyzer","ik_max_word").endObject()
				.startObject("contextNamePath").field("type", "string")
				.field("analyzer","ik").field("search_analyzer","ik_max_word").endObject()
				.startObject("effectTime").field("type","date").field("locale",Locale.CHINA).endObject()
				.startObject("deadTime").field("type","date").field("locale",Locale.CHINA).endObject()
				.startObject("createDate").field("type","date").field("locale",Locale.CHINA).endObject()
				.startObject("updateDate").field("type","date").field("locale",Locale.CHINA).endObject()
				.endObject()
				.endObject()
				.endObject();
		PutMappingRequest mapping = Requests.putMappingRequest(indices).type(mappingType).source(builder);
		PutMappingResponse mappingResponse
				= client.admin().indices().putMapping(mapping).actionGet();
		if (mappingResponse.isAcknowledged()) {
			logger.info("Success modify mapping[" + mappingType+"/]!");
		} else {
			logger.error("Fail to modify mapping[" + mappingType+"/]!");
		}
	}


	public static void main(String[] args) throws Exception {
		String index =  "gx_metadata";
		Client client = createEsClient();
//		deleteIndex(client,index);
//		creataIndex(client,index);
		createMetaDataMapping(client,index,"metadata");

//		deleteById(client,index,"table");
//		deleteIndex(client,"dc_metadata");
//		resetIndex();
	}



}
