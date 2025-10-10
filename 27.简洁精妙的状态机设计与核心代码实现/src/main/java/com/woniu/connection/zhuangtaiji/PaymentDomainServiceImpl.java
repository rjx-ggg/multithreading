package com.woniu.connection.zhuangtaiji;


import com.woniu.connection.entity.PaymentNotifyMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付领域域服务
 */
@Slf4j
public class PaymentDomainServiceImpl {

    /**
     * 状态机设计代码实现
     * 支付结果通知
     */
    public void notify(PaymentNotifyMessage message) {
        PaymentModel paymentModel = loadPaymentModel(message.getPaymentId());
        try {
            // 状态推进
            paymentModel.transferStatusByEvent(PaymentEvent.valueOf(message.getEvent()));
            savePaymentModel(paymentModel);
            // 其它业务处理
        } catch (StateMachineException e) {
            // 异常处理
            log.error("errorMsg;{}", e.getMessage());
        } catch (Exception e) {
            // 异常处理
            log.error("errorMsg;{}", e.getMessage());
        }
    }


    /**
     * 保存支付单操作
     *
     * @param paymentModel
     */
    private void savePaymentModel(PaymentModel paymentModel) {
        log.info("paymentModel is {}", paymentModel);
    }

    private PaymentModel loadPaymentModel(String paymentId) {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setCurrentStatus(PaymentStatus.INIT);
        return paymentModel;
    }
}
