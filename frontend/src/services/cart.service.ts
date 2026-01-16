import { request } from '@/utils/axios';
import { CartFormDTO, CartVO, Cart } from '@/types';

export const cartService = {
  // Add item to shopping cart
  addItemToCart: (data: CartFormDTO) => {
    return request<void>({
      method: 'POST',
      url: '/carts',
      data,
    });
  },

  // Update shopping cart data
  updateCart: (cart: Partial<Cart>) => {
    return request<void>({
      method: 'PUT',
      url: '/carts',
      data: cart,
    });
  },

  // Delete item from shopping cart
  deleteCartItem: (id: number) => {
    return request<void>({
      method: 'DELETE',
      url: `/carts/${id}`,
    });
  },

  // Get shopping cart list
  getMyCarts: () => {
    return request<CartVO[]>({
      method: 'GET',
      url: '/carts',
    });
  },

  // Batch delete items from shopping cart
  deleteCartItems: (ids: number[]) => {
    return request<void>({
      method: 'DELETE',
      url: '/carts',
      params: { ids },
    });
  },
};
