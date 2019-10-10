/********************** 版权声明 *************************
 * 文件名: ListADT.java
 * 包名: priv.primo.list
 * 版权:	杭州华量软件  architectstudy
 * 职责: \
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/13
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.list;

public interface ListADT<E> {
    int size();

    void add(E e);
    void add(E e,int index);

    E remove(int index);

    E get(int index);

    void set(E e, int index);

    boolean contains(E e);

    boolean isEmpty();

    int indexOf(E e);
}
