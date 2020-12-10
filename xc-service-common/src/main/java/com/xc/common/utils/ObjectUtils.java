package com.xc.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author ShenHongSheng
 * ClassName: ObjectUtils
 * Description:
 * Date: 2020/12/10 17:19
 * @version V1.0
 */
public class ObjectUtils {

    public static boolean isNullOrEmpty(Object o) {
        if (null == o) {
            return true;
        } else if (o instanceof String && ((String)o).length() == 0) {
            return true;
        } else if (o instanceof StringBuffer && ((StringBuffer)o).length() == 0) {
            return true;
        } else if (o instanceof Collection && ((Collection)o).isEmpty()) {
            return true;
        } else if (o instanceof Map && ((Map)o).isEmpty()) {
            return true;
        } else if (o instanceof Void) {
            return true;
        } else {
            return o instanceof Object[] && ((Object[])((Object[])o)).length == 0;
        }
    }
}
