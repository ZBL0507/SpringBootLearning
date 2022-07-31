package com.zbl.springboot.annotation;

import com.zbl.springboot.enumm.DataMaskingFunc;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataMasking {

    DataMaskingFunc maskFunc() default DataMaskingFunc.NO_MASK;

}