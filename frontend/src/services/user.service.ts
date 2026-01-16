import { request } from '@/utils/axios';
import { LoginFormDTO, UserLoginVO, AddressDTO } from '@/types';

export const userService = {
  // User authentication
  login: (data: LoginFormDTO) => {
    return request<UserLoginVO>({
      method: 'POST',
      url: '/users/login',
      data,
    });
  },

  // Balance deduction
  deductMoney: (pw: string, amount: number) => {
    return request<void>({
      method: 'PUT',
      url: '/users/money/deduct',
      params: { pw, amount },
    });
  },

  // Get address by ID
  getAddressById: (addressId: number) => {
    return request<AddressDTO>({
      method: 'GET',
      url: `/addresses/${addressId}`,
    });
  },

  // Get current user's addresses
  getMyAddresses: () => {
    return request<AddressDTO[]>({
      method: 'GET',
      url: '/addresses',
    });
  },
};
