package com.newcoder.community.entity;

/**
 * ClassName: Page
 * Package: com.newcoder.community.entity
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/4 17:58
 */

import org.springframework.stereotype.Component;

/**
 * 封装分页相关信息
 */
public class Page {

    // 当前页码
    private int current = 1;
    // 当前上限
    private int limit = 10;
    // 数据总数(用于计算总页数)
    private int rows;
    // 查询路径(用于复用分页链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1)
            this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100)
            this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >=0)
            this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页起始行
     * @return
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal() {
        if (rows == 0) return 1;
        return (rows + limit - 1) / limit; // 向上取整写法
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal(); // 用总页数来截断
        return to > total ? total : to;
    }
}
