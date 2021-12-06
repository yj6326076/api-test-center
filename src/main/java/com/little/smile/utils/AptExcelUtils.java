/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.utils;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * AptExcelUtils
 *
 * @author Little Smile Boy
 * @since 2021-12-03
 */
public class AptExcelUtils {
    /**
     * 从文件中读取数据
     * @param file 文件
     * @param tClass 数据类型
     * @param <T> 数据实际类型
     * @return 读取的数据
     */
    public static <T> List<T> readFile(File file, Class<T> tClass) {
        return EasyExcel.read(file).head(tClass).sheet().doReadSync();
    }

    /**
     * 从流中读取数据
     * @param inputStream 输入流
     * @param tClass 数据类型class
     * @param <T> 数据类型
     * @return 读取的数据
     */
    public static <T> List<T> readFile(InputStream inputStream, Class<T> tClass) {
        return EasyExcel.read(inputStream).head(tClass).sheet().doReadSync();
    }
}
