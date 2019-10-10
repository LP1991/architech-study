/********************** 版权声明 *************************
 * 文件名: LineList.java
 * 包名: priv.primo.list
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/13
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.list;

import java.util.Arrays;

public class LineList<E> implements ListADT<E>{
    private E[] elements;
    private int size;

    private int capacity;

    public LineList(int capacity) {
        this.capacity = capacity;
        elements = (E[]) new Object[capacity];
    }

    public LineList() {
        this.capacity = 10;
        elements = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
//        扩容
        add(e,size);
    }

    @Override
    public void add(E e, int index) {
        if (index>size || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }
//        扩容
        if (size == capacity){
            grow(capacity*2);
        }

        for (int i = size-1; i > index ; i--) {
            elements[i+1] = elements[i];
        }
        elements[index] = e;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index>=size || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }

        //        收容
        if (size < capacity/2 ){
            grow(capacity/2);
        }

        E removed = elements[index];

        for (int i = index; i < size-1; i++) {
            elements[i] = elements[i+1];
        }
        size --;
        return removed;
    }

    @Override
    public E get(int index) {
        return elements[index];
    }

    @Override
    public void set(E e, int index) {
        elements[index] = e;
    }

    @Override
    public boolean contains(E e) {
        for (E o :elements)
            if (e.equals(o))
                return true;
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i <size; i++) {
            if (e.equals(elements[i]))
                return i;
        }

        return -1;
    }

    private void grow(int capacity){
        this.capacity = capacity;
        elements = Arrays.copyOf(elements,capacity);
    }

    public static void main(String[] args) {
        LineList<Integer> list = new LineList<>(16);
        for (int i = 0; i < 100; i++) {
            list.add(i,i);
        }

        for (int i = 0; i < list.size; i++) {
            System.out.println("get element " + i + "th = " + list.get(i));
        }

        for (int i=0; i<list.size;i=i+2){
            list.remove(i);
        }
        System.out.println("after removed");
        for (int i = 0; i < list.size; i++) {
            System.out.println("after removed, get element " + i + "th = " + list.get(i));
        }

        for (int i = 0; i < 4; i++) {
            list.remove(i);
        }

        System.out.println(list.contains(89));
        System.out.println(list.capacity);
        System.out.println(list.size);
        System.out.println(list.isEmpty());
    }
}
