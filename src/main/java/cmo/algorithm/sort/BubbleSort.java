package cmo.algorithm.sort;

import java.util.Arrays;

/**
 * ClassName: bubbleSort
 * Description: 冒泡排序:持续比较相邻的元素。如果第一个比第二个大，就交换他们两个。直到没有任何一对数字需要比较。
 * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。
 * 即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
 *
 * 一般河水中的冒泡，水底刚冒出来的时候是比较小的，随着慢慢向水面浮起会逐渐增大
 * Date:     2019/3/10 22:43
 * History:
 * <version> 1.0
 * @author lin
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] count ={34,1,5,45,4,78,91,65};
         bubblSort(count);

//        bubbleSort1(count);
        System.out.println("Sorting: " + Arrays.toString(count));
    }

    public static void bubblSort(int[] arr){
        int temp;
            //这里是总共需要比较多少轮
              for (int i =0; i <arr.length-1 ; i++) {
                // 这里表示一轮中需要，进行循环次数，并且ar
            for (int j=0;j<arr.length-i-1;j++){
                // 如果这里是小于，那么排序出来结果就是最大在前面，最小的最在后面
                if(arr[j] > arr[j+1]){
                  temp = arr[j];
                  arr[j]=arr[j+1];
                  arr[j+1] = temp;
                }
            }
        }

    }


    public static void bubbleSort(int[] a) {
        int j, flag;
        int temp;
        for (int i = 0; i < a.length; i++) {
            flag = 0;
            for (j = 1; j < a.length - i; j++) {
                if (a[j]<a[j - 1]) {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    flag = 1;
                }
            }
            // 如果没有交换，代表已经排序完毕，直接返回
            if (flag == 0) {
                return;
            }
        }
    }

    public static void bubbleSort1(int[] numbers) {
        int temp = 0;
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                // 交换两数位置
                if (numbers[j] > numbers[j + 1])
                {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }


    /**
     * 记录 交换次数
     * @param numbers
     */
    public  static  void bubbleSort2(int[] numbers){
        int temp=0;
        int numberOfchanges = 0;
        //数组下标是从零开始的， size-1 表示数组长度。
        int size = numbers.length;
        for (int i = 0; i < size-1; i++) {
            /**
             *  size-1-i 表示 的含义是，比如当数据组长度是5时，i第一次是 i=0 时 ，j<数组长度-i=6。j第一次也是0，只不过是说j的循环次数是j<5.
             // 第一次循环 j=0,然后 将 数组的第一个元素取出来和 数组的第二个元素进行比较。如果数据下标为0的元素大于 数组下标为1的元素，那么就替换位置。
             // 将后面小的数 和前面 比较大的数进行位置替换。 替换位置后就进入下一次循环.  如果不大于 则 进入下一次循环。
             // 第二次 循环 j=1， 那么 就是从下标 为1的位置进行比较，如果大于后面一个数组下标的元素就是进行位置替换，否则就进入下一次循环，一次类推。
             // 直到 内层循环走完。 内层循环完后 。
             // 外层循环 又开始从下标 为1的开始循环。 那么内存循环，j就是 j<size-1-1 . 数组长度是5 那么 j<4. 开始进行
             // 循环。也就是循环次数 小于 4次。  这样依次循环比较。 当 内层循环比较完了之后。再次来到外层循环 ，外层循环来到i = 2 的小表。

             */
            for (int j = 0; j <size-1-i; j++) {
                // 如果这里是小于，那么排序出来结果就是最大在前面，最小的最在后面
                 if(numbers[j] > numbers[j+1]){
                     temp = numbers[j];
                     //替换位置，将小的数 放在数组的前面一个下标位置。
                     numbers[j] = numbers[j+1];
                     numbers[j+1] = temp;
                 }
            }
        }
    }
}
