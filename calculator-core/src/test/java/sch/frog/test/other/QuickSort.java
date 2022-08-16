package sch.frog.test.other;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {
    

    public static void main(String[] args){
        int[] arr = new int[]{1, 5, 8, 2, 6, 1, 0, 5, 3};
        for(int i = 1; i < arr.length; i++){
            int fa = arr[i];
            int j = i - 1;
            int fb;
            for(; j > -1; j--){
                fb = arr[j];
                if(fa < fb){
                    arr[j + 1] = fb;
                }else{
                    break;
                }
            }
            arr[j + 1] = fa;
        }

        System.out.println(Arrays.toString(arr));
    }

    

}
