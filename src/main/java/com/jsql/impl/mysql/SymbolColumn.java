package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolColumn extends Column {

    protected Symbol symbol;

    protected Integer count = 1;    // 占位符号的数量

    public SymbolColumn(String name, String suffix, Symbol symbol, Integer count){
        super(name, suffix);
        this.symbol = symbol;
        this.count = count;
    }

    public static enum Symbol {

        // 操作符号
        eq("=", "", ""),
        notEq("<>", "", ""),
        gt(">", "", ""),
        gtEq(">=", "", ""),
        lt("<", "", ""),
        ltEq("<=", "", ""),
        in("in", "(", ")"),
        notIn("not in", "", ""),
        like("like", "'%", "%'"),
        likeLeft("like", "'%", "'"),
        likeRight("like", "'", "%'"),

        ;

        private Symbol(String text, String before, String after){
            this.text = text;
            this.before = before;
            this.after = after;
        }

        private String text;    // 描述
        private String before;  //
        private String after;   //

        public String toSql(String columnName){
            return toSql(columnName, 1);
        }

        public String toSql(String columnName, Integer cnt){

            StringBuilder sql = new StringBuilder();
            sql.append(columnName).append(" ").append(text).append(" ").append(before);
            for(int i = 0; i < cnt; i++){
                sql.append("?");
                if( i < cnt -1)
                    sql.append(",");
            }
            sql.append(after);
            return sql.toString();
        }
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toSql() {
        return symbol.toSql(getFullName(), count);
    }
}
