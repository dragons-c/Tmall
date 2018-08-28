package ecjtu.mall.controller;

import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.PropertyValue;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    ProductService productService;
    @Autowired
    PropertyValueService propertyValueService;
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model , int pid){
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> list = propertyValueService.list(product.getId());
        model.addAttribute("p",product);
        model.addAttribute("pvs",list);
        return "admin/editPropertyValue";
    }
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue){
        propertyValueService.update(propertyValue);
        return "success";
    }
}
