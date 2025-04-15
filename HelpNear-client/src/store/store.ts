import { configureStore, combineReducers } from '@reduxjs/toolkit';

import authReducer from './Slices/auth.slice';

const rootReducer = combineReducers({ auth: authReducer });

export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type AppStore = typeof store;
