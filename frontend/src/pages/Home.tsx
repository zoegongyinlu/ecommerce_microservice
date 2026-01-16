import React, { useEffect, useState } from 'react';
import { Row, Col, Card, Input, Button, Select, Pagination, Spin, message, InputNumber } from 'antd';
import { ShoppingCartOutlined, SearchOutlined } from '@ant-design/icons';
import { itemService } from '@/services/item.service';
import { cartService } from '@/services/cart.service';
import { ItemDTO, ItemPageQuery } from '@/types';
import { useCartStore } from '@/store/cart.store';
import { useAuthStore } from '@/store/auth.store';
import './Home.css';

const { Search } = Input;
const { Option } = Select;

const Home: React.FC = () => {
  const [items, setItems] = useState<ItemDTO[]>([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [query, setQuery] = useState<ItemPageQuery>({
    pageNo: 1,
    pageSize: 12,
    key: '',
    category: undefined,
    brand: undefined,
    minPrice: undefined,
    maxPrice: undefined,
  });

  const { isAuthenticated } = useAuthStore();
  const { setCartItems } = useCartStore();

  useEffect(() => {
    loadItems();
  }, [query.pageNo, query.pageSize]);

  useEffect(() => {
    if (isAuthenticated) {
      loadCart();
    }
  }, [isAuthenticated]);

  const loadItems = async () => {
    setLoading(true);
    try {
      const response = await itemService.searchItems(query);
      setItems(response.list);
      setTotal(response.total);
    } catch (error) {
      console.error('Failed to load items:', error);
      message.error('Failed to load products');
    } finally {
      setLoading(false);
    }
  };

  const loadCart = async () => {
    try {
      const cartItems = await cartService.getMyCarts();
      setCartItems(cartItems);
    } catch (error) {
      console.error('Failed to load cart:', error);
    }
  };

  const handleSearch = () => {
    setQuery({ ...query, pageNo: 1 });
    loadItems();
  };

  const handleAddToCart = async (item: ItemDTO) => {
    if (!isAuthenticated) {
      message.warning('Please login first');
      return;
    }

    try {
      await cartService.addItemToCart({
        itemId: item.id,
        name: item.name,
        spec: item.spec,
        price: item.price,
        image: item.image,
      });
      message.success('Added to cart successfully');
      loadCart();
    } catch (error) {
      console.error('Failed to add to cart:', error);
      message.error('Failed to add to cart');
    }
  };

  const formatPrice = (price: number) => {
    return (price / 100).toFixed(2);
  };

  return (
    <div className="home-container">
      <div className="search-section">
        <Row gutter={16}>
          <Col span={8}>
            <Search
              placeholder="Search products..."
              allowClear
              enterButton={<SearchOutlined />}
              size="large"
              value={query.key}
              onChange={(e) => setQuery({ ...query, key: e.target.value })}
              onSearch={handleSearch}
            />
          </Col>
          <Col span={4}>
            <Select
              placeholder="Category"
              allowClear
              size="large"
              style={{ width: '100%' }}
              value={query.category}
              onChange={(value) => setQuery({ ...query, category: value })}
            >
              <Option value="Electronics">Electronics</Option>
              <Option value="Clothing">Clothing</Option>
              <Option value="Books">Books</Option>
              <Option value="Home">Home</Option>
            </Select>
          </Col>
          <Col span={4}>
            <Select
              placeholder="Brand"
              allowClear
              size="large"
              style={{ width: '100%' }}
              value={query.brand}
              onChange={(value) => setQuery({ ...query, brand: value })}
            >
              <Option value="Apple">Apple</Option>
              <Option value="Samsung">Samsung</Option>
              <Option value="Nike">Nike</Option>
              <Option value="Adidas">Adidas</Option>
            </Select>
          </Col>
          <Col span={3}>
            <InputNumber
              placeholder="Min Price"
              size="large"
              style={{ width: '100%' }}
              value={query.minPrice}
              onChange={(value) => setQuery({ ...query, minPrice: value || undefined })}
            />
          </Col>
          <Col span={3}>
            <InputNumber
              placeholder="Max Price"
              size="large"
              style={{ width: '100%' }}
              value={query.maxPrice}
              onChange={(value) => setQuery({ ...query, maxPrice: value || undefined })}
            />
          </Col>
          <Col span={2}>
            <Button type="primary" size="large" block onClick={handleSearch}>
              Search
            </Button>
          </Col>
        </Row>
      </div>

      <Spin spinning={loading}>
        <Row gutter={[16, 16]} className="product-grid">
          {items.map((item) => (
            <Col key={item.id} xs={24} sm={12} md={8} lg={6}>
              <Card
                hoverable
                cover={
                  <img
                    alt={item.name}
                    src={item.image || 'https://via.placeholder.com/300'}
                    style={{ height: 200, objectFit: 'cover' }}
                  />
                }
              >
                <Card.Meta
                  title={item.name}
                  description={
                    <div>
                      <div className="item-price">${formatPrice(item.price)}</div>
                      <div className="item-info">
                        <span>Stock: {item.stock}</span>
                        <span>Sold: {item.sold}</span>
                      </div>
                      <div className="item-category">
                        {item.category} - {item.brand}
                      </div>
                    </div>
                  }
                />
                <Button
                  type="primary"
                  icon={<ShoppingCartOutlined />}
                  block
                  style={{ marginTop: 16 }}
                  onClick={() => handleAddToCart(item)}
                  disabled={item.stock === 0}
                >
                  {item.stock === 0 ? 'Out of Stock' : 'Add to Cart'}
                </Button>
              </Card>
            </Col>
          ))}
        </Row>
      </Spin>

      <div className="pagination-section">
        <Pagination
          current={query.pageNo}
          pageSize={query.pageSize}
          total={total}
          onChange={(page, pageSize) => setQuery({ ...query, pageNo: page, pageSize: pageSize || 12 })}
          showSizeChanger
          showTotal={(total) => `Total ${total} items`}
        />
      </div>
    </div>
  );
};

export default Home;
