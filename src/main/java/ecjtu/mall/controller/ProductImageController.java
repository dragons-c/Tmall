package ecjtu.mall.controller;

import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.ProductImage;
import ecjtu.mall.service.ProductImageService;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.utils.ImageUtil;
import ecjtu.mall.utils.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;

    /**
     * 显示照片列表
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("admin_productImage_list")
    public  String list(int pid, Model model){
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid,ProductImageService.type_detail);
        model.addAttribute("p",product);
        model.addAttribute("pisSingle",pisSingle);
        model.addAttribute("pisDetail",pisDetail);
        return "admin/listProductImage";
    }

    /**
     * 增加商品图片，但是需要判断是单个还是详情的图片
     * 然后操作和上传图片原理相似
     * @param session
     * @param productImage
     * @param uploadedImageFile
     * @return
     */
    @RequestMapping("admin_productImage_add")
    public String add(HttpSession session, ProductImage productImage, UploadedImageFile uploadedImageFile){
        productImageService.add(productImage);
        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if(ProductImageService.type_single.equals(productImage.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }else{
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        File f = new File(imageFolder,fileName);
        f.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(f);
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img,"jpg",f);
            if(ProductImageService.type_single.equals(productImage.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);

                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }

    /**
     * 删除商品图片
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session){
        ProductImage productImage = productImageService.get(id);
        String fileName = productImage.getId()+".jpg";
        String imageFolder = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if(ProductImageService.type_single.equals(productImage.getType())){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile = new File(imageFolder,fileName);
            File f_small = new File(imageFolder_small,fileName);
            File f_middle = new File(imageFolder_middle,fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();

        }else{
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder,fileName);
            imageFile.delete();
        }
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }
}
