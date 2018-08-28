package ecjtu.mall.service;

import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    OrderItem get(int id);
    List list();
    void fill(List<Order> orders);
    void fill(Order order);
    int getSaleCount(int pid);//根据产品获取销售量
    List<OrderItem> listByUser(int uid);
}
