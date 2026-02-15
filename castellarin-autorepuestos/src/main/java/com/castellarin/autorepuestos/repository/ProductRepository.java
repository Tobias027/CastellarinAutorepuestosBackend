package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT p.part_number,p.name,p.category,p.price,p.offer_price,p.image_path\n" +
            "FROM products p \n" +
            "WHERE p.part_number in (\n" +
            "\tSELECT part_number\n" +
            "\tFROM order_items o\n" +
            "\tGROUP BY part_number\n" +
            "\tORDER BY COUNT(part_number) desc\n" +
            "\tLIMIT 4);", nativeQuery = true)
    List<ProductDto> findFeaturedProducts();

    Page findAll(Specification<Product> specification, Pageable pageable);

    Product findProductByPartNumber(String partNumber);

    List<Product> getProductsByPartNumberIsIn(Collection<String> partNumbers);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUCTS SET STOCK = STOCK - :cantidad WHERE part_number = :partNumber AND STOCK >= :cantidad", nativeQuery = true)
    void decrementStock(@Param("cantidad") Integer cantidad, @Param("partNumber") String partNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUCTS SET STOCK = STOCK + :cantidad WHERE part_number = :partNumber", nativeQuery = true)
    void incrementStock(@Param("cantidad") Integer cantidad, @Param("partNumber") String partNumber);
}
