package com.Products.ProductsMicroService.model.entity;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "PRODUCT_NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "PRODUCT_PRICE", length = 50, nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE", length = 50, nullable = false)
    private ProductsType type;
    @Column(name = "PRODUCT_QUANTITY", length = 50, nullable = false)
    private int quantity;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "product_to_configuration",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "configuration_id")
    )
    private Set<ProductConfiguration> configurations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductEntity))
            return false;

        ProductEntity other = (ProductEntity) o;

        return productId != null &&
                productId.equals(other.getProductId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
