package com.huawenli.java;

import com.sun.org.apache.xpath.internal.objects.XString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void getMatchedText(List<String> params) {
        String tarNum = params.get(params.size() - 2);
        String ruleText = params.get(params.size() - 1);
        ArrayList<String> vals = new ArrayList<>();
        String rule = getVal(ruleText, tarNum);
        for (int i = 0; i < params.size() - 2; i++) {
            String current = params.get(i);
            String currentVal = getVal(current, tarNum);
            if (currentVal.contains(rule)) {
                vals.add(current);
            }
        }
    }

    public static String getVal(String text, String target) {
        int targetInt = Integer.parseInt(target);
        char num0 = '0';
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c - num0 < targetInt) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] ss = {
                "135682318", "23457", "14282123",
                "14231728", "3", "1724153"
        };
        getMatchedText(Arrays.asList(ss));
        String s="1 need ; 2 need ";
        String[] split = s.split(";");
        for (String s1 : split) {
            System.out.println(s1
            );
        }
    }
}
