/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryMapper
 * Author:   jack
 * Date:     18/08/16 15:27
 * Description: 商品分类dao
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.mapper;

import ecjtu.mall.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类dao〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
@Repository
public interface CategoryMapper {

    List<Category> findAll();
}
