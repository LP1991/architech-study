/********************** 版权声明 *************************
 * 文件名: List.java
 * 包名: priv.primo.datastructure.linkqueue
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo   创建时间：2019/8/27
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.datastructure.linkqueue;

public interface List<T> {
    void add(T t);

    void add(T t, int index);

    T remove(int index);

    int indexOf(T t);

    void set(T t, int index);

    T get(int index);

    int size();

    boolean isEmpty();
}
