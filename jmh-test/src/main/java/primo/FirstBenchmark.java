/********************** 版权声明 *************************
 * 文件名: FirstBenchmark.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  jmhTest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/13
 * 文件版本：V1.0
 *
 *******************************************************/
package primo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * priv.primo.FirstBenchmark
 *官方例子
 * http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
 * @author: Primo
 * @createTime: 2019/7/15 16:12
 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class FirstBenchmark {
//    @Benchmark
//    public int sleepAWhile(){
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    List<String> list = new ArrayList();

    @Benchmark
    @Warmup(iterations = 1, batchSize = 5)
    @Measurement(iterations = 5, batchSize = 5)
    public int batchSize(){
        list.add("1");
        return list.size();
    }

    @TearDown
    public void shutdown(){
        System.out.println("TearDown:"+list.size());
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(FirstBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(5)
                .build();

        new Runner(options).run();

        System.out.println(BigInteger.valueOf(1).isProbablePrime(1));
    }
}
