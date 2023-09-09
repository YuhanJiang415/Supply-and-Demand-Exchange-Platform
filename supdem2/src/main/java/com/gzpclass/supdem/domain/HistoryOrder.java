package com.gzpclass.supdem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HistoryOrder {
    @Id
    @GeneratedValue
    Integer Hid;
    Double H_lat;
    Double H_lng;
    Integer flag;

    public Integer getHid() {
        return Hid;
    }

    public void setHid(Integer hid) {
        Hid = hid;
    }

    public Double getH_lat() {
        return H_lat;
    }

    public void setH_lat(Double h_lat) {
        H_lat = h_lat;
    }

    public Double getH_lng() {
        return H_lng;
    }

    public void setH_lng(Double h_lng) {
        H_lng = h_lng;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
