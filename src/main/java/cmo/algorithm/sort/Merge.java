package cmo.algorithm.sort;

import java.util.Arrays;

/**
 * ClassName: Merge
 * Description: 归并排序
 和选择排序一样，归并排序的性能不受输入数据的影响，但表现比选择排序好的多，因为始终都是O(n log n）的时间复杂度。代价是需要额外的内存空间。

 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 归并排序是一种稳定的排序方法。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。

 5.1 算法描述
 把长度为n的输入序列分成两个长度为n/2的子序列；
 对这两个子序列分别采用归并排序；
 将两个排序好的子序列合并成一个最终的排序序列。
 * Author:   lin
 * Date:     2019/3/12 10:15
 * History:
 * <version> 1.0
 */
public class Merge {
    public static void main(String[] args) {
        int[] arry={35,26,3,38,47,2,19,27,50,48,4};
        int[] endmergeSort= mergeSort(arry);
        System.out.println("经过归并排序后的数组顺序为:  " + Arrays.toString(endmergeSort));
    }


    public static  int[]  mergeSort(int[] arr){
         if(arr.length<2){
             return arr;
         }
         int mid = arr.length/2;

         //copyOfRange(int []original,int from,int to),original为原始的int型数组，
         // from为开始角标值，to为终止角标值。（其中包括from角标，不包括to角标。即处于[from,to)状态)
         //将一个原始的数组original，从小标from开始复制，复制到小标to，生成一个新的数组。
         int[] left= Arrays.copyOfRange(arr,0, mid);
         int[] right=Arrays.copyOfRange(arr,mid,arr.length);
         return merg(mergeSort(left),mergeSort(right));
    }


    /**
     * 功能描述:  归并排序——将两段排序好的数组结合成一个排序数组
     * 〈〉

     * @return:
     * @since: 1.0.0
     * @Author:lin
     * @Date: 2019/3/12 10:25
     */
    public static  int[] merg(int[] left,int[] right){
        int[] result= new int[left.length+right.length];
        for (int index=0,i=0,j=0;index<result.length;index++){
            if(i>=left.length){
                result[index] =right[j++];
            }else if(j >= right.length){
                result[index] = left[i++];
            }else if(left[i] > right[j]){
                result[index] = right[j++];
            }else{
                result[index] = left[i++];
            }
        }
        return result;
    }
}
