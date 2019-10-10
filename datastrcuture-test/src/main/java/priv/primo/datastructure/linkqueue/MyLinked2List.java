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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
public class MyLinked2List {

    private Node head;
    private Node tail;
    private int size;

    private static class Node{
        private Node prev,next;
        private int val;

        public Node(int val,Node prev, Node next) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    /** Initialize your data structure here. */
    public MyLinked2List() {
        head = new Node(-1,null,null);
        tail =  new Node(-1,head,null);
        head.next = tail;
        size = 0;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index <0 || index >size || size == 0){
            return -1;
        }

        if (index == 0){
            return head.next.val;
        }

        if (index == size-1){
            return tail.prev.val;
        }

        if (index < size/2){
            Node t = head.next;
            for (int i = 0; i < index; i++) {
                t = t.next;
            }
            return t.val;
        }else {
            Node t = tail.prev;
            for (int i = size -1; i > index; i--) {
                t = t.prev;
            }
            return t.val;
        }
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node t = new Node(val,head,head.next);
        head.next.prev = t;
        head.next = t;
        size ++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node t = new Node(val,tail.prev,tail);
        tail.prev.next = t;
        tail.prev = t;
        size ++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index <0 || index >size){
            return;
//            throw new IndexOutOfBoundsException();
        }

        if (index == 0){
            addAtHead(val);
            return;
        }

        if (index == size){
            addAtTail(val);
            return;
        }

        if (index < size/2){
            Node prev = head.next;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            Node n = new Node(val,prev,prev.next);
            prev.next.prev = n;
            prev.next = n;
        }else {
            Node next = tail.prev;
            for (int i = size-1; i > index ; i--) {
                next = next.prev;
            }
            Node n = new Node(val,next.prev,next);
            next.prev.next = n;
            next.prev = n;
        }
        size ++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index <0 || index >=size || size ==0){
//            throw new IndexOutOfBoundsException();
            return;
        }

        if (index == 0){
            head = head.next;
            head.prev = null;
            size--;
            return;
        }else if (index == size -1){
            tail = tail.prev;
            tail.next = null;
            size--;
            return;
        }

        if (index < size/2){
            Node t = head.next;
            for (int i = 0; i < index; i++) {
                t = t.next;
            }
            t.prev.next = t.next;
            t.next.prev = t.prev;
        }else {
            Node t = tail.prev;
            for (int i = size -1 ;i>index ;i--){
                t = t.prev;
            }
            t.prev.next = t.next;
            t.next.prev = t.prev;
        }
        size --;
    }
