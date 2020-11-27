package com.lylbp.manger.hbase.util;

import cn.hutool.core.collection.ListUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

/**
 * @Author weiwenbin
 * @Date: 2020/11/5 上午10:46
 */
public class HBaseUtil {
    /**
     * 分区【10, 20, 30】 -> ( ,10] (10,20] (20,30] (30, )
     *
     * @param keys 分区集合[10, 20, 30]
     * @return byte二维数组
     */
    public static byte[][] getSplitKeys(List<String> keys) {
        byte[][] splitKeys = new byte[keys.size()][];
        TreeSet<byte[]> rows = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for (String key : keys) {
            rows.add(Bytes.toBytes(key));
        }
        int i = 0;
        for (byte[] row : rows) {
            splitKeys[i] = row;
            i++;
        }
        return splitKeys;
    }

    /**
     * 结果集转map集合
     *
     * @param resultScanner 结果集
     * @return List<Map < String, String>>
     */
    public static List<Map<String, String>> resultScanner2MapList(ResultScanner resultScanner) {
        List<Map<String, String>> list = new ArrayList<>(10);
        for (Result result : resultScanner) {
            Map<String, String> map = result2Map(result);
            list.add(map);
        }

        return list;
    }

    /**
     * 结果转map
     *
     * @param result 结果
     * @return Map<String, String>
     */
    public static Map<String, String> result2Map(Result result) {
        HashMap<String, String> map = new HashMap<>(10);
        //rowkey
        String row = Bytes.toString(result.getRow());
        map.put("row", row);
        //获取列簇
        for (Cell cell : result.listCells()) {
            //列簇
            String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            //列
            String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                    cell.getQualifierLength());
            //值
            String data = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            map.put(family + ":" + qualifier, data);
        }

        return map;
    }
}
