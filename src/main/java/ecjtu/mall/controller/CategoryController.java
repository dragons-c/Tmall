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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
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
    private CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String findAll(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> list = categoryService.list();
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", list);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(MultipartFile image, Category category, HttpSession session) throws Exception{
        categoryService.add(category);
        String newName = category.getId().toString();
        String oldName = image.getOriginalFilename();
        String suf = oldName.substring(oldName.lastIndexOf("."));
        //路径
        String path = session.getServletContext().getRealPath("img/category");
        File file = new File(path,newName+suf);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_delete")
    public String delete(int id){
        categoryService.delete(id);
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String get(Model model,int id){
        Category category = categoryService.get(id);
        model.addAttribute("c",category);
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(Category c,MultipartFile image,HttpSession session) throws Exception{
        categoryService.update(c);
        if(image !=null && !image.isEmpty()){
            String newName = c.getId().toString();
            String oldName = image.getOriginalFilename();
            String suf = oldName.substring(oldName.lastIndexOf("."));
            //路径
            String path = session.getServletContext().getRealPath("img/category");
            File file = new File(path,newName+suf);
            image.transferTo(file);
        }
        return "redirect:/admin_category_list";
    }
}
