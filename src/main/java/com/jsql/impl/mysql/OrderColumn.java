package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderColumn extends Column {

    protected Order order;

    public OrderColumn(String name, String suffix, Order order) {
        super(name, suffix);
        this.order = order;
    }

    public static enum Order {
        desc, asc;

        public String toSql(String columnName){
            StringBuilder sql = new StringBuilder();
            sql.append(columnName).append(" ").append(name());
            return sql.toString();
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toSql() {
        return order.toSql(getFullName());
    }
}
