package com.fenq1.study.leetcode;

import java.util.HashMap;

public class ID_242 {

    /**
     * 242. 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * 示例 2:
     * <p>
     * 输入: s = "rat", t = "car"
     * 输出: false
     * 说明:
     * 你可以假设字符串只包含小写字母。
     * <p>
     * 进阶:
     * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
     * <p>
     * 通过次数188,084提交次数296,913
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(new Solution().isAnagram(s, t));
    }

    static
    class Solution {
        public boolean isAnagram(String s, String t) {
            HashMap<String, Integer> map = new HashMap<>();
            Integer num;
            String key;
            for (char c : s.toCharArray()) {
                key = String.valueOf(c);
                num = map.get(key);
                num = null == num ? 1 : num + 1;
                map.put(key, num);
            }
            for (char c : t.toCharArray()) {
                key = String.valueOf(c);
                num = map.get(key);
                if (null == num)
                    return false;
                if (num == 1)
                    map.remove(key);
                else
                    map.put(key, num - 1);
            }
            return map.keySet().size() == 0;
        }
    }

}
