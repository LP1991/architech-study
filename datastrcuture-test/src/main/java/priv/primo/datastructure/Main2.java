/********************** 版权声明 *************************
 * 文件名: Main2.java
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

import java.util.*;
import java.util.stream.Collectors;

public class Main2 {
    public static void main(String[] args) {

//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            System.out.println(sort(line));
//        }
//        char c = 'a' ;
//        char z = 'z' ;
//        char c1 = 'A' ;
//        System.out.println((int)c);
//        System.out.println((int)z - c);
//        System.out.println((int)c1);
//        System.out.println(thirdMax2(new int[]{2,2,3,1}));
//        TreeSet<Integer> set = new TreeSet<>();
//        set.add(1);
//        set.add(3);
//        set.add(7);
//        set.add(4);
//        set.add(4);
//        set.add(9);
//        set.add(20);
//        set.add(74);
//        set.add(54);
//
//        System.out.println("set.first():"+set.first());
//        System.out.println("set.last():"+set.last());
//        while (!set.isEmpty()){
//            System.out.println(set.pollLast());
//        }
//        validMountainArray(new int[]{0,3,2,1});
        sort("aaabbccccdddeeehj");
    }

//
//    private static String sort(String line) {
//        if (line == null || line.length() ==0){
//            return null;
//        }
//        StringBuffer sb = new StringBuffer();
//        int[] nums = new int[52];
//
//        for (int i = 0; i < line.length(); i++) {
//            nums[line.charAt(i) - 'A'] ++;
//        }
//
//        for (int i=0;i<nums.length;i++){
//            if (nums[i] == 0){
//                continue;
//            }
//            for (int j =0;j<nums[i];j++){
//                sb.append((char)(i + 'A'));
//            }
//        }
//        return sb.toString();
//    }

    private static String sort(String line) {
        if (line == null || line.length() ==0){
            return null;
        }
        Map<Character,Integer> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        int[] nums = new int[52];

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (map.containsKey(c)) {
                Integer val = map.get(c);
                val++;
                map.put(c, val);
            }else {
                map.put(c, 1);
            }
        }
//        key value 转换
        Map<Integer,Character> trans = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));

        Integer[] integers = trans.keySet().toArray(new Integer[]{});
        Arrays.sort(integers);

        for (int i=0;i<integers.length;i++){
            if (integers[i] == 0){
                continue;
            }
            for (int j =0;j<integers[i];j++){
                sb.append(map.get(integers[i]));
            }
        }
        return sb.toString();
    }

    public  static int thirdMax(int[] nums) {
        //假设前三位就是最大的三位
        if(nums == null || nums.length == 0){
            return 0;
        }
        int k = 3;
        int temp;
        for(int i=1;i<nums.length && i<k;i++){
            int j = i-1;
            while(j>=0 && nums[i]>=nums[j]){
                j--;
            }
            j++;
            if(i != j && nums[i] == nums[j]){
                k++;
            }
            if(j != i){
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        if(k > nums.length){
            return nums[0];
        }

        for (int i=k;i<nums.length;i++){
            int j = 2;
            while(j>=0 && nums[i]>nums[j]){
                j--;
            }
            j++;
            while (j < 3){
                temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
        return nums[2];
    }

    public  static int thirdMax2(int[] nums) {
        long max = Long.MIN_VALUE;
        long mid =Long.MIN_VALUE;
        long min = Long.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max){
                min = mid;
                mid = max;
                max = nums[i];
            }else if (nums[i]<max && nums[i] > mid){
                min = mid;
                mid = nums[i];
            }else if (nums[i] < mid && nums[i] >min){
                min = nums[i];
            }
        }

        return min == Long.MIN_VALUE ? (int)max : (int)min;
    }

    public  static int thirdMax3(int[] nums) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums) {
            set.add(num);
            if (set.size()  >= 3){
                set.pollFirst();
            }
        }
        return set.size() == 3 ? set.last() : set.first();
    }

    public static boolean validMountainArray(int[] A) {
        if(A == null || A.length < 3){
            return false;
        }

        int start =0 ;
        int end = A.length -1;
        boolean up = true;
        boolean down = true;
        while(start <= end){
            if(up){
                if(A[start] >= A[start+1]){
                    up = false;
                }else{
                    start++;
                }
            }

            if(down){
                if(A[end] >= A[end-1]){
                    down = false;
                }else{
                    end--;
                }
            }

            if(!(up || down)){
                return false;
            }
        }
        return start > 0 && start <A.length;
    }

}
