package com.yang.cloudmanet.lab2;

import com.yang.cloudmanet.utils.RandomID;

import java.util.HashMap;
import java.util.Map;

public class MatrixMultiplication {

    public static int[][] Multiplication(int[][] p, int[][] q, int n) {
        int[][] result = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                result[i][j] = 0;
                for(int k = 0; k < n; k++){
                    result[i][j] += p[i][j]*q[k][j];
                }
            }
        }
        return result;
    }


    public static int[][] generateMatrix(int n){
        int[][] matrix = new int[n][n];
        RandomID randomID = new RandomID();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                int num = Integer.parseInt(randomID.genMatrix());
                matrix[i][j] = num;
            }
        }
        return matrix;
    }
    // A函数可用于求余子阵
    public static int[][] A(int[][] a, int row, int column) {
        int[][] ans = new int[a.length - 1][a.length - 1];// ans用于储存返回的最终结果
        int[] temp = new int[(a.length - 1) * (a.length - 1)];// 临时一维数组temp用于按顺序储存剔除相应行和列元素后的数组
        int k = 0;
        // 剔除行和列并按顺序储存到temp内
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (i == row - 1) {
                    continue;
                } else if (j == column - 1) {
                    continue;
                }
                temp[k++] = a[i][j];
            }
        }
        // 按顺序从temp中读取数据并储存到ans内
        k = 0;
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[i].length; j++) {
                ans[i][j] = temp[k++];
            }
        }
        return ans;
    }



    private static Map<int[][], Integer> set = new HashMap<>();


    // det用于求行列式
    public static int det(int[][] a) {
        if (a.length == 1) {
            return a[0][0];
        } else {
            int ans=0;
            for (int i = 0; i < a.length; i++) {
                int[][] tmp = A(a,i+1,1);
                if(!set.containsKey(tmp)){
                    ans+=a[i][0]*(int)Math.pow(-1, i)*det(tmp);
                    set.put(tmp,ans);
                }else {
                    return set.get(tmp);
                }
            }
            return ans;
        }
    }

    public static int[][] result(int[][] matrix1, int[][] matrix2){
//        System.out.println("结果矩阵为" + matrix1[0].length + "行" + matrix2.length + "列");
        int[][] matrix = new int[matrix1.length][matrix2[0].length ];
        for(int i=0;i<matrix1.length;i++) {
            for(int j=0;j<matrix2.length;j++) {
                int sum = 0;
                for(int k=0;k<matrix1.length;k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                matrix[i][j] = sum;
//                System.out.print("\t" + sum);
            }
//            System.out.println();
        }
        return matrix;
    }




    public static void main(String[] args) {
        /*
         * 1.构建矩阵
         *             2 9 7               4 2
         *    matrix1= 3 1 4      matrix2= 6 8
         *                                 2 6
         */
        int[][] matrix1 = {{1,1},{1,3}};
//        int[][] matrix2 = {{8,20,39,1},{43,10,43,1},{48,7,44,1}};
        int[][] matrix2 = {{1,1,1,2,3,4,5,87},{2,4,1,6,3,4,5,87},{15,1,32,35,4,5,87},{1,1,34,2,3,4,5,87},{13,12,1,2,3,4,5,87},{1,15,1,27,3,4,5,87},{1,1,14,2,3,4,5,87},{1,1,1,2,3,4,5,87}};
        System.out.println("matrix1矩阵为" + matrix1.length + "行" + matrix1[0].length +"列");
        for(int i=0;i<matrix1.length;i++) {
            for(int j=0;j<matrix1[i].length;j++) {
                System.out.print("\t" + matrix1[i][j]);
            }
            System.out.println();
        }
        System.out.println("matrix2矩阵为" + matrix2.length + "行" + matrix2[0].length +"列");
        for(int i=0;i<matrix2.length;i++) {
            for(int j=0;j<matrix2[i].length;j++) {
                System.out.print("\t" + matrix2[i][j]);
            }
            System.out.println();
        }
        System.out.println(det(matrix2));

    }
}