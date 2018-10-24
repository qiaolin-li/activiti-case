package com.ascendant.core.service;

import com.ascendant.core.entity.BaseEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * service 基础接口
 * @author qiaolin
 * @version 2018/10/24
 **/
public interface BaseService<E extends BaseEntity> {

    /**
     *  新增或修改一条数据
     * @param e
     * @return
     */
    int save(E e);

    /**
     *  根据id 删除一条数据
     * @param id
     * @return
     */
    int delete(String id);


    /**
     *  默认根据对象内的id删除一条数据，可以扩展
     * @param e
     * @return
     */
    int delete(E e);


    /**
     *  根据对象查询数据
     * @param e 查询条件
     * @return
     */
    PageInfo<E> findPage(E e);


    /**
     *  根据Id查询数据
     * @param id
     * @return
     */
    E findById(String id);

    /**
     *  根据对象中Id查询数据
     * @param e
     * @return
     */
    E findById(E e);
}
