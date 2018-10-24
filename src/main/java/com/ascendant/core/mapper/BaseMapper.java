package com.ascendant.core.mapper;

import java.util.List;

/**
 * @author qiaolin
 * @version 2018/10/23
 **/
public interface BaseMapper<E> {

    /**
     *  新增一条数据
     * @param e 待新增的数据
     * @return
     */
    int insert(E e);

    /**
     *  修改一条数据
     * @param e 待修改的数据
     * @return
     */
    int update(E e);

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
    List<E> findList(E e);


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
