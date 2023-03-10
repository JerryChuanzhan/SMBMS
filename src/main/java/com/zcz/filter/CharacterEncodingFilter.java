package com.zcz.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description: 字节编码过滤器
 * @ClassName: CharacterEncodingFilter
 * @Author: ZCZ
 * @Date 2023年02月26日 16:42
 */
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * @return : void
     * @Description :  过滤处理请求编码
     * @Date : 18:39 2023/3/5
     * @Param : [request, response, chain]
     **/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置请求、相应编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 链 放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
