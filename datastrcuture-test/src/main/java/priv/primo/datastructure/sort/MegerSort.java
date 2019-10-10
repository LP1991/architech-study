/********************** 版权声明 *************************
 * 文件名: MegerSort.java
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

public class MegerSort {

    public static void main(String[] args) {

        int[] ints = SortUtils.buildRandomIntArray(100);
        int[] intsCopy = Arrays.copyOf(ints, ints.length);

        Arrays.sort(ints);
        new MegerSort().sort(intsCopy);

        SortUtils.printArray(ints);
        SortUtils.printArray(intsCopy);

        if (SortUtils.isEquals(ints,intsCopy)) {
            System.out.println("array is equal");
        }else {
            System.out.println("array is not equal");
        }
    }

    public void sort(int[] nums){
        sortHelper(nums,0,nums.length-1);
    }

    public void sortHelper(int[] nums,int start,int end){
        if (start >= end){
            return;
        }

        int mid = start + (end -start)/2;

        sortHelper(nums,start,mid);
        sortHelper(nums,mid+1,end);

        meger(nums,start,mid,mid+1,end);
    }

    private void meger(int[] nums, int s1, int e1, int s2, int e2) {
        int i = s1;
        int j = s2;
        int k = 0;
        int[] temp = new int[e1-s1+e2-s2+2];

        while(i<=e1 && j<=e2){
            if (nums[i]<=nums[j]){
                temp[k++] = nums[i++];
            }else {
                temp[k++] = nums[j++];
            }
        }

        while (i<=e1){
            temp[k++] = nums[i++];
        }
        while (j<=e2){
            temp[k++] = nums[j++];
        }

        for (k=0;k<temp.length;k++){
            nums[s1+k] = temp[k];
        }
    }
}
