package com.zcz.servlet;

import com.alibaba.fastjson.JSONArray;
import com.zcz.entity.Provider;
import com.zcz.entity.User;
import com.zcz.service.Bill.BillServiceImpl;
import com.zcz.service.provider.ProviderServiceImpl;
import com.zcz.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: ProviderServlet
 * @Description: 供应商Servlet
 * @Author: ZCZ
 * @Date: 2023/3/8 14:23
 */
public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从前端 获取method 参数
        String method = req.getParameter("method");
        if (method != null && method.equals("query")) {
            this.query(req, resp);
        }
        if (method != null && method.equals("add")) {
            this.addProvider(req, resp);
        }
        if (method != null && method.equals("delprovider")) {
            this.delprovider(req, resp);
        }
        if (method != null && method.equals("modify")) {
            this.jumpPage(req, resp, "providermodify.jsp");
        }
        if (method != null && method.equals("modifysave")) {
            this.modifySave(req, resp);
        }
        if (method != null && method.equals("view")) {
            this.jumpPage(req, resp, "providerview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * @Author: ZCZ
     * @Description: 获取供应商列表
     * @Date: 2023/3/8
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");
        List<Provider> providerList = null;
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList(queryProCode, queryProName);
        req.setAttribute("providerList", providerList);

        // 返回前端
        try {
            req.getRequestDispatcher("providerlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Author: ZCZ
     * @Description: 添加供应商
     * @Date: 2023/3/8
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void addProvider(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProDesc(proDesc);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);

        int CreatedBy = 0;
        CreatedBy = ((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        provider.setCreatedBy(CreatedBy);
        provider.setCreationDate(new Date());

        ProviderServiceImpl providerService = new ProviderServiceImpl();
        boolean flag = providerService.addProvider(provider);

        // 添加成功 重定向至 供应商列表页面;添加失败，继续转发至供应商添加页面
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            req.getRequestDispatcher("provideradd.jsp").forward(req, resp);
        }
    }

    public void delprovider(HttpServletRequest req, HttpServletResponse resp) {
        String proid = req.getParameter("proid");
        HashMap<Object, Object> delFlagHashMap = new HashMap<>();
        boolean delFlag = false;

        BillServiceImpl billService = new BillServiceImpl();
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        int billConut = billService.getConut(proid);
        if (billConut < 1) {
            delFlag = providerService.delProvider(proid);
        }

        // 若供应商 删除成功 且 该供应商下订单数小于1 ，返回true，否则供应商不存在
        if (delFlag && billConut < 1) {
            delFlagHashMap.put("delResult", "true");
        } else if (delFlag && billConut > 1) {// 删除行>0, 且订单行数大于1 ，返回订单数量
            delFlagHashMap.put("delResult", billConut);
        } else {
            delFlagHashMap.put("delResult", "false");
        }

        // 返回形式为json形式
        resp.setContentType("application/json");
        try {// 得到返回流
            PrintWriter writer = resp.getWriter();
            // JSONArray  阿里巴巴的工具类,转换格式
            // 把map对象解析成字符串形式
            writer.write(JSONArray.toJSONString(delFlagHashMap));
            // IO流,刷新、关闭
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: ZCZ
     * @Description: 跳转供应商对应页面
     * @Date: 2023/3/8
     * @Param: [req, resp, url]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String]
     **/
    public void jumpPage(HttpServletRequest req, HttpServletResponse resp, String url) {
        String proid = req.getParameter("proid");
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        Provider provider = providerService.getProviderById(proid);
        req.setAttribute("provider", provider);
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author: ZCZ
     * @Description: 修改供应商信息
     * @Date: 2023/3/8
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void modifySave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean updateFlag = false;

        String id = req.getParameter("id");
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setId(Integer.valueOf(id));
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);

        int modify = ((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        provider.setModifyBy(modify);
        provider.setModifyDate(new Date());


        ProviderServiceImpl providerService = new ProviderServiceImpl();
        updateFlag = providerService.updateProvider(provider);
        // 若修改成功，重定向到用户列表页面  ，否则转发至用户修改页面
        if (updateFlag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            try {
                req.getRequestDispatcher("providermodify.jsp").forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
