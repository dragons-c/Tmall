package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.UserService;
import ecjtu.mall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> list = userService.list();
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        model.addAttribute("us",list);
        model.addAttribute("page",page);
        return "admin/listUser";
    }
}
