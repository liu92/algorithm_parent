package cmo.algorithm.sort;

import java.util.Arrays;

/**
 * ClassName: Selection
 * Description: 选择排序
 * 从算法逻辑上看，选择排序是一种简单直观的排序算法，在简单选择排序过程中，所需移动记录的次数比较少。
 * 选择排序的基本思想：比较 + 交换。
   在未排序序列中找到最小（大）元素，存放到未排序序列的起始位置。在所有的完全依靠交换去移动元素的排序方法中，选择排序属于非常好的一种。
 　 分为三步：

 　　①、从待排序序列中，找到关键字最小的元素

 　　②、如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换

 　　③、从余下的 N - 1 个元素中，找出关键字最小的元素，重复(1)、(2)步，直到排序结束
 * Date:     2019/3/11 21:23
 * History:
 * <version> 1.0
 * @author lin
 */
public class Selection {
    public static void main(String[] args) {
         int[] arr={14,5,25,63,37,82,12};
         selectionSort(arr);
         System.out.println("endSort:  " + Arrays.toString(arr));
    }


    public static void selectionSort(int[] arr){
        //总共要经过N-1轮比较
        // 比如说这里长度=7，然后从i=0开始，那么到下一个循环是 j也从 0开始，如果arr[j]<arr[min]
        for (int i=0; i<arr.length-1; i++){
          int min=i;
          //每轮需要比较的次数
          for (int j = i; j <arr.length; j++) {
               // 如果j=0,那么arr[j]不小于 i=0的arr[i],所以在进行下一次比较，当j=1时 arr[j]和i=0的arr[i]在比较
              if(arr[j]<arr[min]){
                  //记录目前能找到的最小值元素的下标
                  min = j;
              }
          }
          //将找到的最小值和i位置所在的值进行交换
          if(min!=i){
            int temp=arr[i];
            arr[i]=arr[min];
            arr[min]=temp;
          }
            //第 i轮排序的结果为
            System.out.println("第"+(i+1)+"轮排序后的结果为:"+Arrays.toString(arr));
      }
    }
}
