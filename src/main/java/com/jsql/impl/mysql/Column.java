package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 1:16 PM
 * 对应数据库的表中的列
 */
public class Column extends Base{

    protected String name;  // 列名字
    protected String alias; // 列别名

    protected String suffix;  // 列前缀，即表的别名

    public Column(String name, String suffix){
        String[] names = name.split("\\.");
        if(names.length > 1){
            this.name = names[1];
            this.suffix = names[0];
        }else{
            this.name = name;
            this.suffix = suffix;
        }

        if(null != this.suffix)
            this.alias = this.suffix + "_" + this.name;
        else
            this.alias = this.name;
    }

    public String getFullName(){
        if(null != suffix)
            return suffix + "." + name;
        else
            return name;
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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String toSql(){
        return getFullName() + " " + alias;
    }
}
