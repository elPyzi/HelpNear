import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import { routes } from '@/routes';

const router = createBrowserRouter(routes);
const client = new QueryClient();

import './App.scss';

const App = () => (
  <QueryClientProvider client={client}>
    <RouterProvider router={router} />
  </QueryClientProvider>
);

export default App;
