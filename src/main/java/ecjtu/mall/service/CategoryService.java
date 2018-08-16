/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryService
 * Author:   jack
 * Date:     18/08/16 15:34
 * Description: 商品分类业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Category;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类业务层〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
public interface CategoryService {

    List<Category> findAll();
}
