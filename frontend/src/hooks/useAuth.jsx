import { create } from "zustand";
import { authService, removeAuthToken, setAuthToken } from '@/services/auth';

export const useAuth = create(set => ({
  user: null,
  isLoading: false,
  error: null,

  login: async (username, password) => {
    set({ isLoading: true, error: null });
    try {
      const data = await authService.login(username, password);
      setAuthToken(data.token);
      set({ user: data.user, isLoading: false });
      return true;
    } catch (error) {
      set({ error: error.message, isLoading: false });
      return false;
    }
  },

  register: async (username, email, password) => {
    set({ isLoading: true, error: null });
    try {
      const data = await authService.register(username, email, password);
      set({ isLoading: false });
      return true;
    } catch (error) {
      set({ error: error.message, isLoading: false });
      return false;
    }
  },

  logout: () => {
    removeAuthToken();
    set({ user: null });
  },

  clearError: () => set({ error: null }),
}));
