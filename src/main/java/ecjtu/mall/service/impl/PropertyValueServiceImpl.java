package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.PropertyValueMapper;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.Property;
import ecjtu.mall.pojo.PropertyValue;
import ecjtu.mall.pojo.PropertyValueExample;
import ecjtu.mall.service.PropertyValueService;
import ecjtu.mall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Override
    public void init(Product product) {
        List<Property> list = propertyService.list(product.getId());
        for (Property property:list
             ) {
            PropertyValue propertyValue =get(property.getId(),product.getId());
            if(propertyValue == null){
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
            propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> list = propertyValueMapper.selectByExample(example);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> list = propertyValueMapper.selectByExample(example);
        for (PropertyValue propertyValue:list
             ) {
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }
        return list;
    }
}
