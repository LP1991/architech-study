/********************** 版权声明 *************************
 * 文件名: isPalindrome234.java
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

public class isPalindrome234 {

    public static int mid(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            if (fast == null) {
                break;
            }
            slow = slow.next;
        }
        return slow.val;
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode half = head;
        ListNode midHead = head;
        while (head != null && head.next != null) {
            head = head.next.next;
            ListNode next = half.next;
            if (head == null) {
                break;
            }
            half.next = next.next;
            next.next = midHead;
            midHead = next;
            print(midHead);
        }
        half = half.next;
        if (head != null) {
            midHead = midHead.next;
        }

        while (half != null) {
            if (midHead.val != half.val) {
                return false;
            }
            midHead = midHead.next;
            half = half.next;
        }
        return true;
    }

    private static void print(ListNode list) {
        while (list != null) {
            System.out.print(list.val + " -> ");
            list = list.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println(isPalindrome(head));
    }
}
