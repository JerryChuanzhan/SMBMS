package com.zcz.util;

/**
 * @description: 分页实现工具类
 * @fileName: PageSupport
 * @author: ZCZ
 * @date 2023年03月01日 22:22
 */
public class PageSupport {
    /**
     * 当前页码--来自于用户输入
     */
    private int currentPageNo = 1;
    /**
     * 总数量（数据库表查询）
     */
    private int totalCount = 0;
    /**
     * 页面容量
     */
    private int pageSize = 0;
    /**
     * 总页数--（totalCount/pageSize)+1
     */
    private int totalPageCount;

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    //OOP三大特性：封装（属性私有，get、set  合理隐藏、合理暴露，在set中限定住一些不安全的情况）、继承、多态
    public void setCurrentPageNo(int currentPageNo) {
        //当前页码输入大于0，返回得到页码数值，其他条件默认初始值为1
        if (currentPageNo > 0) {
            this.currentPageNo = currentPageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {//数据库查询数量大于0，set总数量数值
            this.totalCount = totalCount;
            //设置总页数

        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0) {//页面容量大于0
            this.pageSize = pageSize;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void setTotalPageCountByRs() {
        if (this.totalCount % this.pageSize == 0) {
            this.totalPageCount = (this.totalCount / this.pageSize);
        } else if (this.totalCount % this.pageSize > 0) {
            this.totalPageCount = (totalCount / pageSize + 1);
        } else {
            this.totalPageCount = 0;
        }
    }
}
