package com.lagou.domain;

/**
 * web接受到前台分页相关参数封装到这个实体类
 */
public class PromotionAdVO {
    //当前页
    private Integer currentPage;
    //每页显示的条数
    private Integer pageSize;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
