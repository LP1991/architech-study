/********************** 版权声明 *************************
 * 文件名: ThirdMax.java
 * 包名: priv.primo.datastructure.sort
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/23
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.datastructure.sort;

import priv.primo.datastructure.SortUtils;

public class ThirdMax {

    public int thirdMax(int[] nums) {
        int pivot = nums[0];



        return 0;
    }

    public void partition(int[] nums,int start,int end){
        if (start >= end){
            return;
        }
        int j = end;
        int pivot = nums[start];
        for (int i=start+1;i<=end;i++){
            if (nums[i] >= pivot){
                SortUtils.swap(nums,i,j);
                j--;
                i--;
            }
        }
//        if (start)
    }
}
