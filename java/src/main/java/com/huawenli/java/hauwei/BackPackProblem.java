package com.huawenli.java.hauwei;

//01背包问题


public class BackPackProblem {
    public static void main(String[] args) {
        int W = 4;
        int N = 3;
        int[] value ={4,2,3};
        int[] weight = {2,1,3};

        int maxValue = getMaxValue(value,weight,W,N);
        System.out.println(maxValue);
    }

    private static int getMaxValue(int[] value, int[] weight, int w, int n) {
       int dp[][] = new int[n+1][w+1];
        for (int i = 1; i <= n; i++) { //物品
            for (int j = 1; j <= w; j++) { //背包大小
                if (weight[i-1] > j){ //如果当前的背包容量装不下，就不装入背包
                    dp[i][j] = dp[i-1][j];
                }else{  //能装下，Max{不装物品i，装物品i}
                    dp[i][j] = Math.max(
                            dp[i-1][j],
                            dp[i-1][j-weight[i-1]] + value[i-1]);
                }
            }
        }
        return dp[n][w];
    }


}
