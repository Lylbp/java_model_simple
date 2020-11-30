package com.lylbp.manger.mybatisplus.mybatis.typeHander;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author weiwenbin
 * @Date 2020/6/3 下午4:43
 */
public class ProjectDoubleTypeHandler extends BaseTypeHandler<Double> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Double aDouble, JdbcType jdbcType) throws SQLException {
        preparedStatement.setDouble(i, aDouble);
    }

    @Override
    public Double getNullableResult(ResultSet rs, String columnName) throws SQLException {
        double result = rs.getDouble(columnName);
        return result == 0.0D && rs.wasNull() ? 0.0D : result;
    }

    @Override
    public Double getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        double result = rs.getDouble(columnIndex);
        return result == 0.0D && rs.wasNull() ? 0.0D : result;
    }

    @Override
    public Double getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        double result = cs.getDouble(columnIndex);
        return result == 0.0D && cs.wasNull() ? 0.0D : result;
    }
}
