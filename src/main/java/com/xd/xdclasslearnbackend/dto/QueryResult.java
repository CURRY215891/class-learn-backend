package com.xd.xdclasslearnbackend.dto;

import lombok.Data;

/**
 * @author 康志阳
 * @date 2026/2/18 18:56
 */
@Data
public class QueryResult {
    private int pageNum = 1;
    private int pageSize = 9;
    private String orderBy;
    private String oderDirection;
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
