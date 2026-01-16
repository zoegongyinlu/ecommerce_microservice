import { create } from 'zustand';
import { CartVO } from '@/types';

interface CartState {
  cartItems: CartVO[];
  cartCount: number;
  totalPrice: number;
  
  setCartItems: (items: CartVO[]) => void;
  addCartItem: (item: CartVO) => void;
  removeCartItem: (id: number) => void;
  updateCartItem: (id: number, num: number) => void;
  clearCart: () => void;
  calculateTotals: () => void;
}

export const useCartStore = create<CartState>((set, get) => ({
  cartItems: [],
  cartCount: 0,
  totalPrice: 0,

  setCartItems: (items) => {
    set({ cartItems: items });
    get().calculateTotals();
  },

  addCartItem: (item) => {
    const { cartItems } = get();
    const existingItem = cartItems.find((i) => i.itemId === item.itemId);
    
    if (existingItem) {
      set({
        cartItems: cartItems.map((i) =>
          i.itemId === item.itemId ? { ...i, num: i.num + item.num } : i
        ),
      });
    } else {
      set({ cartItems: [...cartItems, item] });
    }
    get().calculateTotals();
  },

  removeCartItem: (id) => {
    set((state) => ({
      cartItems: state.cartItems.filter((item) => item.id !== id),
    }));
    get().calculateTotals();
  },

  updateCartItem: (id, num) => {
    set((state) => ({
      cartItems: state.cartItems.map((item) =>
        item.id === id ? { ...item, num } : item
      ),
    }));
    get().calculateTotals();
  },

  clearCart: () => {
    set({ cartItems: [], cartCount: 0, totalPrice: 0 });
  },

  calculateTotals: () => {
    const { cartItems } = get();
    const count = cartItems.reduce((sum, item) => sum + item.num, 0);
    const total = cartItems.reduce((sum, item) => sum + item.price * item.num, 0);
    set({ cartCount: count, totalPrice: total });
  },
}));
