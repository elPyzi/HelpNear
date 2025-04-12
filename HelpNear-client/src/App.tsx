import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { Provider } from 'react-redux';

import { routes } from '@/routes';
import { store } from '@store/store';

const router = createBrowserRouter(routes);
const client = new QueryClient();

import './App.scss';

const App = () => (
  <Provider store={store}>
    <QueryClientProvider client={client}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </Provider>
);

export default App;
