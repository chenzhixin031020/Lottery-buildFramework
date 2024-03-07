package cn.itedus.lottery.domain.award.service.goods;

import cn.itedus.lottery.domain.award.repository.IOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @description: 配送货物基础共用类
 */
public class DistributionBase {
    protected Logger logger = LoggerFactory.getLogger(DistributionBase.class);

    @Resource
    private IOrderRepository orderRepository;

    protected void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        orderRepository.updateUserAwardState(uId,orderId,awardId,grantState);
    }
}
