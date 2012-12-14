package com.jsql.impl.mysql;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/14/12
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Paginate {

    private Integer index = 0; // from 0
    private Integer rows = 10;   // page rows

    public Paginate(){}
    public Paginate(Integer index, Integer rows){
        if(null != index)
            this.index = index;
        this.rows = rows;
    }

    public Integer start(){
        return index * rows;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
