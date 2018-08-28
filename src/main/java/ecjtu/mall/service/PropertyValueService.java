package ecjtu.mall.service;

import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product product);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid,int pid);
    List<PropertyValue> list(int pid);
}
