package ecjtu.mall.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("")
public class PageController {
    @RequestMapping("registerPage")
    public String registerPage(){
        return "fore/register";
    }
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage(){
        return "fore/registerSuccess";
    }
    @RequestMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }
    @RequestMapping("forealipay")
    public String aliPay(){
        return "fore/alipay";
    }
}
