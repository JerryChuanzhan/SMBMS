package com.zcz.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.zcz.entity.Bill;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: BillServlet
 * @Description: 订单Servlet
 * @Author: ZCZ
 * @Date: 2023/3/9 9:22
 */
public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("query")) {
            this.getBills(req, resp);
        }
        if (method != null && method.equals("getproviderlist")) {
            this.getproviderlist(req, resp);
        }
        if (method != null && method.equals("add")) {
            this.addBill(req, resp);
        }
        if (method != null && method.equals("delbill")) {
            this.delbill(req, resp);
        }
        if (method != null && method.equals("modify")) {
            this.jumpPage(req, resp, "billmodify.jsp");
        }
        if (method != null && method.equals("modifysave")) {
            this.modifysave(req, resp);
        }
        if (method != null && method.equals("view")) {
            this.jumpPage(req, resp, "billview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * @Author: ZCZ
     * @Description: 获取订单列表信息
     * @Date: 2023/3/9
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void getBills(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProviderServiceImpl providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList("", "");
        req.setAttribute("providerList", providerList);
        BillServiceImpl billService = new BillServiceImpl();
        Bill bill = new Bill();
        String queryProviderId = req.getParameter("queryProviderId");
        String queryIsPayment = req.getParameter("queryIsPayment");
        String queryProductName = req.getParameter("queryProductName");
        if (StringUtils.isNullOrEmpty(queryProviderId)) {
            bill.setProviderId(0);
        } else {
            bill.setProviderId(Integer.valueOf(queryProviderId));
        }
        if (StringUtils.isNullOrEmpty(queryIsPayment)) {
            bill.setIsPayment(0);
        } else {
            bill.setIsPayment(Integer.valueOf(queryIsPayment));
        }
        if (StringUtils.isNullOrEmpty(queryProductName)) {
            bill.setProductName("");
        } else {
            bill.setProductName(queryProductName);
        }
        List<Bill> billList = billService.getBillList(bill);
        req.setAttribute("queryProductName", queryProductName);
        req.setAttribute("queryIsPayment", queryIsPayment);
        req.setAttribute("billList", billList);
        req.getRequestDispatcher("billlist.jsp").forward(req, resp);
    }

    /**
     * @Author: ZCZ
     * @Description: 增加订单页面，供应商下拉框数据来源
     * @Date: 2023/3/9
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void getproviderlist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList("", "");
        // 把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(providerList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * @Author: ZCZ
     * @Description: 添加新订单
     * @Date: 2023/3/9
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void addBill(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductCount(BigDecimal.valueOf(Long.parseLong(productCount)));
        bill.setTotalPrice(BigDecimal.valueOf(Long.parseLong(totalPrice)));
        bill.setProviderId(Integer.valueOf(providerId));
        bill.setIsPayment(Integer.valueOf(isPayment));


        bill.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());

        BillServiceImpl billService = new BillServiceImpl();
        boolean addFlag = billService.addBill(bill);

        // 添加成功 重定向至 订单列表页面;添加失败，继续转发至添加页面
        if (addFlag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            req.getRequestDispatcher("billadd.jsp").forward(req, resp);
        }
    }

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void delbill(HttpServletRequest req, HttpServletResponse resp) {
        String billid = req.getParameter("billid");
        int delFalg = -1;
        HashMap<Object, Object> relultMap = new HashMap<>();
        BillServiceImpl billService = new BillServiceImpl();
        Bill bill = new Bill();
        bill.setId(Integer.valueOf(billid));
        delFalg = billService.delBill(bill);
        if (delFalg == 1) {
            relultMap.put("delResult", "true");
        } else if (delFalg == 0) {
            relultMap.put("delResult", "notexist");
        } else {
            relultMap.put("delResult", "false");
        }
        // 返回形式为json形式
        resp.setContentType("application/json");
        try {// 得到返回流
            PrintWriter writer = resp.getWriter();
            // JSONArray  阿里巴巴的工具类,转换格式
            // 把map对象解析成字符串形式
            writer.write(JSONArray.toJSONString(relultMap));
            // IO流,刷新、关闭
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [req, resp]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
     **/
    public void modifysave(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id = req.getParameter("id");
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setId(Integer.valueOf(id));
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        // bill.setProductCount(BigDecimal.valueOf(Long.parseLong(productCount)));
        bill.setProductCount(new BigDecimal(productCount));
        // bill.setTotalPrice(BigDecimal.valueOf(Long.parseLong(totalPrice)));
        bill.setTotalPrice(new BigDecimal(totalPrice));
        bill.setProviderId(Integer.valueOf(providerId));
        bill.setIsPayment(Integer.valueOf(isPayment));


        bill.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());

        BillServiceImpl billService = new BillServiceImpl();
        boolean updateFlag = billService.updateBill(bill);

        // 若修改成功，重定向到用户列表页面  ，否则转发至用户修改页面
        if (updateFlag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            try {
                req.getRequestDispatcher("billmodify.jsp.jsp").forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @Author: ZCZ
     * @Description: 跳转修改订单页面
     * @Date: 2023/3/9
     * @Param: [req, resp, url]
     * @return: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String]
     **/
    public void jumpPage(HttpServletRequest req, HttpServletResponse resp, String url) {
        String billid = req.getParameter("billid");
        BillServiceImpl billService = new BillServiceImpl();
        Bill bill = billService.getBillById(billid);

        req.setAttribute("bill", bill);
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
