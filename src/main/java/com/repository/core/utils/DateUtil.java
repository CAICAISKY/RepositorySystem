package com.repository.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * @author schuyler
 */
public class DateUtil {

    /**
     * 获取当前时间年月日字符串
     * @return
     */
    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
