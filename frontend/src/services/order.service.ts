import { request } from '@/utils/axios';
import { OrderFormDTO, OrderVO } from '@/types';

export const orderService = {
  // Get order by ID
  getOrderById: (orderId: number) => {
    return request<OrderVO>({
      method: 'GET',
      url: `/orders/${orderId}`,
    });
  },

  // Create order
  createOrder: (data: OrderFormDTO) => {
    return request<number>({
      method: 'POST',
      url: '/orders',
      data,
    });
  },

  // Mark order as paid
  markOrderPaySuccess: (orderId: number) => {
    return request<void>({
      method: 'PUT',
      url: `/orders/${orderId}`,
    });
  },
};
