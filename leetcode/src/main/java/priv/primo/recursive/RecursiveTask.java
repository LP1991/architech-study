/********************** 版权声明 *************************
 * 文件名: RecursiveTask.java
 * 包名: priv.primo.recursive
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/17
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.recursive;

import java.util.Random;

public class RecursiveTask {
    public static void main(String[] args) {
        int arr[] = new int[100000];
        Random r = new Random();
        for (int i=0;i<arr.length;i++){
            arr[i] = r.nextInt(20);
        }
        long start = System.currentTimeMillis();
        System.out.println("sum(arr):"+ sum(arr));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        start = end;
        System.out.println("sum(int arr[],int start,int end):"+ sum(arr,0,arr.length,0));
        end = System.currentTimeMillis();
        System.out.println(end - start);
        start = end;
//        System.out.println("recursiveTask(int arr[],int start,int end):"+ recursiveTask(arr,0,arr.length));
//        end = System.currentTimeMillis();
//        System.out.println(end - start);
    }

    public static int sum(int arr[]){

        if (arr ==null || arr.length ==0){
            return 0;
        }
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
        }
        return result;
    }

    public static int sum(int arr[],int start,int end, int total){
        if (arr ==null || arr.length ==0){
            return 0;
        }

        if (start >= end){
            return total;
        }
        total+= arr[start];
        start ++;
        return sum(arr,start ,end,total);
    }

    public static int recursiveTask(int arr[],int start,int end){
        if (arr ==null || arr.length ==0){
            return 0;
        }

        if (end - start > 10){
            return recursiveTask(arr,start,(start+end)/2) + recursiveTask(arr,(start +end)/2,end);
        }else {
            return sum(arr,start,end, 0);
        }
    }
}
