package com.testtask.delivery.accessingdatamysql.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Перечень поставленной продукции.
 */
@Entity
public class DeliveryProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Product product;

    @JoinColumn(name = "delivery")
    @ManyToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    private Long size;

    private Double purchaseSum;

    public DeliveryProduct() {

    }

    public DeliveryProduct(Product product, Delivery delivery, Long size, Double purchaseSum) {
        this.product = product;
        this.delivery = delivery;
        this.size = size;
        this.purchaseSum = purchaseSum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Double getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(Double sum) {
        this.purchaseSum = sum;
    }
}
