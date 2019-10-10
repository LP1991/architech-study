/********************** 版权声明 *************************
 * 文件名: TailRecursion.java
 * 包名: priv.primo.module.juc.tailrecursion
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/20
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.module.juc.tailrecursion;

import java.util.stream.Stream;

@FunctionalInterface
public interface TailRecursion<T> {

    TailRecursion<T> apply();

    default boolean isFinished(){
//        System.out.println("isFinished");
        return false;
    }

    default T get(){
        System.out.println("isFinished");
        throw new RuntimeException("nothing could return.");
    }

    default T invoke(){
        System.out.println("invoke");
        return Stream.iterate(this,TailRecursion::apply)
                .filter(TailRecursion::isFinished)
                .findFirst()
                .get()
                .get();
    }
}
