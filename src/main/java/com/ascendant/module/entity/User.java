package com.ascendant.module.entity;

import com.ascendant.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author qiaolin
 * @date 2018-10-24 15:53:59
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 邮箱 */
    private String email;
}