package cn.itedus.lottery.domain.strategy.service.draw;

import cn.itedus.lottery.domain.strategy.model.req.DrawReq;
import cn.itedus.lottery.domain.strategy.model.res.DrawResult;


/**
 * @description 对外提供抽奖接口
 * @author Chen.Zhixin
 */

public interface IDrawExec {
    DrawResult doDrawExec(DrawReq req);
}
