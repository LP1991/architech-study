/********************** 版权声明 *************************
 * 文件名: BubbleSort.java
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

public class BubbleSort {
    public static void main(String[] args) {

        int[] ints = SortUtils.buildRandomIntArray(10);
        int[] intsCopy = Arrays.copyOf(ints, ints.length);

        Arrays.sort(ints);
        new BubbleSort().sort(intsCopy);

        if (SortUtils.isEquals(ints,intsCopy)) {
            System.out.println("array is equal");
        }else {
            System.out.println("array is not equal");
        }
    }

//    time O(n)
//    spaceO(1)
    public void sort(int[] nums){
        if (nums == null || nums.length < 2){
            return;
        }
        boolean flag = false;
        for (int i =0 ;i<nums.length;i++){
            flag = false;
            for (int j=0;j<nums.length - i - 1;j++){
                if (nums[j] > nums[j+1]){
                    swap(nums,j,j+1);
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp  = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
