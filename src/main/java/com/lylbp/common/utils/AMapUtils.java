package com.lylbp.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高德地图api工具
 */
@Slf4j
@Component
public class AMapUtils {
    private static String GEO_URL = "https://restapi.amap.com/v3/geocode/geo";
    private static String REGEO_URL = "https://restapi.amap.com/v3/geocode/regeo";
    private static String KEY_STR = "2827f349d589423decf94590f7a48df4";

//    @Value("${GECODE_KEY}")
//    public void setKeyStr(String keyStr) {
//        AMapUtils.KEY_STR = keyStr;
//    }

    /**
     * 记录REG_COUNT
     */
    private static Integer REG_COUNT = 0;
    /**
     * 记录GEO_COUNT
     */
    private static Integer GEO_COUNT = 0;
    /**
     * 最大允许重复次数
     */
    private static Integer ALLOW_MAX = 2;

    /**
     * 高德地图地理编码
     *
     * @param paramMap 参数
     * @return JSONObject
     */
    public static synchronized JSONObject geo(Map<String, Object> paramMap) {
        JSONObject jsonObject = null;
        try {
            if (GEO_COUNT < ALLOW_MAX) {
                String resultJsonStr = HttpUtil.get(GEO_URL, paramMap, 3000);
                jsonObject = checkResultJson(resultJsonStr);
                if (jsonObject == null) {
                    throw new ResResultException(ResResultEnum.SYSTEM_ERR);
                }
            }
            GEO_COUNT = 0;
            if (jsonObject == null || !jsonObject.containsKey("geocodes")) {
                return null;
            }

            JSONArray geocodes = jsonObject.getJSONArray("geocodes");
            Object geocodesObj = geocodes.getObj(0);

            return (JSONObject) geocodesObj;
        } catch (ResResultException resResultException) {
            ++GEO_COUNT;
            return geo(paramMap);
        } catch (Exception exception) {
            log.error("高德地理编码失败,次数:{},错误信息：{}", GEO_COUNT + 1, exception.getMessage());
            return null;
        }
    }

    /**
     * 高德地图逆地理编码
     *
     * @param paramMap 参数
     * @return JSONObject
     */
    public static synchronized JSONObject reGeo(Map<String, Object> paramMap) {
        JSONObject jsonObject = null;
        try {
            if (REG_COUNT < ALLOW_MAX) {
                String resultJsonStr = HttpUtil.get(REGEO_URL, paramMap, 3000);
                jsonObject = checkResultJson(resultJsonStr);
                if (jsonObject == null) {
                    throw new ResResultException(ResResultEnum.SYSTEM_ERR);
                }
            }

            REG_COUNT = 0;
            if (jsonObject == null || !jsonObject.containsKey("regeocode")) {
                return null;
            }

            Object object = jsonObject.get("regeocode");

            return (JSONObject) object;
        } catch (ResResultException resResultException) {
            ++REG_COUNT;
            return reGeo(paramMap);
        } catch (Exception exception) {
            log.error("高德逆地理编码失败,次数:{},错误信息：{}", REG_COUNT + 1, exception.getMessage());
            return null;
        }
    }

    /**
     * 通过地址获取经维度
     *
     * @param address 地址
     * @return JSONObject
     */
    public static String[] addressToLocation(String address) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", getKey());
        paramMap.put("output", "JSON");
        paramMap.put("address", address);

        JSONObject geo = geo(paramMap);
        try {
            String location = (String) geo.get("location");
            return location.split(",");
        } catch (Exception exception) {
            return null;
        }
    }


    /**
     * 通过经维度获取地址
     *
     * @param location 经维度
     * @return JSONObject
     */
    public static String locationToAddress(String location) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", getKey());
        paramMap.put("output", "JSON");
        paramMap.put("location", location);

        JSONObject reGod = reGeo(paramMap);
        try {
            return ObjectUtil.isEmpty(reGod) ? null : (String) reGod.get("formatted_address");
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getKey() {
        List<String> keyList = Arrays.asList(KEY_STR.split(","));
        return keyList.get((int) RandomUtil.randomLong(0, keyList.size()));
    }


    public static JSONObject checkResultJson(String resultJsonStr) {
        if (null == resultJsonStr) {
            return null;
        }
        JSONObject jsonObject = JSONUtil.parseObj(resultJsonStr);
        if (!jsonObject.containsKey("status")) {
            return null;
        }

        int status = Integer.parseInt((String) jsonObject.get("status"));
        if (status == 0) {
            return null;
        }

        return jsonObject;
    }

}

