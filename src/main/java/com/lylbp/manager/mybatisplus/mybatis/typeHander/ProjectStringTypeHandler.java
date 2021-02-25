package com.lylbp.manager.mybatisplus.mybatis.typeHander;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;


public class ProjectStringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, s);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String str = rs.getString(columnName);
        return str != null ? str : "";
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String str = rs.getString(columnIndex);
        return str != null ? str : "";
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String str = cs.getString(columnIndex);

        return str != null ? str : "";
    }
}
