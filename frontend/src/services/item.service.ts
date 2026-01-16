import { request } from '@/utils/axios';
import { ItemDTO, ItemPageQuery, PageDTO, OrderDetailDTO } from '@/types';

export const itemService = {
  // Paginated query for items
  getItemsPage: (query: ItemPageQuery) => {
    return request<PageDTO<ItemDTO>>({
      method: 'GET',
      url: '/items/page',
      params: query,
    });
  },

  // Query items by ID list
  getItemsByIds: (ids: number[]) => {
    return request<ItemDTO[]>({
      method: 'GET',
      url: '/items',
      params: { ids },
    });
  },

  // Query item by ID
  getItemById: (id: number) => {
    return request<ItemDTO>({
      method: 'GET',
      url: `/items/${id}`,
    });
  },

  // Add new item (admin)
  addItem: (item: Partial<ItemDTO>) => {
    return request<void>({
      method: 'POST',
      url: '/items',
      data: item,
    });
  },

  // Update item status (admin)
  updateItemStatus: (id: number, status: number) => {
    return request<void>({
      method: 'PUT',
      url: `/items/status/${id}/${status}`,
    });
  },

  // Update item details (admin)
  updateItem: (item: Partial<ItemDTO>) => {
    return request<void>({
      method: 'PUT',
      url: '/items',
      data: item,
    });
  },

  // Delete item by ID (admin)
  deleteItem: (id: number) => {
    return request<void>({
      method: 'DELETE',
      url: `/items/${id}`,
    });
  },

  // Batch deduct stock
  deductStock: (items: OrderDetailDTO[]) => {
    return request<void>({
      method: 'PUT',
      url: '/items/stock/deduct',
      data: items,
    });
  },

  // Search products
  searchItems: (query: ItemPageQuery) => {
    return request<PageDTO<ItemDTO>>({
      method: 'GET',
      url: '/search/list',
      params: query,
    });
  },
};
