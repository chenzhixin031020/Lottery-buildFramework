package cn.itedus.lottery.domain.strategy.service.algorithm.impl;

import cn.itedus.lottery.domain.strategy.model.vo.AwardRateVO;
import cn.itedus.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 必中奖策略抽奖，排掉已经中奖的概率，重新计算中奖范围
 * @author Chen.Zhixin
 * @date: 2024/1/26
 */
@Component("entiretyRateRandomDrawAlgorithm")
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        BigDecimal differenceDenominator = BigDecimal.ZERO;

        //排除掉不在抽奖范围的奖品ID集合
        ArrayList<AwardRateVO> differenceAwardRateList = new ArrayList<>();

        List<AwardRateVO> awardRateIntervalList = awardRateInfoMap.get(strategyId);
        for (AwardRateVO awardRateVO : awardRateIntervalList) {
            String awardId = awardRateVO.getAwardId();
            if (excludeAwardIds.contains(awardId)) {
                continue;
            }
            differenceAwardRateList.add(awardRateVO);
            differenceDenominator = differenceDenominator.add(awardRateVO.getAwardRate());
        }

        //前置判断
        if (differenceAwardRateList.isEmpty()) {
            return null;
        }
        if (differenceAwardRateList.size() == 1) {
            return differenceAwardRateList.get(0).getAwardId();
        }

        //获取随机概率值（生成一个介于1到100（包括1和100）之间的随机整数），相似于用户的彩票号码
        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;

        //循环获取奖品
        String awardId = null;
        int cursorVal = 0;
        for (AwardRateVO awardRateVO : differenceAwardRateList) {
            //对获取的奖励率进行除法运算，分母是differenceDenominator。参数2表示保留小数点后两位，BigDecimal.ROUND_UP表示采用向上取整的方式。
            //这一步得到该奖品占所有奖池奖品的概率
            int rateVal = awardRateVO.getAwardRate()
                    .divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = awardRateVO.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }

        //返回中奖结果
        return awardId;
    }
}
