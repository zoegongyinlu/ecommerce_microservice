import { request } from '@/utils/axios';
import { PayApplyDTO, PayOrderFormDTO, PayOrderVO, PayOrderDTO } from '@/types';

export const paymentService = {
  // Get all payment orders
  getPayOrders: () => {
    return request<PayOrderVO[]>({
      method: 'GET',
      url: '/pay-orders',
    });
  },

  // Generate payment order
  applyPayOrder: (data: PayApplyDTO) => {
    return request<string>({
      method: 'POST',
      url: '/pay-orders',
      data,
    });
  },

  // Attempt payment using user balance
  payByBalance: (id: number, data: PayOrderFormDTO) => {
    return request<void>({
      method: 'POST',
      url: `/pay-orders/${id}`,
      data,
    });
  },

  // Check order with ID
  getPayOrderByBizOrderNo: (id: number) => {
    return request<PayOrderDTO>({
      method: 'GET',
      url: `/pay-orders/biz/${id}`,
    });
  },
};
