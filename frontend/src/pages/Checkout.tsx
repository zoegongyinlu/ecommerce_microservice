import React, { useEffect, useState } from 'react';
import { Card, Form, Radio, Button, List, message, Spin } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import { userService } from '@/services/user.service';
import { cartService } from '@/services/cart.service';
import { orderService } from '@/services/order.service';
import { AddressDTO, CartVO, OrderDetailDTO } from '@/types';
import './Checkout.css';

const Checkout: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const selectedItemIds = (location.state as any)?.selectedItems as number[] || [];

  const [addresses, setAddresses] = useState<AddressDTO[]>([]);
  const [selectedAddress, setSelectedAddress] = useState<number | undefined>();
  const [cartItems, setCartItems] = useState<CartVO[]>([]);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    loadAddresses();
    loadCartItems();
  }, []);

  const loadAddresses = async () => {
    try {
      const data = await userService.getMyAddresses();
      setAddresses(data);
      const defaultAddress = data.find((addr) => addr.isDefault === 1);
      if (defaultAddress) {
        setSelectedAddress(defaultAddress.id);
      }
    } catch (error) {
      console.error('Failed to load addresses:', error);
      message.error('Failed to load addresses');
    }
  };

  const loadCartItems = async () => {
    setLoading(true);
    try {
      const allItems = await cartService.getMyCarts();
      const selected = allItems.filter((item) => selectedItemIds.includes(item.id));
      setCartItems(selected);
    } catch (error) {
      console.error('Failed to load cart items:', error);
      message.error('Failed to load cart items');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async () => {
    if (!selectedAddress) {
      message.warning('Please select a shipping address');
      return;
    }

    setSubmitting(true);
    try {
      const orderDetails: OrderDetailDTO[] = cartItems.map((item) => ({
        itemId: item.itemId,
        num: item.num,
      }));

      const orderId = await orderService.createOrder({
        addressId: selectedAddress,
        paymentType: 1, // Balance payment
        details: orderDetails,
      });

      message.success('Order created successfully!');
      
      // Delete checked out items from cart
      await cartService.deleteCartItems(selectedItemIds);
      
      // Navigate to payment page
      navigate('/payment', { state: { orderId } });
    } catch (error) {
      console.error('Failed to create order:', error);
      message.error('Failed to create order');
    } finally {
      setSubmitting(false);
    }
  };

  const formatPrice = (price: number) => {
    return (price / 100).toFixed(2);
  };

  const calculateTotal = () => {
    return cartItems.reduce((sum, item) => sum + item.price * item.num, 0);
  };

  if (loading) {
    return (
      <div className="checkout-container">
        <Spin size="large" />
      </div>
    );
  }

  return (
    <div className="checkout-container">
      <Card title="Checkout" className="checkout-card">
        {/* Shipping Address */}
        <div className="section">
          <h3>Shipping Address</h3>
          {addresses.length === 0 ? (
            <div>No address found. Please add an address first.</div>
          ) : (
            <Radio.Group
              value={selectedAddress}
              onChange={(e) => setSelectedAddress(e.target.value)}
              style={{ width: '100%' }}
            >
              {addresses.map((address) => (
                <Radio key={address.id} value={address.id} style={{ display: 'block', marginBottom: 16 }}>
                  <div className="address-item">
                    <div className="address-contact">
                      {address.contact} - {address.mobile}
                      {address.isDefault === 1 && <span className="default-badge">Default</span>}
                    </div>
                    <div className="address-detail">
                      {address.province} {address.city} {address.town} {address.street}
                    </div>
                    {address.notes && <div className="address-notes">Note: {address.notes}</div>}
                  </div>
                </Radio>
              ))}
            </Radio.Group>
          )}
        </div>

        {/* Order Items */}
        <div className="section">
          <h3>Order Items</h3>
          <List
            dataSource={cartItems}
            renderItem={(item) => (
              <List.Item>
                <div className="order-item">
                  <img src={item.image || 'https://via.placeholder.com/80'} alt={item.name} />
                  <div className="item-info">
                    <div className="item-name">{item.name}</div>
                    <div className="item-spec">{item.spec}</div>
                  </div>
                  <div className="item-quantity">x{item.num}</div>
                  <div className="item-price">${formatPrice(item.price * item.num)}</div>
                </div>
              </List.Item>
            )}
          />
        </div>

        {/* Order Summary */}
        <div className="section summary">
          <div className="summary-row">
            <span>Items Total:</span>
            <span className="summary-price">${formatPrice(calculateTotal())}</span>
          </div>
          <div className="summary-row total">
            <span>Total:</span>
            <span className="summary-price">${formatPrice(calculateTotal())}</span>
          </div>
        </div>

        {/* Submit Button */}
        <Button
          type="primary"
          size="large"
          block
          onClick={handleSubmit}
          loading={submitting}
          disabled={!selectedAddress || cartItems.length === 0}
        >
          Proceed to Payment
        </Button>
      </Card>
    </div>
  );
};

export default Checkout;
