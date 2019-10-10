/********************** 版权声明 *************************
 * 文件名: CacheMissTest.java
 * 包名: primo.cachemiss
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/22
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.cachemiss;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import primo.JMHSample_12_Forking;
import primo.calculator.MultithreadCalculator;
import primo.calculator.SinglethreadCalculator;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CacheMissTest {
    @Param({"1000","10000","100000"})
    private int length;

    private int[] numbers;
    private Integer[] integers;

    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        integers = new Integer[length];
        for (int i = 0; i < length; i++) {
            integers[i] = new Integer(i);
        }
    }

    @Benchmark
    public long intTest(){
        return Arrays.stream(numbers).sum();
    }

    @Benchmark
    public long intBoxedTest(){
        return Arrays.stream(integers).parallel().reduce(Integer::sum).get();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CacheMissTest.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(2)
                .build();

        new Runner(opt).run();
    }
}
