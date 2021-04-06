package com.mincheung.Responses;

import java.io.Serializable;

public class Page  implements Serializable {
    //总页数
    private Long totalPage;
    //开始页,也就是开始查询条数
    private Long begining;
    //前一页
    private Long prevPage;
    //后一页
    private Long nextPage;
    //当前页
    private  Long currentPage;
    //数据量
    private Long PageSize;


    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getBegining() {
        return begining;
    }

    public void setBegining(Long begining) {
        this.begining = begining;
    }

    public Long getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(Long prevPage) {
        this.prevPage = prevPage;
    }

    public Long getNextPage() {
        return nextPage;
    }

    public void setNextPage(Long nextPage) {
        this.nextPage = nextPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return PageSize;
    }

    public void setPageSize(Long pageSize) {
        PageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalPage=" + totalPage +
                ", begining=" + begining +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", currentPage=" + currentPage +
                ", PageSize=" + PageSize +
                '}';
    }
}
