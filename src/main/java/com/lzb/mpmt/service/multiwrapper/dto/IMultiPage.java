package com.lzb.mpmt.service.multiwrapper.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 分页信息对象
 *
 * @author Administrator
 */
public interface IMultiPage<T> {
    /* ------------- 入参 ------------- */
    /**
     * 每页大小
     * @return 每页大小
     */
    Long getPageSize();
    /**
     * 当前第几页
     * @return 当前第几页
     */
    Long getCurrPage();
    /**
     * 排序信息
     * 例如 username asc,id desc
     * @return 当前第几页
     */
    String getOrders();


    /* ------------- 返回 ------------- */
    /**
     * 分页列表内容 <必有>
     * @return 分页列表内容
     */
    List<T> getRecords();
    /**
     * 总条数 <必有>
     * @return 总条数
     */
    Long getTotal();
    /**
     * 统计信息
     * 例如 {'user__userWallet.enableBalance':"1000"}
     * @return 统计信息
     */
    Map<String, Map<String, Object>> getAggregateMap();
    /**
     * 附加信息
     * @return 附加信息
     */
    Object getAttach();

    void setPageSize(Long pageSize);
    void setCurrPage(Long currPage) ;
    void setOrders(String orders);
    void setRecords(List<T> records);
    void setTotal(Long total) ;
    void setAggregateMap(Map<String, Map<String, Object>> aggregateMap);
    void setAttach(Object attach);
}
