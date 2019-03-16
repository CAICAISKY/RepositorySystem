package com.repository.core.utils;

import com.repository.core.vo.VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图类相关工具类
 * @author schuyler
 */
@Slf4j
public class VOUtil {

    public static <T extends VO, S> List<T> ObjectList2VoList(List<S> sList, Class<T> clazz) {
        List<T> voList = new ArrayList<>();
        for (S s : sList) {
            try {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(s, t);
                voList.add(t);
            } catch (Exception e) {
                log.error("【实体转视图】出现异常!");
                e.printStackTrace();
            }
        }
        return voList;
    }
}
