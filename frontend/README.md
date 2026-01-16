# eMall Frontend - React TypeScript Application

A modern, responsive e-commerce frontend application built with React, TypeScript, and Ant Design, connecting to the eMall microservices backend.

## Features

- **User Authentication**: Secure login with JWT token-based authentication
- **Product Catalog**: Browse, search, and filter products by category, brand, and price
- **Shopping Cart**: Add, update, and remove items from cart
- **Checkout Process**: Select shipping address and create orders
- **Payment Integration**: Process payments using balance payment method
- **Responsive Design**: Mobile-friendly UI with modern styling
- **State Management**: Zustand for efficient state management
- **API Integration**: Complete integration with all backend microservices

## Technology Stack

- **React 18.2** - UI library
- **TypeScript** - Type-safe development
- **Vite** - Fast build tool and dev server
- **Ant Design 5.14** - UI component library
- **React Router 6** - Client-side routing
- **Zustand 4.5** - State management
- **Axios** - HTTP client for API calls
- **React Query 3.39** - Server state management (optional)

## Project Structure

```
frontend/
├── src/
│   ├── components/        # Reusable components
│   │   ├── Layout.tsx     # Main layout with header/footer
│   │   └── ProtectedRoute.tsx  # Auth guard component
│   ├── pages/            # Page components
│   │   ├── Home.tsx      # Product catalog and search
│   │   ├── Login.tsx     # User authentication
│   │   ├── Cart.tsx      # Shopping cart
│   │   ├── Checkout.tsx  # Order checkout
│   │   └── Payment.tsx   # Payment processing
│   ├── services/         # API service layer
│   │   ├── user.service.ts
│   │   ├── item.service.ts
│   │   ├── cart.service.ts
│   │   ├── order.service.ts
│   │   └── payment.service.ts
│   ├── store/           # State management
│   │   ├── auth.store.ts
│   │   └── cart.store.ts
│   ├── types/           # TypeScript type definitions
│   │   └── index.ts     # All DTOs and interfaces
│   ├── utils/           # Utility functions
│   │   └── axios.ts     # Axios instance and interceptors
│   ├── App.tsx          # Main app component
│   ├── main.tsx         # App entry point
│   └── index.css        # Global styles
├── index.html           # HTML template
├── package.json         # Dependencies and scripts
├── tsconfig.json        # TypeScript configuration
└── vite.config.ts       # Vite configuration
```

## API Integration

The application integrates with the following microservices through the API Gateway (port 8080):

### User Service (`/users`, `/addresses`)
- User login and authentication
- Address management
- Balance operations

### Item Service (`/items`, `/search`)
- Product catalog management
- Product search and filtering
- Stock management

### Cart Service (`/carts`)
- Add/update/remove cart items
- View cart contents
- Batch operations

### Order Service (`/orders`)
- Create and manage orders
- Order status tracking
- Order payment confirmation

### Payment Service (`/pay-orders`)
- Payment order creation
- Balance payment processing
- Payment status tracking

## Prerequisites

Before running the application, ensure you have:

- **Node.js 16+** or higher
- **npm** or **yarn** package manager
- Backend services running on `http://localhost:8080`

## Installation

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

## Running the Application

### Development Mode

Start the development server with hot reload:

```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### Build for Production

Build the optimized production bundle:

```bash
npm run build
```

The built files will be in the `dist/` directory.

### Preview Production Build

Preview the production build locally:

```bash
npm run preview
```

## Configuration

### API Base URL

The application uses Vite's proxy to forward API requests to the backend gateway. Configuration is in `vite.config.ts`:

```typescript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, ''),
    },
  },
}
```

### Environment Variables

Create a `.env` file in the frontend root directory (if needed):

```
VITE_API_BASE_URL=http://localhost:8080
```

## Key Features Implementation

### Authentication

- JWT token stored in localStorage
- Token automatically attached to API requests via Axios interceptors
- Protected routes require authentication
- Auto-redirect to login on 401 responses

### State Management

**Auth Store:**
- Manages user authentication state
- Persists token and user info
- Handles login/logout operations

**Cart Store:**
- Manages shopping cart state
- Real-time cart count updates
- Total price calculations

### API Error Handling

- Global error handling via Axios interceptors
- User-friendly error messages with Ant Design notifications
- Network error detection
- Automatic token refresh on expiration

## Usage Guide

### 1. Login

Navigate to `/login` and enter your credentials:
- Username: Your user account username
- Password: Your password
- Check "Remember me" to persist the session

### 2. Browse Products

On the home page:
- Use the search bar to find products by name
- Filter by category, brand, or price range
- Click "Search" to apply filters
- Navigate through pages using pagination

### 3. Add to Cart

- Click "Add to Cart" button on any product
- Cart count updates in the header
- View cart by clicking the cart icon

### 4. Checkout

In the cart page:
- Select items to purchase (checkbox)
- Update quantities as needed
- Click "Checkout" to proceed

In the checkout page:
- Select a shipping address
- Review order items and total
- Click "Proceed to Payment"

### 5. Payment

On the payment page:
- Review order details
- Enter your payment password
- Click "Pay Now" to complete the purchase

## API Request Examples

### Login Request
```typescript
POST /api/users/login
{
  "username": "user123",
  "password": "password",
  "rememberMe": false
}
```

### Add to Cart
```typescript
POST /api/carts
{
  "itemId": 1,
  "name": "Product Name",
  "spec": "Specification",
  "price": 9999,
  "image": "image_url"
}
```

### Create Order
```typescript
POST /api/orders
{
  "addressId": 1,
  "paymentType": 1,
  "details": [
    { "itemId": 1, "num": 2 }
  ]
}
```

## Type Safety

All API responses and requests are fully typed using TypeScript interfaces. Example:

```typescript
interface ItemDTO {
  id: number;
  name: string;
  price: number;
  stock: number;
  image: string;
  category: string;
  brand: string;
  spec: string;
  sold: number;
  commentCount: number;
  isAD: boolean;
  status: number;
}
```

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Troubleshooting

### API Connection Issues

1. Ensure backend services are running on port 8080
2. Check browser console for CORS errors
3. Verify Vite proxy configuration

### Authentication Issues

1. Clear localStorage and try logging in again
2. Check if JWT token is valid
3. Verify backend authentication service is running

### Build Issues

1. Clear node_modules and reinstall:
```bash
rm -rf node_modules package-lock.json
npm install
```

2. Clear Vite cache:
```bash
rm -rf node_modules/.vite
```

## Future Enhancements

- [ ] User registration page
- [ ] User profile and order history
- [ ] Product details page with reviews
- [ ] Wishlist functionality
- [ ] Advanced search with Elasticsearch integration
- [ ] Real-time notifications with WebSocket
- [ ] Payment method selection (WeChat, Alipay)
- [ ] Admin dashboard for product management
- [ ] Internationalization (i18n)
- [ ] Dark mode support

## Contributing

When contributing to the frontend:

1. Follow TypeScript best practices
2. Use Ant Design components consistently
3. Maintain type safety
4. Add proper error handling
5. Write clean, documented code

## License

This project is part of the eMall e-commerce platform for educational purposes.

---

**Note**: This frontend application requires the eMall backend microservices to be running. Please refer to the main README in the project root for backend setup instructions.
