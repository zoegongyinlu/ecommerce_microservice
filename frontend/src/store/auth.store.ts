import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface AuthState {
  token: string | null;
  userId: number | null;
  username: string | null;
  balance: number | null;
  isAuthenticated: boolean;
  
  login: (token: string, userId: number, username: string, balance?: number) => void;
  logout: () => void;
  updateBalance: (balance: number) => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      userId: null,
      username: null,
      balance: null,
      isAuthenticated: false,

      login: (token, userId, username, balance) => {
        localStorage.setItem('token', token);
        localStorage.setItem('userId', userId.toString());
        set({
          token,
          userId,
          username,
          balance: balance || null,
          isAuthenticated: true,
        });
      },

      logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        set({
          token: null,
          userId: null,
          username: null,
          balance: null,
          isAuthenticated: false,
        });
      },

      updateBalance: (balance) => {
        set({ balance });
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);
