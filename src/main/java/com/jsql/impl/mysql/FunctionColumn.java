package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionColumn extends Column {

    protected Function function;

    public FunctionColumn(String name, String suffix, Function function) {
        super(name, suffix);
        this.function = function;
    }

    public static enum Function {
        min, max, sum, avg;

        public String toSql(String columnName){
            StringBuilder sql = new StringBuilder();
            sql.append(this.name()).append("(").append(columnName).append(")");
            return sql.toString();
        }
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toSql() {
        return function.toSql(getFullName()) + " " + alias;
    }
}
