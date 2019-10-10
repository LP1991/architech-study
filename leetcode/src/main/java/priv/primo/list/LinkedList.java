/********************** 版权声明 *************************
 * 文件名: LinkedList.java
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


/**
 * priv.primo.list.LinkedList
 *  todo
 * @author: Primo
 * @createTime: 2019/8/13 19:48
 */
public class LinkedList<E> implements ListADT<E> {
    private Node head ;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
        Node<E> t = head;
        if (t == null){
            insertHead(e);
            size ++;
            return;
        }
        while (t.next !=null){
            t = t.next;
        }
//        入队尾
        t.next = new Node(null,e);
        size ++;
    }

    @Override
    public void add(E e, int index) {
//        异常判断
        if (index< 0 || index >size){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            head = new  Node(head,e);
        }else {
            Node t = head;
            for (int i=0;i < index-1;i++){
                t = t.next;
            }
            Node next = t.next;
            t.next = new Node(next,e);
        }
        size ++;
    }

    private void insertHead(E e){
        head = new  Node(head,e);
    }

    @Override
    public E remove(int index) {
        //        异常判断
        if (index< 0|| index >size){
            throw new IndexOutOfBoundsException();
        }
        Node t = head;
        for (int i=0;i < index-1;i++){
            t = t.next;
        }
        Node removed = t.next;
        t.next = t.next.next;
        size --;
        return (E)removed.e;
    }

    @Override
    public E get(int index) {
        Node<E> t = head;
        for (int i = 0; i < index; i++) {
            t =t.next;
        }

        return t.e;
    }

    @Override
    public void set(E e, int index) {
        //        异常判断
        if (index<= 0|| index >size){
            throw new IndexOutOfBoundsException();
        }
        Node t = head;
        for (int i=0;i < index-1;i++){
            t = t.next;
        }
        t.next = new Node<>(t.next,e);
    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size ==0;
    }

    @Override
    public int indexOf(E e) {
        return 0;
    }

    private class Node<E>{
        private Node next;
        private E e;

        public Node() {
        }

        public Node(Node next, E e) {
            this.next = next;
            this.e = e;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i,i);
        }
        list.add(20, 7);
        for (int i =0 ;i<list.size;i++){
            System.out.println(list.get(i));
        }
//        for (int i = 0; i < 50; i =i+8) {
//            list.remove(i);
//        }
//        for (int i =0 ;i<list.size;i++){
//            System.out.println("after removed:"+list.get(i));
//        }
//
//        list.set(9,1);
//        list.set(101,11);
//
//        for (int i =0 ;i<list.size;i++){
//            System.out.println("after inserted:"+list.get(i));
//        }
    }
}
