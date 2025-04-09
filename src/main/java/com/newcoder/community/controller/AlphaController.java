package com.newcoder.community.controller;

import com.newcoder.community.service.AlphaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * ClassName: AlphaController
 * Package: com.newcoder.community.controller
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/1 18:16
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("hello")
    @ResponseBody
    public String sayHello(){ return "hello"; }

    @RequestMapping("data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

//    @RequestMapping("/http")
//    public void http(HttpServletRequest request, HttpServletResponse response){
//
//    }

    //响应HTML数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    //响应JSON数据: Java对象 -> JSON字符串 -> JS对象
    //通常在异步请求中

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        HashMap<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 30);
        emp.put("salary", 8000.0);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 30);
        emp.put("salary", 8000.0);
        list.add(emp);

        Map<String, Object> emp1 = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 50);
        emp.put("salary", 9000.0);
        list.add(emp);
        return list;
    }

}
