/********************** 版权声明 *************************
 * 文件名: InsertSort.java
 * 包名: priv.primo.datastructure.sort
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/20
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.datastructure.sort;

import priv.primo.datastructure.SortUtils;

import java.util.Arrays;

public class InsertSort implements Sort {
    public static void main(String[] args) {

        int[] ints = SortUtils.buildRandomIntArray(10);
        int[] intsCopy = Arrays.copyOf(ints, ints.length);

        Arrays.sort(ints);
        new InsertSort().sort(intsCopy);

        SortUtils.printArray(ints);
        SortUtils.printArray(intsCopy);

        if (SortUtils.isEquals(ints,intsCopy)) {
            System.out.println("array is equal");
        }else {
            System.out.println("array is not equal");
        }
    }

    @Override
    public void sort(int[] nums) {
        if (nums == null || nums.length < 2){
            return;
        }

        for (int i=1;i<nums.length;i++){
            int elem = nums[i];
            int j = i-1;
            while (j>=0 && elem < nums[j]){
                nums[j+1] = nums[j];
                j--;
            }
            nums[j + 1] = elem;
        }
    }
}
