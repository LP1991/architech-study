/********************** 版权声明 *************************
 * 文件名: StreamTest.java
 * 包名: priv.primo.module.juc
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/3
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.module.juc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamTest {
    /**
     * 4      4
     * 10     10
     * 56     32
     * 100    36
     * 1000   32
     * @param args
     */
    public static void main(String[] args) {
//        创建stream数量
//        List<Integer> list = new ArrayList(){{
//            for (int i = 0; i < 120; i++) {
//                this.add(0);
//            }
//        }};
////        使用reduce的identity属性查看任务数量
//        Integer reduce = list.stream().parallel().reduce(1, Integer::sum, Integer::sum);
//        System.out.println(reduce);


        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n->n.add(BigInteger.ONE));
        System.out.println(integers.findFirst().get());
//        show("integers",integers)
    }
}
