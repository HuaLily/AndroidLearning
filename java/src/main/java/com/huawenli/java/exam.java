package com.huawenli.java;

//中兴笔试题1  两个排序好的数组 计算逆序数的个数
//对应剑指offer困难题，计算逆序数，下面就是具体代码
public class exam {
    public static void main(String[] args) {
        int[] nums = new int[]{5,6,7,1,2,3};
        int result = solution(nums);
        System.out.println(result);
    }

    private static int solution(int[] nums) {
        int len = nums.length;
        int[] tmp = new int[len];
        if (len <= 1){ //数组个数位1 ，则逆序数位0
            return 0;
        }

        //复制原始数组，不修改原始数组
        int[] copy = new int[len];
        for (int i = 0; i < len; i++) {
            copy[i] = nums[i];
        }

        //利用递归，实现分治的思想
        int count = coutNum(copy,0,len-1,tmp);

        return count;
    }

    /**
     * nums[left..right]计算逆序
     * 主要利用的是堆排序的算法
     * @param copy
     * @param left
     * @param right
     * @param tmp
     * @return
     */
    private static int coutNum(int[] copy, int left, int right, int[] tmp) {
        if(left == right){ //当只有一个元素的时候，没有逆序数
            return 0;
        }

        int mid = left + (right - left)/2;//为了避免left + right 数组溢出的异常,向下取整
        int leftCount = coutNum(copy,left,mid,tmp);
        int rightCount = coutNum(copy, mid+1, right, tmp);

        //如果是顺序，直接返回
        if (copy[mid] <= copy[mid + 1] ){
          return  leftCount + rightCount;
        }
        int crossCount = mergeCount(copy,left,mid,right,tmp);
        return leftCount + rightCount + crossCount;
    }

    /**
     * 合并nums[left...mid,mid+1..right]排序，并计算逆序数
     * nums[left...mid] nums[mid+1,right] 有序
     * 设置两个指针和一个tmp数组
     * @param nums 原始数组，排好序的数覆盖
     * @param left
     * @param mid
     * @param right
     * @param tmp 复制的原始数组，用来放指针比较
     * @return
     */
    private static int mergeCount(int[] nums, int left, int mid, int right, int[] tmp) {
        for (int i = left; i <= right; i++) {
            tmp[i] = nums[i];
        }
        int count = 0;
        int i = left;
        int j = mid +1;

        for (int k = left; k <= right; k++) { //堆排序
            if (i == mid+1){
                nums[k] = tmp[j];
                j++;
            }else if (j == right+1){
                nums[k] = tmp[i];
                i++;
            }else if (tmp[i] <= tmp[j] ){
                    nums[k] = tmp[i];
                    i++;
                }else{
                    nums[k] = tmp[j];
                    j++;
                    count += (mid - left + 1);
                }
            }



        return count;
    }


}