/*
["addAtHead","addAtHead","deleteAtIndex","addAtIndex","addAtTail","addAtIndex","addAtTail","addAtHead","addAtHead","addAtHead","addAtTail","addAtTail","addAtHead","addAtTail","addAtTail","addAtHead","addAtTail","deleteAtIndex","addAtTail","addAtTail","get","addAtIndex","addAtHead","get","get","addAtHead","get","addAtIndex","addAtTail","addAtIndex","addAtHead","addAtHead","addAtHead","get","addAtHead","addAtIndex","addAtTail","addAtHead","addAtIndex","get","addAtTail","addAtTail","addAtIndex","addAtIndex","addAtHead","addAtHead","get","addAtTail","addAtIndex","addAtIndex","addAtHead","deleteAtIndex","addAtIndex","addAtHead","deleteAtIndex","addAtTail","deleteAtIndex","addAtTail","addAtHead","addAtTail","addAtTail","addAtHead","addAtTail","addAtIndex","deleteAtIndex","addAtHead","addAtHead","addAtHead","addAtTail","get","addAtIndex","addAtTail","addAtTail","addAtTail","deleteAtIndex","get","addAtHead","get","get","addAtTail","deleteAtIndex","addAtTail","addAtIndex","addAtHead","addAtIndex","addAtTail","get","addAtIndex","addAtIndex","addAtHead","addAtHead","get","get","get","addAtIndex","addAtHead","addAtIndex","addAtHead","addAtTail","addAtIndex","get"]
[[38],[45],[2],[1,24],[36],[3,72],[76],[7],[36],[34],[91],[69],[37],[38],[4],[66],[38],[14],[12],[32],[5],[7,5],[74],[7],[13],[11],[8],[10,9],[19],[3,76],[7],[37],[99],[10],[12],[1,20],[29],[42],[21,52],[11],[44],[47],[6,27],[31,85],[59],[57],[4],[99],[13,83],[10,34],[48],[9],[22,64],[69],[33],[5],[18],[87],[42],[1],[35],[31],[67],[36,46],[23],[64],[81],[29],[50],[23],[36,63],[8],[19],[98],[22],[28],[42],[24],[34],[32],[25],[53],[55,76],[38],[23,98],[27],[18],[44,27],[16,8],[70],[15],[9],[27],[59],[40,50],[15],[11,57],[80],[50],[23,37],[12]]
 */
    public static void main(String[] args) {
        MyLinked2List obj = new MyLinked2List();
        String s = "addAtHead,addAtHead,deleteAtIndex,addAtIndex,addAtTail,addAtIndex,addAtTail,addAtHead,addAtHead,addAtHead,addAtTail,addAtTail,addAtHead,addAtTail,addAtTail,addAtHead,addAtTail,deleteAtIndex,addAtTail,addAtTail,get,addAtIndex,addAtHead,get,get,addAtHead,get,addAtIndex,addAtTail,addAtIndex,addAtHead,addAtHead,addAtHead,get,addAtHead,addAtIndex,addAtTail,addAtHead,addAtIndex,get,addAtTail,addAtTail,addAtIndex,addAtIndex,addAtHead,addAtHead,get,addAtTail,addAtIndex,addAtIndex,addAtHead,deleteAtIndex,addAtIndex,addAtHead,deleteAtIndex,addAtTail,deleteAtIndex,addAtTail,addAtHead,addAtTail,addAtTail,addAtHead,addAtTail,addAtIndex,deleteAtIndex,addAtHead,addAtHead,addAtHead,addAtTail,get,addAtIndex,addAtTail,addAtTail,addAtTail,deleteAtIndex,get,addAtHead,get,get,addAtTail,deleteAtIndex,addAtTail,addAtIndex,addAtHead,addAtIndex,addAtTail,get,addAtIndex,addAtIndex,addAtHead,addAtHead,get,get,get,addAtIndex,addAtHead,addAtIndex,addAtHead,addAtTail,addAtIndex,get";
        String arg = "[38],[45],[2],[1,24],[36],[3,72],[76],[7],[36],[34],[91],[69],[37],[38],[4],[66],[38],[14],[12],[32],[5],[7,5],[74],[7],[13],[11],[8],[10,9],[19],[3,76],[7],[37],[99],[10],[12],[1,20],[29],[42],[21,52],[11],[44],[47],[6,27],[31,85],[59],[57],[4],[99],[13,83],[10,34],[48],[9],[22,64],[69],[33],[5],[18],[87],[42],[1],[35],[31],[67],[36,46],[23],[64],[81],[29],[50],[23],[36,63],[8],[19],[98],[22],[28],[42],[24],[34],[32],[25],[53],[55,76],[38],[23,98],[27],[18],[44,27],[16,8],[70],[15],[9],[27],[59],[40,50],[15],[11,57],[80],[50],[23,37],[12]";
        operate(s.split(","),arg.split("],\\["),obj);
    }


    private static void operate(String[] operates,String[] args ,MyLinked2List obj ){
        java.util.LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i =0;i<operates.length;i++) {
            String arg = args[i].replace("[","");
            arg = arg.replace("]","");
            int t,s;
            switch (operates[i]){
                case "addAtHead":
                    t = Integer.parseInt(arg);
                    obj.addAtHead(t);
                    list.addFirst(t);
                    break;
                case "addAtTail":
                    t = Integer.parseInt(arg);
                    obj.addAtTail(t);
                    list.addLast(t);
                    break;
                case "addAtIndex":
                    t = Integer.parseInt(arg.split(",")[1]);
                    s = Integer.parseInt(arg.split(",")[0]);
                    obj.addAtIndex(s,t);
                    if (s <= list.size()){
                        list.add(s,t);
                    }
                    break;
                case "deleteAtIndex":
                    t = Integer.parseInt(arg);
                    obj.deleteAtIndex(t);
                    if (t < list.size()){
                        list.remove(t);
                    }
                    break;
                case "get":
                    t = Integer.parseInt(arg);
                    if (obj.get(t) != list.get(t)) {
                        printList(obj);
                        printList(list);
                        return;
                    }
                    break;
                default: throw new RuntimeException();
            }

            for (int j = 0; j < obj.size; j++) {
                if (obj.get(j) != list.get(j)){
                    printList(obj);
                    printList(list);
                    break;
                }
            }
        }

    }


    private static void printList( java.util.LinkedList obj ){
        System.out.print("LinkedList:");
        for (int i = 0; i < obj.size(); i++) {
            System.out.print(obj.get(i) + " ");
        }
        System.out.println();
    }


    private static void printList( MyLinked2List obj ){
        System.out.print("Linke2List:");
        for (int i = 0; i < obj.size; i++) {
            System.out.print(obj.get(i) + " ");
        }
        System.out.println();
    }
}