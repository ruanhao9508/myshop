package com.qf.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 1:31
 **/
@Controller
public class SystemExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public String systemException(HttpServletResponse response){
        //获取错误的状态码
        int status=response.getStatus();

        switch (status){
            case 401:
                return "401";
            case 402:
                return "402";
            //拒绝访问
            case 403:
                return "403";
            case 404:
                return "404";
        }
        return "myerror";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
