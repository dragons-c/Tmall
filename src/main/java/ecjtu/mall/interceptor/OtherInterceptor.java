package ecjtu.mall.interceptor;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.OrderItem;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Category> list = categoryService.list();
        HttpSession session = request.getSession();
        session.setAttribute("cs",list);
        String contextPath = session.getServletContext().getContextPath();
        session.setAttribute("contextPath",contextPath);
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if(user != null){
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for (OrderItem orderItem:orderItems
                 ) {
                cartTotalItemNumber += orderItem.getNumber();
            }
            
        }
        session.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
    }
}
