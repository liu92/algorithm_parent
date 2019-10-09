/**
 * Copyright (C), 2018-2019, XXX有限公司
 * FileName: BloomFilterDemo
 * Author:   lin
 * Date:     2019/3/8 11:18
 * Description: 布隆过滤器测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cmo.algorithm;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author lin
 */
public class BloomFilterDemo {

    private  static  final  int capacity=10000000;
    private  static  final  int key =999998;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
    }

    public static void main(String[] args) {
         /*返回计算机最精确的时间，单位微妙*/
        long start= System.nanoTime();

        if(bloomFilter.mightContain(key)){
            System.out.println("成功过滤到" + key);
        }

        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间:" + (end - start));

        int sum= 0;
        int countTem=20000;
        int countTem1=30000;
        for (int i = capacity+countTem; i <capacity+countTem1 ; i++) {
          if(bloomFilter.mightContain(i)){
              sum  =sum+1;
          }
        }
        System.out.println("错判率为:" + sum);
    }
    /**
     * 运行结果是
     * 成功过滤到999998
       布隆过滤器消耗时间:197541
       错判率为:302
     * 在100 w个数据中只消耗了约0.19毫秒就可以匹配到了key,速度还是比较快的，
     * 然后模拟1w个不存在布隆过滤器中的key
     * 匹配错误率为302/10000,也就是说出错率大概为3%。
     *
     */



}
