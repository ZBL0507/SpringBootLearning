package com.zbl.springboot.conf.datamask;

@FunctionalInterface
public interface DataMaskingOperation {

    String MASK_CHAR = "*";

    /**
     * 给定一个字符串，进行打码
     *
     * @param content  原字符串内容
     * @param maskChar 打码符号
     * @return 返回打码后的字符串
     */
    String mask(String content, String maskChar);

}