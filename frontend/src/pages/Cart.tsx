import React, { useEffect, useState } from 'react';
import { Table, Button, InputNumber, message, Card, Empty, Popconfirm } from 'antd';
import { DeleteOutlined, ShoppingOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { cartService } from '@/services/cart.service';
import { useCartStore } from '@/store/cart.store';
import { CartVO } from '@/types';
import './Cart.css';

const Cart: React.FC = () => {
  const navigate = useNavigate();
  const { cartItems, setCartItems, removeCartItem, updateCartItem, calculateTotals } = useCartStore();
  const [loading, setLoading] = useState(false);
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = async () => {
    setLoading(true);
    try {
      const items = await cartService.getMyCarts();
      setCartItems(items);
    } catch (error) {
      console.error('Failed to load cart:', error);
      message.error('Failed to load cart');
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = async (id: number, value: number | null) => {
    if (!value || value < 1) return;

    try {
      await cartService.updateCart({ id, num: value });
      updateCartItem(id, value);
      message.success('Quantity updated');
    } catch (error) {
      console.error('Failed to update quantity:', error);
      message.error('Failed to update quantity');
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await cartService.deleteCartItem(id);
      removeCartItem(id);
      message.success('Item removed from cart');
    } catch (error) {
      console.error('Failed to delete item:', error);
      message.error('Failed to delete item');
    }
  };

  const handleBatchDelete = async () => {
    if (selectedRowKeys.length === 0) {
      message.warning('Please select items to delete');
      return;
    }

    try {
      await cartService.deleteCartItems(selectedRowKeys as number[]);
      selectedRowKeys.forEach((key) => removeCartItem(key as number));
      setSelectedRowKeys([]);
      message.success('Selected items removed from cart');
    } catch (error) {
      console.error('Failed to delete items:', error);
      message.error('Failed to delete items');
    }
  };

  const handleCheckout = () => {
    if (selectedRowKeys.length === 0) {
      message.warning('Please select items to checkout');
      return;
    }
    navigate('/checkout', { state: { selectedItems: selectedRowKeys } });
  };

  const formatPrice = (price: number) => {
    return (price / 100).toFixed(2);
  };

  const calculateSelectedTotal = () => {
    return cartItems
      .filter((item) => selectedRowKeys.includes(item.id))
      .reduce((sum, item) => sum + item.price * item.num, 0);
  };

  const columns = [
    {
      title: 'Product',
      dataIndex: 'name',
      key: 'name',
      render: (text: string, record: CartVO) => (
        <div className="cart-item">
          <img src={record.image || 'https://via.placeholder.com/80'} alt={text} />
          <div>
            <div className="item-name">{text}</div>
            <div className="item-spec">{record.spec}</div>
          </div>
        </div>
      ),
    },
    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      render: (price: number) => <span className="price">${formatPrice(price)}</span>,
    },
    {
      title: 'Quantity',
      dataIndex: 'num',
      key: 'num',
      render: (num: number, record: CartVO) => (
        <InputNumber
          min={1}
          value={num}
          onChange={(value) => handleQuantityChange(record.id, value)}
        />
      ),
    },
    {
      title: 'Subtotal',
      key: 'subtotal',
      render: (record: CartVO) => (
        <span className="price">${formatPrice(record.price * record.num)}</span>
      ),
    },
    {
      title: 'Action',
      key: 'action',
      render: (record: CartVO) => (
        <Popconfirm
          title="Are you sure to delete this item?"
          onConfirm={() => handleDelete(record.id)}
          okText="Yes"
          cancelText="No"
        >
          <Button type="link" danger icon={<DeleteOutlined />}>
            Delete
          </Button>
        </Popconfirm>
      ),
    },
  ];

  const rowSelection = {
    selectedRowKeys,
    onChange: (newSelectedRowKeys: React.Key[]) => {
      setSelectedRowKeys(newSelectedRowKeys);
    },
  };

  if (cartItems.length === 0 && !loading) {
    return (
      <div className="cart-container">
        <Card>
          <Empty
            description="Your cart is empty"
            image={Empty.PRESENTED_IMAGE_SIMPLE}
          >
            <Button type="primary" onClick={() => navigate('/')}>
              Start Shopping
            </Button>
          </Empty>
        </Card>
      </div>
    );
  }

  return (
    <div className="cart-container">
      <Card title="Shopping Cart">
        <Table
          rowSelection={rowSelection}
          columns={columns}
          dataSource={cartItems}
          rowKey="id"
          loading={loading}
          pagination={false}
        />

        <div className="cart-actions">
          <div className="left-actions">
            <Button onClick={handleBatchDelete} disabled={selectedRowKeys.length === 0}>
              Delete Selected
            </Button>
          </div>
          <div className="right-actions">
            <div className="total-section">
              <span className="total-label">Total ({selectedRowKeys.length} items):</span>
              <span className="total-price">${formatPrice(calculateSelectedTotal())}</span>
            </div>
            <Button
              type="primary"
              size="large"
              icon={<ShoppingOutlined />}
              onClick={handleCheckout}
              disabled={selectedRowKeys.length === 0}
            >
              Checkout
            </Button>
          </div>
        </div>
      </Card>
    </div>
  );
};

export default Cart;
