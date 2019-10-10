/********************** 版权声明 *************************
 * 文件名: LinkNodeAdd.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/13
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo;

public class LinkNodeAdd {
    /**
     * [2,4,3]
     * [5,6,4]
     * @param
     * @return
     * @Create by Primo at 2019/8/13 12:43
     * @throws
     */
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.setNext(new ListNode(4)).setNext(new ListNode(3));
        ListNode l2 = new ListNode(5);
        l2.setNext(new ListNode(6)).setNext(new ListNode(4));

        ListNode listNode = addTwoNumbers(l1, l2);
        while (listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }

    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        int carry = 0;
        int remainder = 0;
        ListNode currentIndex = null;
        while(l1 != null && l2 != null){
            remainder = (l1.val + l2.val + carry) % 10;
            carry = (l1.val + l2.val + carry)/10;

            ListNode temp = new ListNode(remainder);

            process(result,currentIndex,temp);
            l1 = l1.next;
            l2 = l2.next;
        }

        while(l1 != null){
            remainder = (l1.val + carry) % 10;
            carry = (l1.val + carry)/10;

            ListNode temp = new ListNode(remainder);
            process(result,currentIndex,temp);
            l1 = l1.next;
        }

        while(l2 != null){
            remainder = (l2.val + carry) % 10;
            carry = (l2.val + carry)/10;

            ListNode temp = new ListNode(remainder);
            process(result,currentIndex,temp);
            l2 = l2.next;
        }

        if(carry > 0){
            ListNode temp = new ListNode(carry);
            process(result,currentIndex,temp);

        }
        return result;
    }

    private  static void process(ListNode result,ListNode currentIndex, ListNode temp){
        if(result == null){
            result = temp;
            currentIndex = result;
        }else {
            currentIndex.next = temp;
            currentIndex = temp;
        }
    }
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    public ListNode setNext(ListNode next){
        this.next = next;
        return next;
    }
}