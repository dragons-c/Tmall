package ecjtu.mall.service;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List list(int cid);
    void setFirstProductImage(Product product);
    void fill(List<Category> categories);
    void fill(Category category);
    void fillByRow(List<Category> categories);
    void setSaleAndReviewNumber(Product product); //产品设置销量和评价数量
    void setSaleAndReviewNumber(List<Product> products);
    List<Product> search(String keyword);
}
