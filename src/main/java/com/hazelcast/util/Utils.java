package com.hazelcast.util;

import java.util.Collection;

/**
 * @author abidk
 * 
 */
public class Utils {

    /**
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
	return (null == object) ? true : false;
    }

    /**
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
	return isNull(object) ? false : true;
    }

    /**
     * @param objects
     * @return
     */
    public static boolean isEmpty(Collection<? extends Object> objects) {
	return (isNull(objects) || objects.size() == 0) ? true : false;
    }

    /**
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Collection<? extends Object> objects) {
	return isEmpty(objects) ? false : true;
    }
}
