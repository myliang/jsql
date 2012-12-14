package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupColumn extends Column {

    public GroupColumn(String name, String suffix) {
        super(name, suffix);
    }

    @Override
    public String toSql() {
        return getFullName();
    }
}
