package com.xxzy.EXLG.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 查询参数
 *
 * @author gjq0117@163.com
 */
public class Query<T> {

    /**
     * 当前页码
     */
    public final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public final String ORDER = "order";
    /**
     * 升序
     */
    public final String ASC = "asc";

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if(params.get(PAGE) != null){
            curPage = Long.parseLong(params.get(PAGE).toString());
        }
        if(params.get(LIMIT) != null){
            limit = Long.parseLong((String)params.get(LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String)params.get(ORDER_FIELD));
        String order = (String)params.get(ORDER);


        //前端字段排序
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(ASC.equalsIgnoreCase(order)) {
                return  page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if(StringUtils.isBlank(defaultOrderField)){
            return page;
        }

        //默认排序
        if(isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        }else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
