import React, { useEffect, useState } from 'react';
import { Card, Form, Input, Button, Result, message, Spin, Descriptions } from 'antd';
import { LockOutlined, CheckCircleOutlined } from '@ant-design/icons';
import { useNavigate, useLocation } from 'react-router-dom';
import { paymentService } from '@/services/payment.service';
import { orderService } from '@/services/order.service';
import { PayOrderDTO, OrderVO } from '@/types';
import './Payment.css';

const Payment: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const orderId = (location.state as any)?.orderId as number;

  const [order, setOrder] = useState<OrderVO | null>(null);
  const [payOrder, setPayOrder] = useState<PayOrderDTO | null>(null);
  const [loading, setLoading] = useState(false);
  const [paying, setPaying] = useState(false);
  const [paymentSuccess, setPaymentSuccess] = useState(false);

  useEffect(() => {
    if (!orderId) {
      message.error('Invalid order');
      navigate('/');
      return;
    }
    loadOrder();
  }, [orderId]);

  const loadOrder = async () => {
    setLoading(true);
    try {
      const orderData = await orderService.getOrderById(orderId);
      setOrder(orderData);

      // Create payment order
      const payOrderNo = await paymentService.applyPayOrder({
        bizOrderNo: orderId,
        amount: orderData.totalFee,
        payChannelCode: 'balance',
        payType: 1, // Balance payment
        orderInfo: `Order #${orderId}`,
      });

      // Load payment order details
      const payOrderData = await paymentService.getPayOrderByBizOrderNo(orderId);
      setPayOrder(payOrderData);
    } catch (error) {
      console.error('Failed to load order:', error);
      message.error('Failed to load order');
    } finally {
      setLoading(false);
    }
  };

  const handlePayment = async (values: { pw: string }) => {
    if (!payOrder) {
      message.error('Payment order not found');
      return;
    }

    setPaying(true);
    try {
      // Process payment
      await paymentService.payByBalance(payOrder.id, {
        id: payOrder.id,
        pw: values.pw,
      });

      // Mark order as paid
      await orderService.markOrderPaySuccess(orderId);

      setPaymentSuccess(true);
      message.success('Payment successful!');
    } catch (error) {
      console.error('Payment failed:', error);
      message.error('Payment failed. Please check your password and balance.');
    } finally {
      setPaying(false);
    }
  };

  const formatPrice = (price: number) => {
    return (price / 100).toFixed(2);
  };

  if (loading) {
    return (
      <div className="payment-container">
        <Spin size="large" tip="Loading payment information..." />
      </div>
    );
  }

  if (paymentSuccess) {
    return (
      <div className="payment-container">
        <Result
          status="success"
          icon={<CheckCircleOutlined />}
          title="Payment Successful!"
          subTitle={`Order #${orderId} has been paid successfully.`}
          extra={[
            <Button type="primary" key="home" onClick={() => navigate('/')}>
              Back to Home
            </Button>,
            <Button key="orders" onClick={() => navigate('/orders')}>
              View Orders
            </Button>,
          ]}
        />
      </div>
    );
  }

  return (
    <div className="payment-container">
      <Card title="Payment" className="payment-card">
        {order && (
          <div className="order-info">
            <h3>Order Information</h3>
            <Descriptions column={1} bordered>
              <Descriptions.Item label="Order ID">{order.id}</Descriptions.Item>
              <Descriptions.Item label="Amount">
                <span className="amount">${formatPrice(order.totalFee)}</span>
              </Descriptions.Item>
              <Descriptions.Item label="Payment Method">Balance Payment</Descriptions.Item>
            </Descriptions>
          </div>
        )}

        <div className="payment-form">
          <h3>Enter Payment Password</h3>
          <Form onFinish={handlePayment} layout="vertical">
            <Form.Item
              name="pw"
              label="Payment Password"
              rules={[{ required: true, message: 'Please enter your payment password' }]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="Enter payment password"
                size="large"
              />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                size="large"
                block
                loading={paying}
              >
                Pay Now
              </Button>
            </Form.Item>

            <Form.Item>
              <Button
                size="large"
                block
                onClick={() => navigate('/')}
              >
                Cancel
              </Button>
            </Form.Item>
          </Form>
        </div>
      </Card>
    </div>
  );
};

export default Payment;
