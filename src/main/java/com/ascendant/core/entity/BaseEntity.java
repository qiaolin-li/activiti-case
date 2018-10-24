package com.ascendant.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author qiaolin
 * @version 2018/10/23
 **/

@Data
public class BaseEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1133831723343809504L;

	/**
     *  主键
     */
    private String id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     *  创建时间
     */
    private Date createTime;


    /**
     *  修改者
     */
    private String updateBy;

    /**
     *  修改时间
     */
    private Date updateTime;

    /**
     *  分页对象
     */
    @JsonIgnore
    private Page page;

}
