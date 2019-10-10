import java.util.LinkedList;
import java.util.List;

/********************** 版权声明 *************************
 * 文件名: TestLinkedList.java
 * 包名: PACKAGE_NAME
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/15
 * 文件版本：V1.0
 *
 *******************************************************/

public class TestLinkedList {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i,i);
        }
        list.add(7,20);
        System.out.println(list);

    }
}
