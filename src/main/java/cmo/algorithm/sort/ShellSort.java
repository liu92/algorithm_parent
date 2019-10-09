package cmo.algorithm.sort;

import java.util.Arrays;

/**
 * ClassName: shellSort
 * Description: 希尔排序
 * 希尔排序是先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，待整个序列中的记录“基本有序”时，再对全体记录进行依次直接插入排序。
 *  ①. 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；（一般初次取数组半长，之后每次再减半，直到增量为1）
    ②. 按增量序列个数k，对序列进行k 趟排序；
    ③. 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
       仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 * Author:   lin
 * Date:     2019/3/11 21:00
 * History:
 * <version> 1.0
 */
public class ShellSort {
    public static void main(String[] args) {
        int [] arry ={7,35,22,54,16,89,92,65,12};
        shellSortDemo(arry);
        System.out.println("希尔排序经过插入排序后的数组顺序为:  " + Arrays.toString(arry));
    }

    /**
     * 功能描述: 希尔排序通过将比较的全部元素分为几个区域来提升插入排序的性能。这样可以让一个元素可以一次性地朝最终位置前进一大步。
     * 然后算法再取越来越小的步长进行排序，算法的最后一步就是普通的插入排序，但是到了这步，需排序的数据几乎是已排好的了（此时插入排序较快）。
     *
     * @return:
     * @since: 1.0.0
     * @Author:lin
     * @Date: 2019/3/12 9:41
     */
    public static  void  shellSortDemo(int[] arr){
        int number=arr.length/2;
        int i;
        int j;
        int temp;
        while (number>=1){
            for (i=number; i<arr.length; i++){
                temp=arr[i];
                // 比如 number等于5，那么这里i=5,然后j=0; temp=arr[5]
                j=i-number;

                //需要注意的是，这里array[j] < temp将会使数组从大到小排序
                while (j>=0 && arr[j]>temp){
                    arr[j+number]=arr[j];
                    j=j-number;
                }
                arr[j+number]=temp;
            }
            number=number/2;
        }

        /**
         * 排序结果
         这里array[j] < temp将会使数组从大到小排序
         希尔排序经过插入排序后的数组顺序为:  [92, 89, 65, 54, 35, 22, 16, 12, 7]

         如果是array[j] > temp将会使数组从小到大排序
         希尔排序经过插入排序后的数组顺序为:  [7, 12, 16, 22, 35, 54, 65, 89, 92]

         */

    }

}
