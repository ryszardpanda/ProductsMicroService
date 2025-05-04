package com.Products.ProductsMicroService.model.entity;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PRODUCT_NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "PRODUCT_PRICE", length = 50, nullable = false)
    private double price;
    @Column(name = "PRODUCT_TYPE", length = 50, nullable = false)
    private ProductsType type;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductConfiguration configuration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductEntity))
            return false;

        ProductEntity other = (ProductEntity) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
