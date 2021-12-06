/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.entity;

import lombok.Data;

import java.util.Date;

/**
 * BasicTestEntity
 *
 * @author Little Smile Boy
 * @since 2021-12-07
 */
@Data
public abstract class TestEntity {

    /** 主键id */
    private Long id;
    /** 测试类型 */
    private String type;
    /** 测试名称 */
    private String name;
    /** json类型详细测试配置 */
    private String testInfo;
    /** 创建者 */
    private Long createUserId;
    /** 创建日期 */
    private Date createdDate;
    /** 修改者 */
    private Long modifyUserId;
    /** 修改日期 */
    private Date modifiedDate;

    public abstract TestEntity getNextStep();
}
