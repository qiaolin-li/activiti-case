package com.ascendant.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * 分页对象
 * @author qiaolin
 * @version 2018/10/23
 **/

@Getter
@Setter
public class Page {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;

}
