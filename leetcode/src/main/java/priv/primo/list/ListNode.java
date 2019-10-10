/********************** 版权声明 *************************
 * 文件名: ListNode.java
 * 包名: priv.primo.list
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/9/3
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.list;

public class ListNode {
          int val;
     ListNode next;
      ListNode(int x) { val = x; }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
