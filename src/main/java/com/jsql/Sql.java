package com.jsql;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/13/12
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 * QUERY: select * from t where<strong> id = ? and name = ? or type = 1</strong>
 * EXE_INSERT: insert into t<strong>(id, name, type) values(?, ?, ?)</strong>;
 * EXE_UPDATE: update t set <strong>name = ?, type = ? where id = ?</strong>
 * EXE_DELETE: delete from t where <strong>id = ?</strong>
 */
public abstract class Sql implements Serializable {

    // 基本操作

    /**
     * 供写操作使用
     * @param columnName
     * @param value
     * @return
     */
    public abstract Sql eqw(String columnName, Object value);

    public abstract Sql eq(String columnName, Object value);
    public abstract Sql notEq(String columnName, Object value);
    public abstract Sql gt(String columnName, Object value);
    public abstract Sql gtEq(String columnName, Object value);
    public abstract Sql lt(String columnName, Object value);
    public abstract Sql ltEq(String columnName, Object value);
    public abstract Sql range(String columnName, Object minValue, Object maxValue);
    // in
    public abstract Sql in(String columnName, Object ...values);
    public abstract Sql notIn(String columnName, Object ...values);
    // like
    public abstract Sql likeLeft(String columnName, Object value);
    public abstract Sql likeRight(String columnName, Object value);

    public abstract Sql like(String columnName, Object value);

    /**
     *
     * @param page 弟几页，从0开始
     * @param pageRows 每页显示的数量
     * @return
     */
    public abstract Sql limit(Integer page, Integer pageRows);

    // select
    public abstract Sql select(String ...columnNames);

    // group by
    public abstract Sql group(String ...columnNames);

    // order by
    public abstract Sql desc(String ...columnNames);
    public abstract Sql asc(String ...columnNames);

    // base function
    public abstract Sql sum(String ...columnNames);
    public abstract Sql min(String ...columnNames);
    public abstract Sql max(String ...columnNames);
    public abstract Sql avg(String ...columnNames);

    // joins
    public abstract Sql inner(String tableName, String aliasTableName, String left, String right);
    public abstract Sql left(String tableName, String aliasTableName, String left, String right);
    public abstract Sql right(String tableName, String aliasTableName, String left, String right);


    /**
     * 返回sql字符串
     * @return
     */
    public abstract String getQuerySql();
    public abstract String getUpdateSql();
    public abstract String getInsertSql();
    public abstract String getDeleteSql();

    /**
     * 返回所有的具体的值
     * @return
     */
    public abstract List<Object> getQueryValues();
    public abstract List<Object> getInsertValues();
    public abstract List<Object> getUpdateValues();

}
