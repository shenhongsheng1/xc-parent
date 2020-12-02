package com.xc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ShenHongSheng
 * ClassName: BeanUtilCopy
 * Description:
 * Date: 2020/11/26 16:51
 * @version V1.0
 */
public class BeanCopyUtil {

    private final static Logger logger = LoggerFactory.getLogger(BeanCopyUtil.class);

    private BeanCopyUtil() {}

    public static <T> List<T> copyPropertiesForList(List source, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            if (!CollectionUtils.isEmpty(source)) {
                for (Object o : source) {
                    T instance = (T) Class.forName(clazz.getName()).newInstance();
                    BeanUtils.copyProperties(o, instance);
                    list.add(instance);
                }
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("实例化出错，出错信息：" + e.getMessage());
        }
        return list;
    }
}
