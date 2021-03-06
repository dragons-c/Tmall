package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    /**
     * 查询商品
     * @param model
     * @param page
     * @param cid
     * @return
     */
    @RequestMapping("admin_product_list")
    public String list(Model model, Page page,int cid){
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> list = productService.list(cid);
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+category.getId());
        model.addAttribute("ps",list);
        model.addAttribute("c",category);
        model.addAttribute("page",page);
        return "admin/listProduct";

    }

    /**
     * 增加商品
     * @param product
     * @return
     */
    @RequestMapping("admin_product_add")
    public String add(Product product){
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
    @RequestMapping("admin_product_edit")
    public String edit(int id,Model model){
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("p",product);
        return "admin/editProduct";
    }
    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
}
