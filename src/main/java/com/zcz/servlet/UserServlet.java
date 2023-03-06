package com.zcz.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.zcz.entity.Role;
import com.zcz.entity.User;
import com.zcz.service.role.serviceImpl.RoleServiceImpl;
import com.zcz.service.user.UserService;
import com.zcz.service.user.serviceImpl.UserServiceImpl;
import com.zcz.util.Constants;
import com.zcz.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 用户信息操作Servlet
 * @FileName: UserServlet
 * @Author: ZCZ
 * @Date 2023年02月26日 22:36
 */
public class UserServlet extends HttpServlet {
    /**
     * @return void
     * @Description Servlet复用
     * @Date 18:22 2023/3/5
     * @Param [req, resp]
     **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("savepwd")) {
            // 封装servlet的密码修改方法，实现servlet复用
            this.updatePwd(req, resp);
        } else if (method != null && method.equals("pwdmodify")) {
            this.testOldPwd(req, resp);
        } else if (method != null && method.equals("query")) {
            this.query(req, resp);
        } else if (method != null && method.equals("add")) {
            this.addUser(req, resp);
        } else if (method != null && method.equals("getrolelist")) {
            this.getroleList(req, resp);
        } else if (method != null && method.equals("ucexist")) {
            this.isExitUser(req, resp);
        } else if (method != null && method.equals("deluser")) {
            this.deluser(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    /**
     * @return void
     * @Description  封装servlet的修改密码方法
     * @Date 18:23 2023/3/5
     * @Param [req, resp]
     **/
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        // 从Session中获取用户ID
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        boolean flag = false;
        String newpassword = req.getParameter("newpassword");
        System.out.println(newpassword);
        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserService userService = new UserServiceImpl();
            try {
                flag = userService.UpdatePwd(((User) o).getId(), newpassword);
                if (flag) {
                    req.setAttribute("message", "密码修改成功，请退出，使用新密码重新登录！");
                    // 修改密码后，原用户已经注销，需要移除Session
                    req.getSession().removeAttribute(Constants.USER_SESSION);
                } else {
                    req.setAttribute("message", "密码修改失败！");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            req.setAttribute("message", "新密码设置格式不正确！");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Description  验证旧密码，Session中有用户的密码   根据Js中ajax逻辑进行判断
     * @Date 18:23 2023/3/5
     * @Param [req, resp]
     **/
    public void testOldPwd(HttpServletRequest req, HttpServletResponse resp) {
        // 从Session中获取用户ID
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        boolean flag = false;
        // 拿到用户输入的旧密码
        String oldpassword = req.getParameter("oldpassword");

        // 万能的Map : Map
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (o == null) {// 对象为空，则session失效了，session过期
            // 用maop和密码JS提示交互
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {// 输入的旧密码为空
            // 用maop和密码JS提示交互
            resultMap.put("result", "error");
        } else {// 用户数据库查询的密码和输入的旧密码相等
            if (((User) o).getUserPassword().equals(oldpassword)) {
                // 用maop和密码JS提示交互
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }
        // 返回形式为json形式
        resp.setContentType("application/json");
        try {// 得到返回流
            PrintWriter writer = resp.getWriter();
            // JSONArray  阿里巴巴的工具类,转换格式
            // 把map对象解析成字符串形式
            writer.write(JSONArray.toJSONString(resultMap));
            // IO流,刷新、关闭
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Description 用户管理实现   重点难点
     * @Date 18:23 2023/3/5
     * @Param [req, resp]
     **/
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        // 从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");// 临时
        String pageIndex = req.getParameter("pageIndex");// pageIndex ：当前页

        // 角色默认为0 请选择
        int queryUserRole = 0;
        if (queryUserName == null) {
            queryUserName = "";
        }
        // 角色下拉列表角色Id  userRole     默认为“0”（请选择），上述设置，若前端传递参数不为空，则设为前端所传值
        if (temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);// 如果temp不为空，给temp查询赋值  0，1，2
        }
        // 获取角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);

        // 查询用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;
        // 设置分页页面数据   第一次走这个请求，肯定是第一页，页面大小是固定的
        int pageSize = 5;  // 可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(String.valueOf(pageIndex));
        }
        // 获取用户总数(分页 ： 上一页  下一页的情况)
        try {
            int total = userService.getUserCount(queryUserRole, queryUserName);
            PageSupport pageSupport = new PageSupport();
            pageSupport.setCurrentPageNo(currentPageNo);
            pageSupport.setPageSize(pageSize);
            pageSupport.setTotalCount(total);
            int totalPageCount = ((int) (total / pageSize)) + 1;

            // 控制首页和尾页
            // 如果页面要小于1，当前页就显示第一页
            if (currentPageNo < 1) {
                currentPageNo = 1;
            } else if (currentPageNo > (total / pageSize + 1)) {// 当前页大于最后一页
                currentPageNo = (total / pageSize + 1);
            }

            // 设置前端页面数据
            req.setAttribute("totalPageCount", total / pageSize + 1);
            req.setAttribute("currentPageNo", currentPageNo);
            req.setAttribute("totalPageCount", totalPageCount);
            req.setAttribute("queryUserName", queryUserName);
            req.setAttribute("queryUserRole", queryUserRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userList = userService.getUserList(queryUserRole, queryUserName, pageSize, currentPageNo);
        req.setAttribute("userList", userList);


        // 返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return void
     * @Description 用户管理实现   重点难点
     * @Date 18:24 2023/3/5
     * @Param [req, resp]
     **/
    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从前端获取数据
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String password = req.getParameter("ruserPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        int createdBy = 0;
        // 获取当前时间
        Date date = new Date();
        boolean flag = false;
        UserServiceImpl userService = new UserServiceImpl();
        // 获取当前用户的uid
        User user = userService.login(userCode, password);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date birth = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            flag = userService.addUser(Integer.valueOf(userCode), userName, password, Integer.valueOf(gender), simpleDateFormat.format(birth), phone, address, Integer.parseInt(userRole), createdBy, simpleDateFormat.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }
    }

    /**
     * @return void
     * @Description 获取角色列表
     * @Date 18:24 2023/3/5
     * @Param [req, resp]
     **/
    public void getroleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        // 把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * @Description: 删除用户实现
     * @Date: 18:26 2023/3/5
     * @Param: [req, resp]
     * @return: void
     **/
    public void isExitUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("userCode");
        UserServiceImpl userService = new UserServiceImpl();
        HashMap<Object, Object> userMap = new HashMap<>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            // userCode == null || userCode.equals("")
            userMap.put("userCode", "exist");
        } else {
            User userByUserCode = userService.getUserByUserCode(userCode);
            // 三元表达式  userByUserCode不为空，说明存在，则userCode   put "exist"；反之，put "noexist"
            userMap.put("userCode", (userByUserCode != null) ? "exist" : "noexist");

            // 把resultMap转为json字符串以json的形式输出
            // 配置上下文的输出类型
            resp.setContentType("application/json");
            // 从response对象中获取往外输出的writer对象
            PrintWriter outPrintWriter = resp.getWriter();
            // 把resultMap转为json字符串 输出
            outPrintWriter.write(JSONArray.toJSONString(userMap));
            outPrintWriter.flush();// 刷新
            outPrintWriter.close();// 关闭流

        }
    }

    public void deluser(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        int userRole = 0;
        HashMap<String, String> delFlag = new HashMap<>();
        // 从前端获取用户ID
        String userId = req.getParameter("uid");
        String userName = req.getParameter("username");
        UserServiceImpl userService = new UserServiceImpl();

        // 若根据用户ID查询患者数量小于1，则用户不存在
        try {
            int userCount = userService.getUserCount(userRole, userName);
            if (userCount < 1) {
                delFlag.put("delResult", "notexist");
            } else {// 若查询患者数量大于1 ， 判断删除
                // 根据用户
                flag = userService.deluser(Integer.parseInt(userId));
                // 删除用户是否成功
                if (flag) {
                    delFlag.put("delResult", "true");
                } else {
                    delFlag.put("delResult", "false");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 返回形式为json形式
        resp.setContentType("application/json");
        try {// 得到返回流
            PrintWriter writer = resp.getWriter();
            // JSONArray  阿里巴巴的工具类,转换格式
            // 把map对象解析成字符串形式
            writer.write(JSONArray.toJSONString(delFlag));
            // IO流,刷新、关闭
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
