package com.qianxuefeng.arithmetic;

import java.util.Date;

public class QuickSort {

    public static void main(String[] args) {
        long start = new Date().getTime();
        int[] arr = {12, 1, 24, 36, 98, 214, 67, 23, 24, 25, 567, 234, 2546, 234};
        sort(arr, 0, arr.length - 1);
        for (int a : arr) {
            System.out.print(a + ", ");
        }
        System.out.println();
        for (int i = 0; i < 10000000; i++) {
            sort(arr, 0, arr.length - 1);
        }
        System.out.println("耗时：" + (new Date().getTime() - start) + "毫秒");
    }


    private static void sort(int[] arr, int start, int end) {
        int l = start;
        int h = end;
        int povit = arr[start];

        while (l < h) {
            while (l < h && arr[h] >= povit)
                h--;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }

            while (l < h && arr[l] <= povit)
                l++;

            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
//        System.out.print(arr);
//        System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit + "\n");
        if (l > start) sort(arr, start, l - 1);
        if (h < end) sort(arr, l + 1, end);
    }
}