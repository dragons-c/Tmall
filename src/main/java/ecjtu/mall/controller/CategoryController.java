package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.utils.ImageUtil;
import ecjtu.mall.utils.Page;
import ecjtu.mall.utils.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * 分类
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查找所有分类数据，并用分页显示
     * 使用了分页插件
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("admin_category_list")
    public String findAll(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());//设置分页的当前起始页和总共显示的页数
        List<Category> list = categoryService.list();
        int total = (int) new PageInfo<>(list).getTotal();//获取数据总数
        page.setTotal(total);
        model.addAttribute("cs", list);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    /**
     * 添加新的分类，并且上传图片
     * @param category
     * @param session
     * @param uploadedImageFile
     * @return
     * @throws Exception
     */
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws Exception{
//       设置图片路径
        String path = session.getServletContext().getRealPath("img/category");
        categoryService.add(category);
        File file = new File(path);
        File file1 = new File(file,category.getId()+".jpg");
        if(!file1.getParentFile().exists()){
            file1.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file1);
        BufferedImage image = ImageUtil.change2jpg(file1);
        ImageIO.write(image,"jpg",file1);
        return "redirect:/admin_category_list";
    }

    /**
     * 删除方法，并且把路径下的图片删除
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session){
        categoryService.delete(id);
        File file = new File(session.getServletContext().getRealPath("img/category"));
        File file1 = new File(file,id+".jpg");
        file1.delete();
        return "redirect:/admin_category_list";
    }

    /**
     * 编辑图片方法，先跳到这个方法，然后再跳到修改方法
     * 可实现类别的更新
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("admin_category_edit")
    public String get(Model model,int id){
        Category category = categoryService.get(id);
        model.addAttribute("c",category);
        return "admin/editCategory";
    }

    /**
     * 更新方法
     * 判断更新时是否添加图片，假如添加了，则保存起来
     * @param c
     * @param image
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("admin_category_update")
    public String update(Category c,MultipartFile image,HttpSession session) throws Exception{
        categoryService.update(c);
        if(image !=null && !image.isEmpty()){
            File file = new File(session.getServletContext().getRealPath("img/category"));
            File file1 = new File(file,c.getId()+".jpg");
            image.transferTo(file1);
            BufferedImage img = ImageUtil.change2jpg(file1);
            ImageIO.write(img,"jpg",file1);
        }
        return "redirect:/admin_category_list";
    }
}
