package com.fenq1.study.leetcode;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.Arrays;
import java.util.HashMap;

public class ID_1 {

    /**
     * 1. 两数之和
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * <p>
     * 示例:
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * 通过次数1,613,112提交次数3,254,594
     */
    public static void main(String[] args) {
        int[] nums = {1, 5, 2, 7, 11, 15};
        int target = 9;
        System.out.println(JSONUtil.toJsonStr(new Solution().twoSum(nums, target)));
    }
    static
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int[] results = new int[2];
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(nums[i])) {
                    results[0] = map.get(nums[i]);
                    results[1] = i;
                    return results;
                }
                map.put(target - nums[i], i);
            }
            return results;
        }
    }
}
