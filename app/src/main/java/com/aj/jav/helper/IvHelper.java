package com.aj.jav.helper;

import com.aj.jav.utils.EncodeUtility;

/**
 * Created by pascal on 2018/7/10.
 * 解碼用的
 * 切掉前8個字元後取16個字元
 */

public class IvHelper {
    public static String decode(String iv){
        return EncodeUtility.getMd5(iv).substring(8,24);
    }
}
