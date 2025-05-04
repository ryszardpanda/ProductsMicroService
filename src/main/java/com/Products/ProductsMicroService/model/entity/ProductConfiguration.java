package com.Products.ProductsMicroService.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProductConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String processor;
    private Integer ram;

    private String color;
    private String batteryCapacity;

    @ElementCollection
    @CollectionTable(name = "product_accessories", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "accessory")
    private List<String> accessories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductConfiguration))
            return false;

        ProductConfiguration other = (ProductConfiguration) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
