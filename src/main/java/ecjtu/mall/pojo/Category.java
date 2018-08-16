/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Category
 * Author:   jack
 * Date:     18/08/16 11:55
 * Description: 商品分类模型
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.pojo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类模型〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
public class Category {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
