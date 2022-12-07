package com.example.Shop.models;

import javax.persistence.*;

@Entity
@Table(name = "OrderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item", unique = true, nullable = false)
    private Long id;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    CustomOrder order;

    public OrderItem() {
    }

    public OrderItem(Integer count, Product product, CustomOrder order) {
        this.count = count;
        this.product = product;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomOrder getCustomOrder() {
        return order;
    }

    public void setCustomOrder(CustomOrder order) {
        this.order = order;
    }
}
