// API Response Types
export interface ApiResponse<T = any> {
  success: boolean;
  data: T;
  message?: string;
}

export interface PageDTO<T> {
  list: T[];
  total: number;
  pages: number;
}

export interface PageQuery {
  pageNo: number;
  pageSize: number;
  sortBy?: string;
  isAsc?: boolean;
}

// User Service Types
export interface LoginFormDTO {
  username: string;
  password: string;
  rememberMe?: boolean;
}

export interface UserLoginVO {
  userId: number;
  username: string;
  token: string;
  balance?: number;
}

export interface AddressDTO {
  id?: number;
  province: string;
  city: string;
  town: string;
  mobile: string;
  street: string;
  contact: string;
  isDefault: number;
  notes?: string;
}

// Item Service Types
export interface ItemDTO {
  id: number;
  name: string;
  price: number; // in cents
  stock: number;
  image: string;
  category: string;
  brand: string;
  spec: string;
  sold: number;
  commentCount: number;
  isAD: boolean;
  status: number; // 1 - Active, 2 - Unlisted, 3 - Deleted
}

export interface ItemPageQuery extends PageQuery {
  key?: string;
  brand?: string;
  category?: string;
  minPrice?: number;
  maxPrice?: number;
}

// Cart Service Types
export interface CartFormDTO {
  itemId: number;
  name: string;
  spec: string;
  price: number; // in cents
  image: string;
}

export interface CartVO {
  id: number;
  userId: number;
  itemId: number;
  name: string;
  spec: string;
  price: number;
  num: number;
  image: string;
}

export interface Cart {
  id: number;
  userId: number;
  itemId: number;
  num: number;
}

// Order Service Types
export interface OrderDetailDTO {
  itemId: number;
  num: number;
}

export interface OrderFormDTO {
  addressId: number;
  paymentType: number;
  details: OrderDetailDTO[];
}

export interface OrderVO {
  id: number;
  totalFee: number;
  paymentType: number;
  userId: number;
  status: number;
  addressId: number;
  createTime: string;
  updateTime: string;
}

// Payment Service Types
export interface PayApplyDTO {
  bizOrderNo: number;
  amount: number;
  payChannelCode: string;
  payType: number;
  orderInfo: string;
}

export interface PayOrderFormDTO {
  id: number;
  pw: string;
}

export interface PayOrderVO {
  id: number;
  bizOrderNo: number;
  payOrderNo: number;
  bizUserId: number;
  amount: number;
  paySuccessTime?: string;
  payOverTime: string;
  payChannelCode: string;
  status: number;
  payType: number;
  qrCodeUrl?: string;
}

export interface PayOrderDTO {
  id: number;
  bizOrderNo: number;
  payOrderNo: number;
  bizUserId: number;
  amount: number;
  paySuccessTime?: string;
  payOverTime: string;
  payChannelCode: string;
  status: number;
  payType: number;
  qrCodeUrl?: string;
}

// Enums
export enum PaymentType {
  BALANCE = 1,
  WECHAT = 2,
  ALIPAY = 3,
}

export enum OrderStatus {
  PENDING = 1,
  PAID = 2,
  SHIPPED = 3,
  DELIVERED = 4,
  CANCELLED = 5,
}

export enum ItemStatus {
  ACTIVE = 1,
  UNLISTED = 2,
  DELETED = 3,
}
