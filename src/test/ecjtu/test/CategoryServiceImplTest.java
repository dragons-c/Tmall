/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryServiceImplTest
 * Author:   jack
 * Date:     18/08/16 17:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.test;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService service;

    @Test
    public void testFindAll(){
        List<Category> all = service.findAll();
        for (Category category: all) {
            System.out.println(category);
        }
    }
}
