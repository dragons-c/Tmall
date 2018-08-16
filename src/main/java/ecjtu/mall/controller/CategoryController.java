/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryController
 * Author:   jack
 * Date:     18/08/16 16:54
 * Description: 商品分类Controller层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类Controller层〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping("admin_property_list")
    public String findAll(Model model) {
        List<Category> list = service.findAll();
        System.out.println("liebiao");
        model.addAttribute("cs", list);
        return "admin/listCategory";
    }

}
