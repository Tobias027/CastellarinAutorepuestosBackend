package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
    ProductBrand findProductBrandByBrand(String brand);
}
