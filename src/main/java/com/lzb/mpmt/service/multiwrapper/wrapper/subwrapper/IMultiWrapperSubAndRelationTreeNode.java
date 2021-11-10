package com.lzb.mpmt.service.multiwrapper.wrapper.subwrapper;

import com.lzb.mpmt.service.multiwrapper.sqlsegment.MultiWrapperSelect;
import com.lzb.mpmt.service.multiwrapper.util.TreeNode;

/**
 * 副表,和主副表对应关系信息
 *
 * @author Administrator
 */
public interface IMultiWrapperSubAndRelationTreeNode extends TreeNode.IEqualsKey<IMultiWrapperSubAndRelationTreeNode> {

    /**
     * @return 当前节点的当前表
     */
    String getTableNameThis();

    /**
     * @return 当前节点的当其他表
     */
    String getTableNameOther();


    // ----  以下是与树节点无关的额外信息 ----- start
    /**
     * @return 当前副表(或主表)对应类
     */
    Class<?> getTableClassThis();

    /**
     * @return 当前副表对应relationCode(或主表表名)
     */
    String getRelationCode();

    /**
     * @return select信息
     */
    MultiWrapperSelect<?, ?> getMultiWrapperSelectInfo();

    // ----  以下是与树节点无关的额外信息 ----- end

    /**
     * 父子节点存在父子关系的判断
     * @param   child 子节点
     * @return  当前节点(作为父节点)与子节点,是否存在父子关系
     */
    @Override
    default boolean parentKeyEqualsChildKey(IMultiWrapperSubAndRelationTreeNode child) {
        String tableNameOtherParent = getTableNameThis();
        return tableNameOtherParent.equals(child.getTableNameOther());
    }
}