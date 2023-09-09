package com.gzpclass.supdem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class goods {

    @Id
    @GeneratedValue
    private Integer goodsId;
    private Integer sellerId;
    private String goodsName;
    private float goodsPrice;
    private String status;


    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer seller_id) {
        this.sellerId = seller_id;
    }


    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goods_id) {
        this.goodsId = goods_id;
    }

    public String getName() {
        return goodsName;
    }
    public void setName(String goods_name) {
        this.goodsName = goods_name;
    }

    public float getPrice() {
        return goodsPrice;
    }
    public void setPrice(float goods_price) {
        this.goodsPrice = goods_price;
    }

    public String getstatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
