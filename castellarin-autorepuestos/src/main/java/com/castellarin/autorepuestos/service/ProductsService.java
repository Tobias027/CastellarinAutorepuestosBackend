package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.ProductDetailsDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.entity.ProductVehicleComp;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
import com.castellarin.autorepuestos.domain.mappers.ProductDetailsMapper;
import com.castellarin.autorepuestos.domain.mappers.ProductMapper;
import com.castellarin.autorepuestos.domain.sorting.ProductsSorting;
import com.castellarin.autorepuestos.domain.specification.ProductsSpecification;
import com.castellarin.autorepuestos.repository.ProductRepository;

import com.castellarin.autorepuestos.repository.ProductVehicleCompRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final ProductVehicleCompRepository productVehicleCompRepository;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    public List<ProductDto> getFeaturedProducts(){
        List<ProductDto> productDtos = productRepository.findFeaturedProducts();
        if(productDtos.isEmpty()){
            throw new EntityNotFoundException("Productos no encontrados");
        }
        return productDtos;
    }

    public Page getProducts(
            String searchTerm,
            String category,
            String brand,
            Double minPrice,
            Double maxPrice,
            String page,
            String direction
    ){
        Specification<Product> specification = Specification.allOf(ProductsSpecification.isActive())
                .and(ProductsSpecification.contains(searchTerm))
                .and(ProductsSpecification.hasBrand(brand))
                .and(ProductsSpecification.hasCategory(category))
                .and(ProductsSpecification.priceLowerthan(minPrice))
                .and((ProductsSpecification.priceGreaterthan(maxPrice)));

        Pageable pageable = ProductsSorting.createPageable(Integer.parseInt(page), DEFAULT_SIZE, direction);

        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(ProductMapper::toDto);
    }

    public ProductDetailsDto getProductById(long productId){
        Product product = productRepository.findById(productId);
        if(product == null){
            throw new EntityNotFoundException("Producto no encontrado");
        }
        List<Vehicle> compatible_vehicles = productVehicleCompRepository.findCompatible(productId);
        if(compatible_vehicles.isEmpty()){
            throw new EntityNotFoundException("Compatibilidad no encontrada");
        }
        return ProductDetailsMapper.toDto(product, compatible_vehicles);
    }
}
