package com.Products.ProductsMicroService.model.entity;

import com.Products.ProductsMicroService.common.ConfigurationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_configuration")
public class ProductConfiguration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ConfigurationType type;

    @ManyToMany(mappedBy = "configurations")
    private Set<ProductEntity> products = new HashSet<>();
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
