package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Property;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.PropertyService;
import ecjtu.mall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    /**
     * 查询属性列表
     * @param model
     * @param page
     * @param cid
     * @return
     */
    @RequestMapping("admin_property_list")
    public String list(Model model, Page page, Integer cid){
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> list = propertyService.list(cid);
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+category.getId());
        model.addAttribute("c",category);
        model.addAttribute("ps",list);
        model.addAttribute("page",page);
        return "admin/listProperty";
    }

    /**
     * 添加属性
     * @param property
     * @return
     */
    @RequestMapping("admin_property_add")
    public String add(Property property){
        propertyService.add(property);

        return "redirect:admin_property_list?cid="+property.getCid();
    }

    /**
     * 编辑属性
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("admin_property_edit")
    public String edit(Model model,int id){
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("p",property);
        return "admin/editProperty";
    }

    /**
     * 更新属性
     * @param property
     * @return
     */
    @RequestMapping("admin_property_update")
    public String update(Property property){
        propertyService.update(property);
        return "redirect:admin_property_list?cid="+property.getCid();
    }

    /**
     * 删除属性
     * @param property
     * @return
     */
    @RequestMapping("admin_property_delete")
    public String delete(Property property){
        Property property1 = propertyService.get(property.getId());
        propertyService.delete(property.getId());
        return "redirect:admin_property_list?cid="+property1.getCid();
    }
}
