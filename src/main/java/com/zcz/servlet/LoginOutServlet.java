package com.zcz.servlet;

import com.zcz.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 用户注销功能
 * @FileName: LoginOutServlet
 * @Author: ZCZ
 * @Date 2023年02月26日 19:56
 */
public class LoginOutServlet extends HttpServlet {

    /**
     * @return void
     * @Description :注销用户Session
     * @Date 18:21 2023/3/5
     * @Param [req, resp]
     **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //移出用户Session属性（Constants.USER_SESSION）来实现注销
        req.getSession().removeAttribute(Constants.USER_SESSION);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");//返回登录页面
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
