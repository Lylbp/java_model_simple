package com.lylbp.manger.mybatisPlus.mybatis.typeHander;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author weiwenbin
 * @Date 2020/6/3 下午4:44
 */
public class ProjectBigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BigDecimal bigDecimal, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBigDecimal(i, bigDecimal);
    }

    @Override
    public BigDecimal getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnName);
        return bigDecimal != null ? bigDecimal : new BigDecimal("0.00");
    }

    @Override
    public BigDecimal getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnIndex);
        return bigDecimal != null ? bigDecimal : new BigDecimal("0.00");
    }

    @Override
    public BigDecimal getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        BigDecimal bigDecimal = cs.getBigDecimal(columnIndex);
        return bigDecimal != null ? bigDecimal : new BigDecimal("0.00");
    }
}
