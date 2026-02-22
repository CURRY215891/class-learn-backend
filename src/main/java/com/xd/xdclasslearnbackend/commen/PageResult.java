package com.xd.xdclasslearnbackend.commen;

import com.xd.xdclasslearnbackend.entity.Course;
import lombok.Data;

import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 16:43
 */
@Data
public class PageResult <T>{
    /**
     * 当前页数据
     */
    private List<T> data;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有上一页
     */
    private boolean hasPreviousPage;

    public PageResult(List<T> data, long total, int pageNum, int pageSize, int totalPages) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (int)Math.ceil((double)total/pageSize);
        this.hasNextPage = pageNum < totalPages;
        this.hasPreviousPage = pageNum > 1;
    }

    public PageResult(List<T> data, long total, int pageNum, int pageSize) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = pageSize > 0 ? (int) Math.ceil((double) total / pageSize) : 0;
        this.hasNextPage = this.pageNum < this.totalPages;
        this.hasPreviousPage = this.pageNum > 1;
    }
}
