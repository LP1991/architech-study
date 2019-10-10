/********************** 版权声明 *************************
 * 文件名: EsKeyCheck.java
 * 包名: priv.primo.primo.priv.es
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/5/21
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.Arrays;

public class EsKeyCheck {
    public static void main(String[] args) {
        String param = "S_船检签发证书情况_运输装备_水路交通_运行管理事项";
        String contextPath = "";

        try {
            searchMetadata(param,contextPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchMetadata(String param, String contextPath) throws Exception {
        QueryBuilder qb = QueryBuilders.boolQuery();
//        QueryBuilders.disMaxQuery()
        Client client = EsUtil.getClient();
/**
 *

 if ("*".equals(param) || StringUtils.isBlank(param)) {
 qb = ((BoolQueryBuilder) qb).must(QueryBuilders.matchAllQuery());// 全文检索
 } else {
 // a wildcard term should not start with one of the wildcards * or ?
 String[] params = param.split(" ");
 //          添加查询
 for (int i = 0; i < params.length; i++) {
 if (params[i].length()>0) {
 QueryBuilder queryBuilder = null;
 //                判断如果出现.则使用正则表达式
 //                        queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("metadataCode",params[i]))
 //                                .should(QueryBuilders.wildcardQuery("metadataName",params[i]));
 queryBuilder = QueryBuilders.multiMatchQuery(params[i].toLowerCase(), "metadataCode", "metadataName", "metadataDesc")
 .analyzer("caseSensitive");
 ((BoolQueryBuilder) qb).must(queryBuilder);
 }
 }
 }
 */
//    @Modify by Primo at 2019/2/21 13:59 Es 检索调整
        if ("*".equals(param)) {
            qb = ((BoolQueryBuilder) qb).must(QueryBuilders.matchAllQuery());// 全文检索
        }else{
//            String upperParam = "*"+param+"*";
            String upperParam = param;
//            QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("metadataCode",upperParam))
//                    .should(QueryBuilders.wildcardQuery("metadataName",upperParam));
            DisMaxQueryBuilder add = QueryBuilders.disMaxQuery().add(QueryBuilders.matchQuery("metadataCode", upperParam)).add(QueryBuilders.matchQuery("metadataName", upperParam)).tieBreaker(0.7f);
//            QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("metadataCode",upperParam))
//                    .should(QueryBuilders.wildcardQuery("metadataName",upperParam));
//                    .should(QueryBuilders.wildcardQuery("metadataDesc",upperParam));
            ((BoolQueryBuilder) qb).must(add);
        }

//      添加全路径判断
        if (contextPath != null && contextPath.length()>0){
            ((BoolQueryBuilder) qb).must(QueryBuilders.prefixQuery("contextPath",contextPath));
        }

//      添加系统运行状态 by guofl
        ((BoolQueryBuilder) qb).must(QueryBuilders.prefixQuery("status","1"));

//         添加权限查询/ 1.只能查询自己的; 2. 别人的数据只能查看已发布的. 3. 管理员角色可以查看所有, 调整为: 去除权限, 所有发布的元数据都可以查看, pjd 2019-02-14
//        判断是不是管理员角色
//        if (!UserUtils.getUser().isAdmin()) {
//            添加用户ID过滤
//            QueryBuilder queryBuilder = QueryBuilders.matchQuery("createBy.id",UserUtils.getUser().getId());
//            添加版本过滤
        QueryBuilder pubVersion = QueryBuilders.existsQuery("curVersion");
//            用户ID过滤 or  版本过滤
//            BoolQueryBuilder should = QueryBuilders.boolQuery().should(queryBuilder).should(pubVersion);
        BoolQueryBuilder should = QueryBuilders.boolQuery().should(pubVersion);
//            关联到主查询
        qb = ((BoolQueryBuilder) qb).must(should);
//        }
//      方法改造，添加分页查询 @Modify by Primo at 2018/8/14 19:23
//       获取pageNo和pageSize
        int pageSize = 100;
        int startNum = 0;
//
//      查询请求
        SearchRequestBuilder requestBuilder = client.prepareSearch("gx_metadata").setTypes("metadata").setQuery(qb).setFrom(startNum).setSize(pageSize);
//        查询返回结果
        SearchResponse response = requestBuilder.execute().actionGet();
        long count = response.getHits().getTotalHits();
        //      总数为0 直接返回数据
//        查询结果遍历
         Arrays.stream(response.getHits().getHits())
                .map(SearchHit::getSource).forEach(System.out::println);
////      设置返回值
//        page.setList(mapList);
//        page.setCount(count);
//        return page;
    }
}
