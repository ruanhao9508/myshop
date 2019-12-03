package com.qf.exception;

import com.qf.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auth RuanHao
 * @Time 2019/12/2 21:29
 **/
/*包名要和myshop_back(引用这个项目的项目)里面启动类注解的扫包路径一样,不然扫不到这个类*/
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request,Exception e){
        System.out.println("项目出错了"+e.getMessage());

        String header=request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(header)){
            //满足判断说明是ajax请求,出现异常应该返回json
            return new ResultData<>().setCode(ResultData.ResultCodeList.ERROR).setMsg("服务器繁忙,请稍后再试.");
        }else{
            //说明是一个同步请求(form表单,href),返回异常页面
            //表示一定会去找视图
            return new ModelAndView( "myerror");
        }

    }
}
