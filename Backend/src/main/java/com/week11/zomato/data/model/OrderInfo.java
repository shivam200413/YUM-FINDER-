package com.week11.zomato.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, updatable = false, nullable = false)
    private Integer orderid;

    @Column(name = "user_id", unique = false, updatable = false, nullable = false)
    private Integer userid;

    @Column(name = "restaurant_id", unique = false, updatable = true, nullable = false)
    private Integer restaurantid;

    @Column(name = "restaurant_name", unique = false, updatable = true, nullable = false)
    private String restaurantname;

    @Column(name = "total_amount", unique = false, updatable = true, nullable = false)
    private Integer totalamount = 0;

    @Column(name = "order_flag", unique = false, updatable = true, nullable = false)
    private Integer orderflag = 0;


    public Integer getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(Integer orderflag) {
        this.orderflag = orderflag;
    }

    @Column(name = "delivery_address", unique = false, updatable = true, nullable = false)
    private String deliveryaddress;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderinfo")
    private List<OrderFoodItems> orderFoodItems = new ArrayList<OrderFoodItems>();

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(Integer restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }

    public List<OrderFoodItems> getOrderFoodItems() {
        return orderFoodItems;
    }

    public void setOrderFoodItems(List<OrderFoodItems> orderFoodItems) {
        this.orderFoodItems = orderFoodItems;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public Integer getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Integer totalamount) {
        this.totalamount = totalamount;
    }

}
