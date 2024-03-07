package cn.itedus.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SnowFlake implements IIdGenerator{

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        //0 ~ 31位，可以采用配置的方式使用
        long workId;
        try {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());   //NetUtil.getLocalhostStr() 获取本地主机的IP地址
        } catch (Exception e) {
            workId = NetUtil.getLocalhostStr().hashCode();
        }

        workId = workId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.createSnowflake(workId,dataCenterId);
    }


    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
