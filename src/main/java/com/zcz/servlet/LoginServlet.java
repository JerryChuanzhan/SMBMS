package com.zcz.servlet;

import com.zcz.entity.User;
import com.zcz.service.user.serviceImpl.UserServiceImpl;
import com.zcz.service.user.UserService;
import com.zcz.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @description:用户登录
 * @fileName: UserServlet
 * @author: ZCZ
 * @date 2023年02月26日 19:20
 */
public class LoginServlet extends HttpServlet {
    //Servlet（控制层） : 调用业务层代码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证进入Servlet
        System.out.println("LoginServlet--start...");
        //获取用户名、密码
        //和前端对应参数
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库中的密码进行对比，调用业务层
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.login(userCode, userPassword);
            if (user != null) {//查有此人，可以登录
                //将用户的信息放入到session中
                req.getSession().setAttribute(Constants.USER_SESSION, user);
                //跳转到主页
                resp.sendRedirect("jsp/frame.jsp");
            } else {
                //查无此人，无法登录
                //转发回登录页面，顺便提示登录错误
                req.setAttribute("error", "您输入的用户名或密码错误");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
