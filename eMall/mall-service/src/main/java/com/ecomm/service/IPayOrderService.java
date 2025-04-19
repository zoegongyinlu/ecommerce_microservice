package com.ecomm.service;

import com.ecomm.domain.dto.PayApplyDTO;
import com.ecomm.domain.dto.PayOrderFormDTO;
import com.ecomm.domain.po.PayOrder;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IPayOrderService extends IService<PayOrder> {

    String applyPayOrder(PayApplyDTO applyDTO);

    void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO);
}
