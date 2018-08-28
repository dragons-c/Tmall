package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.OrderItemMapper;
import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderItem;
import ecjtu.mall.pojo.OrderItemExample;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.service.OrderItemService;
import ecjtu.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        return orderItems;
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order:orders
             ) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : orderItems) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        int result = 0;
        for (OrderItem orderItem:orderItems
             ) {
            result +=orderItem.getNumber();
        }
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    public void setProduct(OrderItem orderItem){
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }
    public void setProduct(List<OrderItem> orderItems){
        for (OrderItem orderItem:orderItems
             ) {
            setProduct(orderItem);
        }
    }

}
