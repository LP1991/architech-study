/********************** 版权声明 *************************
 * 文件名: RotateArray.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/19
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo;

public class RotateArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7,8};
        rotateA(nums,6);

        for (int num : nums) {
            System.out.print(num+" ");
        }
    }

    public static void rotateA(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

//    时间复杂度 O(n)  ,空间复杂度 O(n)
    public static void rotate(int[] nums, int k) {
        k = k % nums.length;
        int round = nums.length / k;
        int prev = 0;
        int temp = 0;
        for(int i=0;i<k;i++){
//            prev = nums[i];
            int currentIndex = 0;
            for (int j = 0;j<=round;j++){
                currentIndex = (currentIndex+k) % nums.length;
                temp = nums[currentIndex];
                nums[currentIndex] = prev;
                prev = temp;
            }
        }
    }

    public static void rotate2(int[] nums, int k) {
        k = k % nums.length;

        int temp = 0;
        int exchange = 0;
        for(int i=0;i< k;i++){
//                头尾对换
            temp = nums[0];
            nums[0] = nums[nums.length -1];
            for(int j = 1; j< nums.length; j++){
                exchange = nums[j];
                nums[j] = temp;
                temp = exchange;
            }
        }
    }
}
