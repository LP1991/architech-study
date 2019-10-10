/********************** 版权声明 *************************
 * 文件名: SelectSort.java
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

/**
 * priv.primo.datastructure.sort.SelectSort
 *
 * 每次选择最小值跟第i个位置交换
 * @author: Primo
 * @createTime: 2019/8/20 15:57
 */
public class SelectSort implements Sort{

    public static void main(String[] args) {

        int[] ints = SortUtils.buildRandomIntArray(100);
        int[] intsCopy = Arrays.copyOf(ints, ints.length);

        Arrays.sort(ints);
        new SelectSort().sort(intsCopy);

        if (SortUtils.isEquals(ints,intsCopy)) {
            System.out.println("array is equal");
        }else {
            System.out.println("array is not equal");
        }
    }

    //最坏和平均O(n^2)
    //最好O(n^2)
    @Override
    public void sort(int[] nums) {
        if (nums == null || nums.length < 2){
            return;
        }

        for (int i=0;i< nums.length -1;i++){
            int minIndex = i;
            for (int j=i+1;j<nums.length;j++){
                if (nums[minIndex] > nums[j]){
                    minIndex = j;
                }
            }
            if (minIndex != i){
                SortUtils.swap(nums,i,minIndex);
            }
        }
    }
}
