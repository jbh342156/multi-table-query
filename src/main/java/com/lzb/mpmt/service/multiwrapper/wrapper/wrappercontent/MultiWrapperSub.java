package com.lzb.mpmt.service.multiwrapper.wrapper.wrappercontent;

import com.lzb.mpmt.service.multiwrapper.sqlsegment.MultiWrapperAggregate;
import com.lzb.mpmt.service.multiwrapper.sqlsegment.MultiWrapperSelect;
import com.lzb.mpmt.service.multiwrapper.sqlsegment.MultiWrapperWhere;
import com.lzb.mpmt.service.multiwrapper.sqlsegment.aggregate.MultiAggregateInfo;
import com.lzb.mpmt.service.multiwrapper.sqlsegment.wheredata.WhereDataTree;
import com.lzb.mpmt.service.multiwrapper.util.MultiUtil;
import com.lzb.mpmt.service.multiwrapper.util.mybatisplus.MultiFunction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@SuppressWarnings("unused")
public class MultiWrapperSub<SUB> implements
        MultiWrapperWhere<SUB, MultiWrapperSub<SUB>>,
        MultiWrapperSelect<SUB, MultiWrapperSub<SUB>>,
        MultiWrapperAggregate<SUB, MultiWrapperMain<SUB>> {


    /**
     * 下划线表名
     */
    private String tableName;

    /**
     * where条件
     */
    private WhereDataTree whereTree = new WhereDataTree();

    /**
     * select属性列表
     */
    private List<String> selectFields;

    /**
     * 类为了生成List<SUB>
     */
    private Class<SUB> clazz;

    /**
     * 聚合函数信息 执行MultiExecutor.page()/MultiExecutor.aggregate()时,才会使用到
     */
    private List<MultiAggregateInfo> multiAggregateInfos = Collections.emptyList();

    public static <SUB> MultiWrapperSub<SUB> lambda(Class<SUB> clazz) {
        String tableName = MultiUtil.camelToUnderline(clazz.getSimpleName());
        MultiWrapperSub<SUB> wrapperSub = new MultiWrapperSub<>();
        wrapperSub.setTableName(tableName);
        wrapperSub.setClazz(clazz);
        return wrapperSub;
    }

    @SafeVarargs
    @Override
    public final <VAL> MultiWrapperSub<SUB> select(MultiFunction<SUB, VAL>... propFuncs) {
        return MultiWrapperSelect.super.select(propFuncs);
    }
}
