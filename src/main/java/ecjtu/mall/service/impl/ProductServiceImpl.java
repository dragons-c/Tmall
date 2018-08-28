package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.ProductMapper;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.ProductExample;
import ecjtu.mall.pojo.ProductImage;
import ecjtu.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product= productMapper.selectByPrimaryKey(id);
        setCategory(product);
        setFirstProductImage(product);
        return product;
    }
    public void setCategory(Product product){
        int cid = product.getCid();
        Category category = categoryService.get(cid);
        product.setCategory(category);
    }
    public void setCategory(List<Product> ps){
        for (Product p:ps
             ) {
            setCategory(p);
        }
    }
    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List list = productMapper.selectByExample(example);
        setCategory(list);
        setFirstProductImage(list);
        return list;
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> list = productImageService.list(product.getId(),ProductImageService.type_single);
        if(!list.isEmpty()){
            ProductImage productImage = list.get(0);
            product.setFirstProductImage(productImage);
        }
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category:categories
             ) {
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> list = list(category.getId());
        category.setProducts(list);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category:categories
             ) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i = 0;i<products.size();i+=productNumberEachRow){
                int size = i + productNumberEachRow;
                size = size>products.size()?products.size():size;
                List<Product> productsOfEachRow = products.subList(i,size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        product.setSaleCount(saleCount);
        int count = reviewService.getCount(product.getId());
        product.setReviewCount(count);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product:products
             ) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        setCategory(products);
        setFirstProductImage(products);
        return products;
    }

    public void setFirstProductImage(List<Product> list){
        for (Product product:list
             ) {
            setFirstProductImage(product);
        }
    }
}
