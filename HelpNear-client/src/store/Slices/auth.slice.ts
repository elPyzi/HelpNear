import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { User } from '@/types/Users';
import Cookies from 'js-cookie';

type AuthState = {
  user: User | null;
};

const initialState: AuthState = {
  user: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<User>) => {
      state.user = action.payload;
    },
    logout: (state) => {
      Cookies.remove('accessToken');
      Cookies.remove('refreshToken');
      state.user = null;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
