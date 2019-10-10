/********************** 版权声明 *************************
 * 文件名: RedPacketRandom.java
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
public class RedPacketRandom {
    /** 红包占总金额的最大比例*/
    public static final double RED_PACKET_MAX_RATIO = 0.3;
    public static final double RED_PACKET_MIN_MONEY = 1.0;
    //    主程序
    public static void main(String[] args) throws InterruptedException {
        List<Double> redPacket = getRandomRedPacket(200.0, 20);
        System.out.println(redPacket);
        System.out.println(redPacket.stream().reduce((a, b) -> a + b).get());
    }

    /**
     * @方法名称: getRedPacket
     * @实现功能: 根据输入参数 persons
     * @param money 红包总金额
     * @param persons 抢红包人数
     * @return 红包集合
     * @Create by Primo at 2019/4/17 20:53
     * @throws
     */
    private static List<Double> getRandomRedPacket(Double money, int persons) {
//        输入校验，钱和r人数总数必须大于0
        assert money > 0;
        assert persons > 0;

        if (persons < 4){
//            无法保证每个人都不大于30%。
//            最大值不限制
            return getRandomRedPacket(money, persons, money, RED_PACKET_MIN_MONEY,true);
        }

        if (persons > money){
//            无法保证每个人都能获取到1块。
            //            最小值为0.1
            return getRandomRedPacket(money, persons, money*RED_PACKET_MAX_RATIO, 0.1,false);
        }
        return getRandomRedPacket(money, persons, money*RED_PACKET_MAX_RATIO, RED_PACKET_MIN_MONEY,true);
    }

    /**
     * @方法名称: getRedPacket
     * @实现功能: 根据输入参数 persons
     * @param money 红包总金额
     * @param persons 抢红包人数
     * @param topMoney 单个红包最大值
     * @param bottomMoney 单个红包最小值
     * @param useInt 是否取整
     * @return 红包集合
     * @Create by Primo at 2019/4/17 20:53
     * @throws
     */
    public static List<Double> getRandomRedPacket(Double money, int persons, double topMoney, double bottomMoney, boolean useInt){
//        输入校验，钱和r人数总数必须大于0
        assert money > 0;
        assert persons > 0;

        List<Double> redPackets = new ArrayList<>();
        Random random = new Random();
//        红包的区间值
        double minMoney;
        double restMoney = money;
        double maxMoney = topMoney;
//        平均值
        double avg = money/persons;
        //精确小数点2位

        while(persons > 0){
//            最后一个人，剩下的都给他
            if (persons == 1){
//                此处不用 restMoney ，避免double精度的溢出
                redPackets.add(restMoney);
                break;
            }
//            最小值= 剩余人的平均值-人均平均值
            minMoney = restMoney/persons-avg;
//            随机获取红包值
//            Math.round((random.nextDouble() * avg + minMoney) * 100) * 0.01d;
            double red = 0;
            if(useInt){
                // 取整
                red = Math.ceil(random.nextDouble() * avg + minMoney);
            }else {
//            保留2为小数
                red = Math.round((random.nextDouble() * avg + minMoney) * 100) * 0.01d;
            }
//            System.out.println("red:"+red+",minMoney:"+minMoney+",restMoney:"+restMoney+",persons:"+persons);
//            避免超过最大值
            red = red > maxMoney ? maxMoney : red;
//          如果小于最小值则默认设置为最小值
            red = red < bottomMoney ? bottomMoney : red;
//            如果剩余的钱不够每个人分了
            if (restMoney - red <= (persons-1)*bottomMoney){
                red = restMoney - (persons-1)*bottomMoney;
            }

//            如果剩余的钱足够每个人都分到最大值，则直接分配red为money的30%
            if (restMoney - red >= (persons-1)*maxMoney){
                red = maxMoney;
            }
            redPackets.add(red);
//           剩余钱减去
            restMoney -= red;
            persons --;
        }

//      打乱顺序
        Collections.shuffle(redPackets);
        return redPackets;
    }
}

