package cn.itedus.lottery.domain.strategy.service.algorithm.impl;

import cn.itedus.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

/**
 * 单项随机概率抽奖，抽到一个已经排掉的奖品则未中奖
 * @author Chen.Zhixin
 */
@Component("singleRateRandomDrawAlgorithm")
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        //获取策略对应元组
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        //使用断言（assert）确保获取的rateTuple不为空
        assert rateTuple != null;

        //随机索引,根据value值算出下标位置
        int randomVal = new SecureRandom().nextInt(100) + 1;
        int idx = super.hashIdx(randomVal);

        //返回结果
        String awardId = rateTuple[idx];
        if(excludeAwardIds.contains(awardId)) return "未中奖";

        return awardId;
    }
}
