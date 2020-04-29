package top.tinn.portal.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.tinn.common.api.CommonResult;
import top.tinn.portal.service.OmsPortalOrderService;

/**
 * @Description OrderTimeOutCancelTask
 * @Author Tinn
 * @Date 2020/4/10 16:13
 */
@Component
public class OrderTimeOutCancelTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);
    @Autowired
    private OmsPortalOrderService portalOrderService;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/30 * ? * ?")
    private void cancelTimeOutOrder(){
        LOGGER.info("取消订单，并根据sku编号释放锁定库存");
        CommonResult result = portalOrderService.cancelTimeOutOrder();
        LOGGER.info(result.toString());
    }
}
