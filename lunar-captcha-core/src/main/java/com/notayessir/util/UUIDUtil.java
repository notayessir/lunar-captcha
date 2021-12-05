package com.notayessir.util;

import java.util.UUID;

/**
 * uuid util
 */
public class UUIDUtil {


    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
