package com.zcz.filter;

import com.zcz.entity.User;
import com.zcz.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 用户session过滤器
 * @FileName: SysFilter
 * @aAthor: ZCZ
 * @Date 2023年02月26日 20:11
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @Description :  过滤用户Session
     * @Date : 18:28 2023/3/5
     * @Param : [req, resp, chain]
     * @return : void
     **/
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 过滤器，从Session中获取用户
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (user != null) {// 可以从Session中获取用户，用户未注销，正常进行
            chain.doFilter(req, resp);
        } else {// 用户已经被移除 或者注销了  或者未登录
            response.sendRedirect("/error.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
