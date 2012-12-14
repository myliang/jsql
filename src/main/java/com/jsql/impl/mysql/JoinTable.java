package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinTable extends Table {

    protected Column left;  // 左列
    protected Column right; // 右列

    protected Join join;    // 类型

    public static enum Join {
        inner, left, right
    }

    public JoinTable(){}

    public JoinTable(String name, String alias, Column left, Column right, Join join){
        super(name, alias);
        this.left = left;
        this.right = right;
        this.join = join;
    }

    public Column getLeft() {
        return left;
    }

    public void setLeft(Column left) {
        this.left = left;
    }

    public Column getRight() {
        return right;
    }

    public void setRight(Column right) {
        this.right = right;
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    @Override
    public String toSql() {
        StringBuilder sql = new StringBuilder(" ");
        sql.append(join.name()).append(" join ").append(name).append(" ").append(alias).append(" on ");
        sql.append(left.getFullName()).append("=").append(right.getFullName()).append(" ");
        return sql.toString();
    }
}
