package com.ecomm.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecomm.payment.domain.dto.PayApplyDTO;
import com.ecomm.payment.domain.dto.PayOrderFormDTO;
import com.ecomm.payment.domain.po.PayOrder;


public interface IPayOrderService extends IService<PayOrder> {

    String applyPayOrder(PayApplyDTO applyDTO);

    void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO);
}
