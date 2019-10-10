/********************** 版权声明 *************************
 * 文件名: Main.java
 * 包名: priv.primo.datastructure
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/25
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        // 默认先保存k个数，认为他们就是最小的，并保存显示其中最大的数，遍历数据不断跟最大数比较
        // 小则替换,再换算出最大数.
//        空判断
        if (input == null || input.length ==0 || k == 0){
            return null;
        }

//       长度判断
        if (k > input.length){
            throw new IllegalArgumentException("k 值不能大于input长度");
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int i=0;i< k;i++){
            int minIndex = i;
            for (int j=i+1;j<input.length;j++){
                if (input[minIndex] > input[j]){
                    minIndex = j;
                }
            }
            if (minIndex != i){
                swap(input,i,minIndex);

            }
//            添加最小值
            result.add(input[i]);
        }
        return result;
    }

    public static void swap(int[] nums, int i, int j) {
        int temp  = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static int[] spliteLine(String line) {
        String[] s = line.split(" ");
        int[] result = new int[s.length];
        for (int i=0;i<s.length;i++){
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }


    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            int k = Integer.parseInt(in.nextLine());
//            int[] input = spliteLine(line);
//            ArrayList<Integer> res = GetLeastNumbers_Solution(input, k);
//            System.out.println(res);
//        }
        List list = new ArrayList();
        list.add(0,1);
        System.out.println(list.get(1));
    }



}
