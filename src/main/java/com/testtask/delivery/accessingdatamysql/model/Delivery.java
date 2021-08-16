package com.testtask.delivery.accessingdatamysql.model;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

/**
 * Перечень поставок.
 */
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String number;

    @OneToOne
    private Supplier supplier;

    private LocalDate date;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<DeliveryProduct> deliveryProducts;

    public Delivery() {

    }

    public Delivery(String number, Supplier supplier, LocalDate date) {
        this.number = number;
        this.supplier = supplier;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<DeliveryProduct> getDeliveryProducts() {
        return deliveryProducts;
    }

    public void setDeliveryProducts(List<DeliveryProduct> deliveryProducts) {
        this.deliveryProducts = deliveryProducts;
    }
}
