package cn.yihao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HMR
 * @create 2021/8/20 16:54
 */
@Controller
public class HelloController {
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("add...");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(){
        System.out.println("delete..");
        return "success";
    }
}
