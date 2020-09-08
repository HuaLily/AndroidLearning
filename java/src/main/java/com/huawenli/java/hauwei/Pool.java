package com.huawenli.java.hauwei;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Pool {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        String[] s = sc.next().split(",");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        System.out.println("n = "+n);
        System.out.println("m = "+m);

//        String[][] board = new String[n][m];
//        for (int i = 0; i < n ; i++) {
//            s = sc.next().split("");
//            board[i]= s;
//            System.out.print(Arrays.toString(s));
//        }

        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++) {
            char[] chars = sc.next().toCharArray();
            board[i] = chars;
        }


//        int n = 4;
//        int m = 5;
//
//        char[][] board = new char[][]{
//                new char[]{'S', 'S', 'H', 'H', 'S'},
//                new char[]{'S', 'S', 'H', 'H', 'H'},
//                new char[]{'H', 'S', 'S', 'H', 'H'},
//                new char[]{'H', 'H', 'H', 'S', 'S'}
//        };

//        for(int i= 0;i< board.length;i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                System.out.print(board[i][j] );
//            }
//            System.out.println();    //换行
//        }

        int num = solution(board, n, m);
        System.out.println(num);
    }

    private static int solution(char[][] board, int n, int m) {
        //先遍历找到边上的，做好标记E，再遍历整个数组，找到中间的被单独包围的S，最后相加
        int count = 0;

        //按行查找
        for (int i = 0; i < m; i++) {
            //遍历找到第一行上的池塘,并标记为E
            if (board[0][i] == 'S') {
                dfs(board, 0, i, n, m);
                count++;
            }

            //最后一行
            if (board[n - 1][i] == 'S') {
                dfs(board, n-1, i, n, m);
                count++;
            }
        }
        //按列查找
        for (int i = 1; i < n-1; i++) {
            //遍历找到第一列上的池塘,并标记为E
            if (board[i][0] == 'S') {
                dfs(board, i, 0, n, m);
                count++;
            }

            //最后一列
            if (board[i][m-1] == 'S') {
                dfs(board, i, m-1, n, m);
                count++;
            }
        }




        //遍历全部数组
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'S') {
                    dfs(board,i,j,n,m);
                    count++;
                }
            }
        }

        return count;
    }

    private static void dfs(char[][] board, int x, int y, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || board[x][y] != 'S') {
            return;
        }
        board[x][y] = 'E';
        dfs(board, x + 1, y, n, m); //右边
        dfs(board, x - 1, y, n, m); //左边
        dfs(board, x, y + 1, n, m); //上边
        dfs(board, x, y - 1, n, m); //下边
    }
}
