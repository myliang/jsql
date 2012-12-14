package com.jsql.impl.mysql;

import com.jsql.Sql;
import com.jsql.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SqlImpl extends Sql {

    private static final String Semicolon = ", ";

    private Table table;    // 主表

    private List<JoinTable> joinTables = new ArrayList<JoinTable>();

    private List<SymbolColumn> symbolColumns = new ArrayList<SymbolColumn>();
    private List<OrderColumn> orderColumns = new ArrayList<OrderColumn>();
    private List<FunctionColumn> functionColumns = new ArrayList<FunctionColumn>();

    private List<SelectColumn> selectColumns = new ArrayList<SelectColumn>();
    private List<GroupColumn> groupColumns = new ArrayList<GroupColumn>();

    // where values
    private List<Object> values = new ArrayList<Object>();


    // 供更新，插入使用
    private List<SymbolColumn> saveOrUpdateColumns = new ArrayList<SymbolColumn>();
    private List<Object> insertValues = new ArrayList<Object>();

    private Paginate paginate = null;

    public SqlImpl(String tableName) {
        table = new Table(tableName, null);
    }

    public SqlImpl(String tableName, String aliasTableName) {
        table = new Table(tableName, aliasTableName);
    }

    @Override
    public Sql eqw(String columnName, Object value) {
        saveOrUpdateColumns.add(new SymbolColumn(columnName, table.alias, SymbolColumn.Symbol.eq, 1));
        insertValues.add(value);
        return this;
    }

    @Override
    public Sql eq(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.eq, columnName, value);
    }

    @Override
    public Sql notEq(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.notEq, columnName, value);
    }

    @Override
    public Sql gt(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.gt, columnName, value);
    }

    @Override
    public Sql gtEq(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.gtEq, columnName, value);
    }

    @Override
    public Sql lt(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.lt, columnName, value);
    }

    @Override
    public Sql ltEq(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.ltEq, columnName, value);
    }

    @Override
    public Sql range(String columnName, Object minValue, Object maxValue) {
        callSymbol(SymbolColumn.Symbol.gtEq, columnName, minValue);
        callSymbol(SymbolColumn.Symbol.ltEq, columnName, maxValue);
        return this;
    }

    @Override
    public Sql in(String columnName, Object... values) {
        return callSymbol(SymbolColumn.Symbol.in, columnName, values);
    }

    @Override
    public Sql notIn(String columnName, Object... values) {
        return callSymbol(SymbolColumn.Symbol.notIn, columnName, values);
    }

    @Override
    public Sql likeLeft(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.likeLeft, columnName, value);
    }

    @Override
    public Sql likeRight(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.likeRight, columnName, value);
    }

    @Override
    public Sql like(String columnName, Object value) {
        return callSymbol(SymbolColumn.Symbol.like, columnName, value);
    }

    @Override
    public Sql limit(Integer page, Integer pageRows) {
        paginate = new Paginate(page, pageRows);
        return this;
    }

    @Override
    public Sql select(String... columnNames) {
        for(String columnName : columnNames)
            this.selectColumns.add(new SelectColumn(columnName, table.alias));
        return this;
    }

    @Override
    public Sql group(String... columnNames) {
        for(String columnName : columnNames)
            this.groupColumns.add(new GroupColumn(columnName, table.alias));
        return this;
    }

    @Override
    public Sql desc(String... columnNames) {
        return callOrder(OrderColumn.Order.desc, columnNames);
    }

    @Override
    public Sql asc(String... columnNames) {
        return callOrder(OrderColumn.Order.asc, columnNames);
    }

    @Override
    public Sql sum(String... columnNames) {
        return callFunction(FunctionColumn.Function.sum, columnNames);
    }

    @Override
    public Sql min(String... columnNames) {
        return callFunction(FunctionColumn.Function.min, columnNames);
    }

    @Override
    public Sql max(String... columnNames) {
        return callFunction(FunctionColumn.Function.max, columnNames);
    }

    @Override
    public Sql avg(String... columnNames) {
        return callFunction(FunctionColumn.Function.avg, columnNames);
    }

    @Override
    public Sql inner(String tableName, String aliasTableName, String left, String right) {
        joinTables.add(new JoinTable(tableName, aliasTableName, new Column(left, table.alias), new Column(right, aliasTableName), JoinTable.Join.inner));
        return this;
    }

    @Override
    public Sql left(String tableName, String aliasTableName, String left, String right) {
        joinTables.add(new JoinTable(tableName, aliasTableName, new Column(left, table.alias), new Column(right, aliasTableName), JoinTable.Join.left));
        return this;
    }

    @Override
    public Sql right(String tableName, String aliasTableName, String left, String right) {
        joinTables.add(new JoinTable(tableName, aliasTableName, new Column(left, table.alias), new Column(right, aliasTableName), JoinTable.Join.right));
        return this;
    }

    @Override
    public String getQuerySql() {
        StringBuilder buffer = new StringBuilder("select ");

        // select
        if(selectColumns.size() <= 0)
            buffer.append(table.alias).append(".* ");
        else
            buffer.append(join(selectColumns, Semicolon));

        // function
        if(functionColumns.size() > 0)
            buffer.append(Semicolon).append(join(functionColumns, Semicolon));

        // table
        buffer.append(" from ").append(table.toSql());

        // table join
        if(joinTables.size() > 0)
            buffer.append(join(joinTables, " "));

        // where
        if(symbolColumns.size() > 0)
            buffer.append(" where ").append(join(symbolColumns, " and "));

        // group
        if(groupColumns.size() > 0)
            buffer.append(" group by ").append(join(groupColumns, Semicolon));

        // order
        if(orderColumns.size() > 0)
            buffer.append(" order by ").append(join(orderColumns, Semicolon));

        // limit
        if(null != paginate)
            buffer.append(" limit ").append(paginate.start()).append(Semicolon).append(paginate.getRows());

        return buffer.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder buffer = new StringBuilder("update ").append(table.toSql()).append(" set ");

        if(saveOrUpdateColumns.size() > 0)
            buffer.append(join(saveOrUpdateColumns, Semicolon));

        // where
        if(symbolColumns.size() > 0)
            buffer.append(" where ").append(join(symbolColumns, " and "));

        return buffer.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder buffer = new StringBuilder("insert into");

        StringBuilder columns = new StringBuilder();
        StringBuilder symbols = new StringBuilder();
        for(SymbolColumn symbolColumn : saveOrUpdateColumns){
            columns.append(symbolColumn.getName());
            symbols.append("?");
            if(!symbolColumn.equals(saveOrUpdateColumns.get(saveOrUpdateColumns.size() - 1))){
                columns.append(Semicolon);
                symbols.append(Semicolon);
            }
        }

        buffer.append(table.getName()).append("(").append(columns).append(") values(").append(symbols).append(")");
        return buffer.toString();
    }

    @Override
    public String getDeleteSql() {
        StringBuilder buffer = new StringBuilder("delete from ");
        // table
        buffer.append(table.toSql());
        // where
        if(symbolColumns.size() > 0)
            buffer.append(" where ").append(join(symbolColumns, " and "));
        return buffer.toString();
    }

    @Override
    public List<Object> getQueryValues() {
        return values;
    }

    @Override
    public List<Object> getInsertValues() {
        return insertValues;
    }
    @Override
    public List<Object> getUpdateValues() {
        List<Object> returnList = new ArrayList<Object>();
        returnList.addAll(insertValues);
        returnList.addAll(values);
        return returnList;
    }

    /*********************** private methods ********************************************/

    private static String join(List<?> objects, String split){
        return ArrayUtil.join(objects.toArray(), split, new ArrayUtil.JoinCallBack() {
            @Override
            public String it(Object obj) {
                if(obj instanceof String)
                    return (String)obj;
                else if(obj instanceof Base)
                    return ((Base) obj).toSql();
                return obj.toString();
            }
        });
    }

    private Sql callSymbol(SymbolColumn.Symbol symbol, String columnName, Object... values){
        if(values.length > 0){
            if(null != values[0])
                symbolColumns.add(new SymbolColumn(columnName, table.alias, symbol,  values.length));
            for(Object value : values){
                if(null != value)
                    this.values.add(value);
            }
        }
        return this;
    }

    private Sql callFunction(FunctionColumn.Function function, String... columnNames){
        for(String columnName : columnNames)
            functionColumns.add(new FunctionColumn(columnName, table.alias, function));
        return this;
    }

    private Sql callOrder(OrderColumn.Order order, String... columnNames){
        for(String columnName : columnNames)
            orderColumns.add(new OrderColumn(columnName, table.alias, order));
        return this;
    }

    public static void main(String[] args){
        Sql sql = new SqlImpl("user", "u");
        sql.select("id","name").max("money").in("type", 1,2).eq("agent", 5).eq("b", "77").group("id").desc("id");
        System.out.println("::::query=" + sql.getQuerySql());
        System.out.println("::::values=" + sql.getQueryValues());

        // joinTable
        sql = new SqlImpl("user", "u");
        sql.inner("books", "b", "id", "user_id").eq("b.type", 1);
        System.out.println("::::query=" + sql.getQuerySql());
        //System.out.println("::::values=" + sql.getDeleteSql());
        System.out.println("::::values=" + sql.getQueryValues());

        // update
        sql = new SqlImpl("user");
        sql.eqw("name", "myliang").eqw("agent", 50).eq("id", 1);

        System.out.println("::::update.sql=" + sql.getUpdateSql());
        System.out.println("::::update.values=" + sql.getUpdateValues());

        // delete
        sql = new SqlImpl("user");
        sql.eq("id", 11).like("name", "myliang");

        System.out.println("::::update.sql=" + sql.getDeleteSql());
        System.out.println("::::update.values=" + sql.getQueryValues());
    }
}
