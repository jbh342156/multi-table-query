package com.lzb.mpmt.service.multiwrapper.sqlsegment.aggregate;

import com.lzb.mpmt.service.multiwrapper.constant.MultiConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 聚合信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MultiAggregateInfo {
    /**
     * 聚合操作类型
     */
    MultiConstant.MultiAggregateTypeEnum aggregateType;
    /**
     * 聚合字段对应关系名称
     */
    String relationCode;
    /**
     * 聚合字段对应字段名称
     */
    String fieldName;
    /**
     * 聚合字段别名
     */
    String alias;
}
