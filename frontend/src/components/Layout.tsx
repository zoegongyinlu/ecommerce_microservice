import React from 'react';
import { Layout as AntLayout, Menu, Badge, Button, Dropdown } from 'antd';
import {
  ShoppingCartOutlined,
  UserOutlined,
  HomeOutlined,
  LogoutOutlined,
  LoginOutlined,
} from '@ant-design/icons';
import { useNavigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '@/store/auth.store';
import { useCartStore } from '@/store/cart.store';
import type { MenuProps } from 'antd';
import './Layout.css';

const { Header, Content, Footer } = AntLayout;

const MainLayout: React.FC = () => {
  const navigate = useNavigate();
  const { isAuthenticated, username, logout } = useAuthStore();
  const { cartCount } = useCartStore();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const userMenuItems: MenuProps['items'] = [
    {
      key: 'profile',
      label: 'Profile',
      icon: <UserOutlined />,
      onClick: () => navigate('/profile'),
    },
    {
      key: 'logout',
      label: 'Logout',
      icon: <LogoutOutlined />,
      onClick: handleLogout,
    },
  ];

  return (
    <AntLayout className="main-layout">
      <Header className="header">
        <div className="logo" onClick={() => navigate('/')}>
          <HomeOutlined /> eMall
        </div>
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[]}
          items={[
            {
              key: 'home',
              label: 'Home',
              icon: <HomeOutlined />,
              onClick: () => navigate('/'),
            },
          ]}
        />
        <div className="header-actions">
          <Badge count={cartCount} showZero>
            <Button
              type="text"
              icon={<ShoppingCartOutlined />}
              size="large"
              onClick={() => navigate('/cart')}
              style={{ color: 'white' }}
            >
              Cart
            </Button>
          </Badge>
          {isAuthenticated ? (
            <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
              <Button type="text" icon={<UserOutlined />} style={{ color: 'white' }}>
                {username}
              </Button>
            </Dropdown>
          ) : (
            <Button
              type="text"
              icon={<LoginOutlined />}
              onClick={() => navigate('/login')}
              style={{ color: 'white' }}
            >
              Login
            </Button>
          )}
        </div>
      </Header>
      <Content className="content">
        <Outlet />
      </Content>
      <Footer className="footer">
        eMall E-commerce Platform ©{new Date().getFullYear()} - Built with React & TypeScript
      </Footer>
    </AntLayout>
  );
};

export default MainLayout;
