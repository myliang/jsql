package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 1:15 PM
 * 数据库对应的表类
 */
public class Table extends Base {

    protected String name;  // 表名
    protected String alias; // 表别名

    public Table(){}
    public Table(String name, String alias){
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toSql() {
        if(null != alias)
            return name + " " + alias;
        else return name;
    }
}
