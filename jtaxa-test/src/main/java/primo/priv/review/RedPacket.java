/********************** 版权声明 *************************
 * 文件名: RedPacket.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/17
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * priv.primo.primo.priv.review.RedPacket
 * 测试抢红包算法, 问题 人员小于4个人时候，肯定有人大于30%
 * @author: Primo
 * @createTime: 2019/4/17 20:45
 */
public class RedPacket {
    /** 红包占总金额的最大比例*/
    public static final double RED_PACKET_MAX_RATIO = 0.3;
    public static final double RED_PACKET_MIN_MONEY = 1.0;
//    主程序
    public static void main(String[] args) throws InterruptedException {
        List<Double> redPacket = getRedPacket(200.0, 40);
        System.out.println(redPacket);
        System.out.println(redPacket.stream().reduce((a, b) -> a + b).get());
        for(int i=0 ;i<1000000;i++){
           if (i %10000 == 0){
               Thread.sleep(1000);
           }
           Object o = new Object();
//            System.out.println(o.hashCode());
        }
    }

    /**
     * @方法名称: getRedPacket
     * @实现功能: 根据输入参数 persons; 当人数小于4个时，无法保证每个值都不超过总数的30%
     * @param money 红包总金额
     * @param persons 抢红包人数
     * @return 红包集合
     * @Create by Primo at 2019/4/17 20:53
     * @throws
     */
    public static List<Double> getRedPacket(Double money, int persons){
//        输入校验，钱和红包总数必须大于0
        assert money > 0;
        assert persons > 0;
        List<Double> redPackets = new ArrayList<>(persons);
        Random random = new Random();
//        红包的区间值,最大和最小值
        double rangeMin;
        double rangeMax;
//        剩余红包钱
        double restMoney = money;
//        红包阈值，默认30%， 但是当人数小于4个时，无法保证每个值都不超过总数的30%
        double maxMoney = money*RED_PACKET_MAX_RATIO;
//        平均值
        double avg = money/persons;
        //精确小数点2位

        while(persons > 0){
//            最后一个人，剩下的都给他
            if (persons == 1){
                redPackets.add(restMoney);
                break;
            }

            // 最小值= 保证后面每个人最大不超过30%
            rangeMin = restMoney - (persons - 1) * maxMoney;
            rangeMin = rangeMin < 0 ? 0 : rangeMin;

            // 最大值= 保证后面每个人至少能有RED_PACKET_MIN_MONEY ;
            rangeMax = restMoney - (persons - 1) * RED_PACKET_MIN_MONEY;

//            随机获取红包值
            double red = Math.ceil(random.nextDouble() * (rangeMax-rangeMin) + rangeMin);

            System.out.println("red:"+red+",rangeMin:"+rangeMin+",rangeMax:"+rangeMax+",restMoney:"+restMoney+",persons:"+persons);
//            避免超过最大值
            red = red > maxMoney ? maxMoney : red;
//          如果小于最小值则默认设置为最小值
            red = red < RED_PACKET_MIN_MONEY ? RED_PACKET_MIN_MONEY : red;
//            如果剩余的钱不够每个人分了
//            if (restMoney - red <= (persons-1)*RED_PACKET_MIN_MONEY){
//                red = restMoney - (persons-1)*RED_PACKET_MIN_MONEY;
//                redPackets.add(red);
//                break;
//            }
            redPackets.add(red);
//           剩余钱减去
            restMoney -= red;
            persons --;
        }

//        如果有人没有分配到，每个人给默认最小值
//        if (persons > 1){
//            for (int i = 1; i < persons; i++) {
//                redPackets.add(restMoney/persons);
//            }
//        }
//      打乱顺序
        Collections.shuffle(redPackets);
        return redPackets;
    }
}
