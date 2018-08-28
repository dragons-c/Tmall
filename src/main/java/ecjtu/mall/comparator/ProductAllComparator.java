package ecjtu.mall.comparator;

import ecjtu.mall.pojo.Product;

import java.util.Comparator;

public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return product2.getSaleCount()*product2.getReviewCount()-product1.getReviewCount()*product1.getSaleCount();
    }
}
