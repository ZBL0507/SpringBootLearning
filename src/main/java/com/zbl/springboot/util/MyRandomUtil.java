package com.zbl.springboot.util;

import cn.hutool.core.util.RandomUtil;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/25 10:15
 */
public class MyRandomUtil {

    private final static String Xing = "赵钱孙李周吴郑王" +
            "冯陈褚卫蒋沈韩杨" +
            "朱秦尤许何吕施张" +
            "孔曹严华金魏陶姜" +
            "戚谢邹喻柏水窦章" +
            "云苏潘葛奚范彭郎" +
            "鲁韦昌马苗凤花方" +
            "俞任袁柳酆鲍史唐" +
            "费廉岑薛雷贺倪汤" +
            "滕殷罗毕郝邬安常" +
            "乐于时傅皮卞齐康" +
            "伍余元卜顾孟平黄" +
            "和穆萧尹姚邵湛汪" +
            "祁毛禹狄米贝明臧" +
            "计伏成戴谈宋茅庞" +
            "熊纪舒屈项祝董梁" +
            "杜阮蓝闵席季麻强" +
            "贾路娄危江童颜郭" +
            "梅盛林刁钟徐邱骆" +
            "高夏蔡田樊胡凌霍" +
            "虞万支柯昝管卢莫" +
            "经房裘缪干解应宗" +
            "丁宣贲邓郁单杭洪";

    private final static String Ming = "是利空打击圣诞节了房间定金都付了看见对方弗兰克感觉记电流反馈结果";

    /**
     * 生成一个随机的姓名
     *
     * @return 生成的随机姓名
     */
    public static String randomName() {
        return RandomUtil.randomString(Xing, 1) + RandomUtil.randomString(Ming, 1);
    }

    /**
     * 生成一个随机的年龄
     *
     * @return 生成的随机年龄
     */
    public static int randomAge() {
        return RandomUtil.randomInt(0, 120);
    }

    /**
     * 生成一个随机的性别
     *
     * @return 生成的随机性别
     */
    public static String randomSex() {
        return RandomUtil.randomString("男女", 1);
    }
}
