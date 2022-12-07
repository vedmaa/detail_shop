package com.example.Shop.models;

import com.example.Shop.config.enums.StatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class CustomOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", unique = true, nullable = false)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status = StatusEnum.Processing;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id_customer")
    private Customer customer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "point_id", referencedColumnName = "id_point")
    private PickupPoint pickupPoint;

    @ManyToOne
    @JoinColumn(name = "code_id", referencedColumnName = "id_code")
    private PromoCode promoCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    private List<OrderItem> items;

    @Transient
    private Integer size;

    @Transient
    private Double itemsPrice;

    @Transient
    private Double totalPrice;

    public Double getTotalPrice() {
        return getItemsPrice() - getItemsPrice() * (promoCode == null ? 0.0 : promoCode.getDiscontAmount()) / 100;
    }

    public Double getItemsPrice() {
        double sum = 0;
        for (var item : items) {
            sum += item.getProduct().getPrice() * item.getCount();
        }
        System.out.println("getItemsPrice = " + sum);
        return sum;
    }

    public Integer getSize() {
        int size = 0;
        for (var item : items) {
            size += item.getCount();
        }
        return size;
    }

    public CustomOrder() {
    }

    public CustomOrder(Date orderDate, StatusEnum status, Customer customer, PickupPoint pickupPoint, PromoCode promoCode, List<OrderItem> items) {
        this.orderDate = orderDate;
        this.status = status;
        this.customer = customer;
        this.pickupPoint = pickupPoint;
        this.promoCode = promoCode;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
