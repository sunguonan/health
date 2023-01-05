package com.ydlclass.health.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
public class PageResult implements Serializable {
    private static final long serialVersionUID = 5465906506145600940L;
    private Long total;// 总记录数
    private List rows;// 当前页结果

    public PageResult() {
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
