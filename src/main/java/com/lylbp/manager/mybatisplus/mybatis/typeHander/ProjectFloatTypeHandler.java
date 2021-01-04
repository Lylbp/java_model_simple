package com.lylbp.manager.mybatisplus.mybatis.typeHander;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author weiwenbin
 * @date 2020/6/3 下午4:43
 */
public class ProjectFloatTypeHandler extends BaseTypeHandler<Float> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Float parameter, JdbcType jdbcType) throws SQLException {
        ps.setFloat(i, parameter);
    }

    @Override
    public Float getNullableResult(ResultSet rs, String columnName) throws SQLException {
        float result = rs.getFloat(columnName);
        return result == 0.0F && rs.wasNull() ? 0.0F : result;
    }

    @Override
    public Float getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        float result = rs.getFloat(columnIndex);
        return result == 0.0F && rs.wasNull() ? 0.0F : result;
    }

    @Override
    public Float getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        float result = cs.getFloat(columnIndex);
        return result == 0.0F && cs.wasNull() ? 0.0F : result;
    }
}
