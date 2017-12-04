package net.eoutech.base.tcpserver.entity;

import java.io.Serializable;

/**
 * Created by WangY on 2017/5/17 0017.
 */
public class EouFlashMsg implements Serializable{
    private Integer order;
    private Integer number;
    private EouData data;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public EouData getData() {
        return data;
    }

    public void setData(EouData data) {
        this.data = data;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "order=" + order + ",number=" + number + ",data=" + data.toString() + ",count=" + count;
    }
}
