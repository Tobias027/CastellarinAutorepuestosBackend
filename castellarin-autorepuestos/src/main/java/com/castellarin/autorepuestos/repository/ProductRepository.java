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
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.product_id,p.name,p.category,p.price,p.offer_price,p.image_path\n" +
            "FROM products p \n" +
            "WHERE p.product_id in (\n" +
            "\tSELECT product_id\n" +
            "\tFROM order_items o\n" +
            "\tGROUP BY product_id\n" +
            "\tORDER BY COUNT(product_id) desc\n" +
            "\tLIMIT 4);", nativeQuery = true)
    List<ProductDto> findFeaturedProducts();

    Page findAll(Specification<Product> specification, Pageable pageable);

    Product findById(long id);

    @Query(value = "SELECT *\n" +
            "FROM products p \n" +
            "WHERE p.part_number = :partNumber;", nativeQuery = true)
    Optional<Product> findByPartNumber();

    Product findProductByPartNumber(String partNumber);


    List<Product> getProductsByPartNumberIsIn(Collection<String> partNumbers);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUCTS SET STOCK = STOCK - :cantidad WHERE id = :productoId AND STOCK >= :cantidad", nativeQuery = true)
    int decrementStock(@Param("cantidad") Integer cantidad, @Param("productoId") String productoId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUCTS SET STOCK = STOCK + :cantidad WHERE id = :productoId", nativeQuery = true)
    int incrementStock(@Param("cantidad") Integer cantidad, @Param("productoId") String productoId);
}
