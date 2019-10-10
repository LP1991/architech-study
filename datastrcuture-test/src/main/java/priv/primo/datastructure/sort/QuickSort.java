/********************** 版权声明 *************************
 * 文件名: QuickSort.java
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
 * priv.primo.datastructure.sort.QuickSort
 * 选择中位数，分2组，大于pivot,和小于pivot
 * 比如，快速排序原本是不稳定的排序方法，但若待排序记录中只有一组具有相同关键码的记录，
 * 而选择的轴值恰好是这组相同关键码中的一个，此时的快速排序就是稳定的。
 * @author: Primo
 * @createTime: 2019/8/20 16:33
 */
public class QuickSort implements Sort{

    public static void main(String[] args) {

        int[] ints = SortUtils.buildRandomIntArray(10);
        int[] intsCopy = Arrays.copyOf(ints, ints.length);

        Arrays.sort(ints);
        new QuickSort().sort(intsCopy);

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
        if (nums == null || nums.length <2){
            return;
        }
//        sortHelper(nums,0,nums.length-1);
        sortThreeWay(nums,0,nums.length-1);

    }

    public void sortThreeWay (int[] nums, int start, int end){
        if (nums == null || start >= end){
            return;
        }
        int pivot = nums[start];
        int lt = start;
        int gt = end;
        int i = start+1;
        while (i<=gt){
            if (nums[i] > pivot){
                SortUtils.swap(nums,i,gt);
                gt--;
            }else if (nums[i] < pivot){
                SortUtils.swap(nums,i,lt);
                lt++;
                i++;
            }else {
                i++;
            }
        }
        sortThreeWay(nums,start,lt-1);
        sortThreeWay(nums,gt+1,end);
    }

    public void sortHelper(int[] nums , int start ,int end){
//        if(end - start <= THRESHOLD - 1) {
//            insertSort(arr, start, end);
//            return;
//        }

        if (start >= end){
            return;
        }
        int median = meidanOf3Num(nums, start, start+(end-start)/2, end);
        SortUtils.swap(nums, start, median);
        int partition = partition2(nums, start, end);
        sortHelper(nums,start,partition-1);
        sortHelper(nums,partition+1,end);
    }

    public int partition(int[] nums,int start,int end){
        int pivot = nums[start];
        int i = start + 1;
        for (int j=i;j<=end;j++){
            if (nums[j] <= pivot){
                SortUtils.swap(nums,i,j);
                i++;
            }
        }
        SortUtils.swap(nums,start,i-1);
        return i-1;
    }

    public int partition2(int[] nums ,int start, int end){
        int pivot = nums[start];

        while(start<end){
            while(start<end && nums[end] > pivot){
                end--;
            }
            nums[start] = nums[end];

            while (start<end && nums[start] <= pivot){
                start++;
            }
            nums[end] = nums[start];

        }
        nums[start] = pivot;
        return start;
    }

    private int meidanOf3Num(int[] arr, int lo, int center, int hi) {
        if (arr[lo] < arr[center]) {
            if(arr[center] < arr[hi]) {
                return center;
            }else {
                return (arr[lo] < arr[hi])?hi:lo;
            }
        }else {
            if(arr[hi] < arr[center]) {
                return center;
            }else {
                return (arr[hi] > arr[lo]) ? hi :lo;
            }
        }
    }
}
