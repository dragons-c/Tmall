package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.OrderMapper;
import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderExample;
import ecjtu.mall.pojo.OrderItem;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.OrderItemService;
import ecjtu.mall.service.OrderService;
import ecjtu.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;
    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);

        if(false)
            throw new RuntimeException();

        for (OrderItem oi: ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;

    }

    @Override
    public List list(int uid, String excludedStatus) {
        OrderExample example =new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    public void setUser(Order order){
        User user = userService.get(order.getUid());
        order.setUser(user);
    }
    public void setUser(List<Order> orders){
        for (Order order:orders
             ) {
            setUser(order);
        }
    }
}
