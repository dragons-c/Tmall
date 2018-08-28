package ecjtu.mall.interceptor;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.OrderItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] noNeedAuthPage = new String[]{"home","checkLogin","register","loginAjax","login","product","category","search"};
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/fore")){
            String method = StringUtils.substringAfterLast(uri,"/fore");
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User) session.getAttribute("user");
                if(user == null){
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }
}
