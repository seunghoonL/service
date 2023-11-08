package org.delivery.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {



    @GetMapping({"", "/main"})
    public String main(){
        return "main";

    }

    @GetMapping("/order")
    public String order(){
        return "/order/order";
    }
}
