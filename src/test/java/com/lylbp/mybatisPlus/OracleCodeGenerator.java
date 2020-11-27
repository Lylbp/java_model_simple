package com.lylbp.mybatisPlus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.lylbp.mybatisPlus.util.CommonUtils;

/**
 * Oracle 数据库代码生成类
 *
 * @author Erwin Feng[xfsy_2015@163.com]
 * @since 2019-04-17 10:33
 */
public class OracleCodeGenerator {
    public static void main(String[] args) {
        DbType dbType = DbType.ORACLE;
        String dbUrl = "jdbc:oracle:thin:@192.168.1.151:1521:cdb1";
        String username = "C##dar";
        String password = "1qaz2wsx";
        String driver = "oracle.jdbc.driver.OracleDriver";
        // 表前缀，生成的实体类，不含前缀
        String[] tablePrefixes = {""};
        // 表名，为空，生成所有的表
        String[] tableNames = {""};
        // 字段前缀
        String[] fieldPrefixes = {};
        CommonUtils.execute(dbType, dbUrl, username, password, driver, tablePrefixes, tableNames, fieldPrefixes);
    }

}
