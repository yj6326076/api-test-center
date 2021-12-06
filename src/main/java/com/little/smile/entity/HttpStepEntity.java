/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.entity;

/**
 * HttpStepEntity
 *
 * @author Little Smile Boy
 * @since 2021-12-07
 */
public class HttpStepEntity extends TestEntity {

    private String method;

    private Boolean needCookie;

    @Override
    public TestEntity getNextStep() {
        return null;
    }
}
