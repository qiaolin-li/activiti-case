package com.ascendant.core.service;

import com.ascendant.common.util.IdGen;
import com.ascendant.core.entity.BaseEntity;
import com.ascendant.core.entity.Page;
import com.ascendant.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author qiaolin
 * @version 2018/10/24
 **/
public class BaseServiceImpl<E extends BaseEntity, M extends BaseMapper<E>> implements BaseService<E> {

    @Autowired
    protected M mapper;

    @Transactional
    public int save(E e){
        Date now = new Date();
        e.setUpdateTime(now);
        // TODO 这里得新增和修改人暂时为1 后面再修改
        e.setUpdateBy("1");
        if(StringUtils.isBlank(e.getId())) {
            e.setId(IdGen.uuid());
            e.setCreateBy("1");
            e.setCreateTime(now);
            return mapper.insert(e);
        }
        return mapper.update(e);
    }

    @Override
    public int delete(String id) {
        return mapper.delete(id);
    }

    @Override
    public int delete(E e) {
        return mapper.delete(e);
    }

    @Override
    public PageInfo<E> findPage(E e) {
        Page page = e.getPage();
        if(Objects.nonNull(page)){
            if(StringUtils.isNotBlank(page.getOrderBy())){
                PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
            }else{
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
        }

        return new PageInfo<E>(mapper.findList(e));
    }

    @Override
    public E findById(String id) {
        return mapper.findById(id);
    }

    @Override
    public E findById(E e) {
        return mapper.findById(e);
    }
}
