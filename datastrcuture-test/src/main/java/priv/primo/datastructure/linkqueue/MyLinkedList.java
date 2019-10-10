/********************** 版权声明 *************************
 * 文件名: MyLinkedList.java
 * 包名: priv.primo.datastructure.linkqueue
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/27
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.datastructure.linkqueue;

public class MyLinkedList<T> implements List<T>{

    private Node head = null;
    private int size;

    private static class Node<T>{
        private Node next;
        private T val;

        public Node( T val,Node next) {
            this.next = next;
            this.val = val;
        }
    }


    @Override
    public void add(T t) {
        insertTail(t);
        size++;
    }

    @Override
    public void add(T t, int index) {
        if (index<0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            insertHead(t);
        }

        if (index == size){
            insertTail(t);
        }

        Node head = this.head;

        for (int i = 0; i < index -1 ; i++) {
            head = head.next;
        }

        head.next = new Node(t,head.next);
        size++;
    }

    @Override
    public T remove(int index) {
        if (index<0 || index > size-1){
            throw new IndexOutOfBoundsException();
        }
        Node<T> removed = null;
        if (index ==0){
            removed = removeHead();
        } else if (index == size -1){
            removed = removeTail();
        }else {
            Node<T> head = this.head;
            for (int i = 0; i < index -1; i++) {
                head = head.next;
            }
            removed = head.next;
            head.next = head.next.next;
        }
        size--;
        return removed.val;
    }

    @Override
    public int indexOf(T t) {
        return 0;
    }

    @Override
    public void set(T t, int index) {
        if (index<0 || index > size-1){
            throw new IndexOutOfBoundsException();
        }

        Node head = this.head;
        for (int i = 0; i < index; i++) {
            head = head.next;
        }
        head.val = t;
    }

    @Override
    public T get(int index) {
        if (index<0 || index > size-1){
            throw new IndexOutOfBoundsException();
        }

        Node<T> head = this.head;
        for (int i = 0; i < index; i++) {
            head = head.next;
        }
        return head.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size ==0 ;
    }

    private void insertHead(T t){
        head = new Node(t,head);
    }

    private void insertTail(T t){
        Node head = this.head;
        if (head == null){
            insertHead(t);
            return;
        }
        while (head.next != null){
            head = head.next;
        }
        head.next = new Node(t,null);
    }

    private Node<T> removeHead(){
        Node<T> head = this.head;
        this.head = head.next;
        return head;
    }

    private Node<T> removeTail(){
        Node<T> head = this.head;

        while (head.next != null){
            head = head.next;
        }
        Node<T> removed = head;
        head = null;
        return removed;
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();

        for (int i =0 ;i< 10;i++){
            linkedList.add(i);
        }
        System.out.println("size:"+ linkedList.size());
        for (int i = 0; i < linkedList.size; i++) {
            System.out.println("第"+i+"个元素:"+ linkedList.get(i));
        }

//        for (int i =0 ;i < 100;i= i+5){
//            linkedList.remove(i);
//        }

        linkedList.remove(5);
        linkedList.remove(0);
        linkedList.remove(linkedList.size()-1);
        linkedList.remove(linkedList.size()-1);

        for (int i = 0; i < linkedList.size; i++) {
            System.out.println("第"+i+"个元素:"+ linkedList.get(i));
        }

//        linkedList.add(17,7);
        linkedList.add(20,1);
        linkedList.add(20,linkedList.size());

        for (int i = 0; i < linkedList.size; i++) {
            System.out.println("第"+i+"个元素:"+ linkedList.get(i));
        }
    }
}
