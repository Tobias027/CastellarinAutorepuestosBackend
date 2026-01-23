package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.*;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.ProductDetailsMapper;
import com.castellarin.autorepuestos.domain.mappers.ProductMapper;
import com.castellarin.autorepuestos.domain.sorting.ProductsSorting;
import com.castellarin.autorepuestos.domain.specification.ProductsSpecification;
import com.castellarin.autorepuestos.repository.CategoryRepository;
import com.castellarin.autorepuestos.repository.ProductBrandRepository;
import com.castellarin.autorepuestos.repository.ProductRepository;

import com.castellarin.autorepuestos.repository.ProductVehicleCompRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final ProductBrandRepository productBrandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVehicleCompRepository productVehicleCompRepository;
    private final ImageService imageService;
    private static final int DEFAULT_SIZE = 20;

    public List<ProductDto> getFeaturedProducts(){
        return productRepository.findFeaturedProducts();
    }

    public PageResponse getProducts(
            String searchTerm,
            String category,
            String brand,
            String page,
            String direction
    ){
        Specification<Product> specification = Specification.allOf(ProductsSpecification.isActive())
                .and(ProductsSpecification.contains(searchTerm))
                .and(ProductsSpecification.hasBrand(brand))
                .and(ProductsSpecification.hasCategory(category));
                //.and(ProductsSpecification.priceLowerthan(minPrice))
                //.and(ProductsSpecification.priceGreaterthan(maxPrice));

        Pageable pageable = ProductsSorting.createPageable(Integer.parseInt(page), DEFAULT_SIZE, direction);

        Page products = productRepository.findAll(specification, pageable);
        return PageResponse.of(products);
    }

    /*
    public ProductDetailsDto getProductById(long productId){
        Product product = productRepository.findById(productId);
        if(product == null){
            return null;
        }
        List<Vehicle> compatible_vehicles = productVehicleCompRepository.findByProductId(product.getProductId());
        if(compatible_vehicles.isEmpty()) {
            return null;
        }
        return ProductDetailsMapper.toDto(product,compatible_vehicles);
    }*/

    public ProductDetailsDto getProductDetailsByProductPart(String productPart){
        Product product = productRepository.findProductByPartNumber(productPart.toLowerCase());
        if(product == null){
            return null;
        }
        List<Vehicle> compatible_vehicles = productVehicleCompRepository.findByProductId(product.getProductId());
        if(compatible_vehicles.isEmpty()) {
            return null;
        }
        return ProductDetailsMapper.toDto(product,compatible_vehicles);
    }

    public List<ProductCartDto> getProductByProductsParts(List<String> productsParts){
        List<Product> products = productRepository.getProductsByPartNumberIsIn(productsParts.stream().map(String::toLowerCase).collect(Collectors.toList()));
        return ProductMapper.toCartProductsDto(products);
    }

    public Product getProductByProductPart(String productPart){
        Product product = productRepository.findProductByPartNumber(productPart);
        if(product==null){
            throw new EntityNotFoundException("Product not found");
        }
        if(!product.getIsActive()){
            throw new EntityNotFoundException("Product is not active");
        }
        return product;
    }

    public Product createProduct(CreateProductDto createProductDto){
        Product product = new Product();
        ProductBrand brand = productBrandRepository.findProductBrandByBrand(createProductDto.getBrand());
        Category category = categoryRepository.findByCategory(createProductDto.getCategory());
        String imagePath = imageService.uploadProductsImage(createProductDto.getBase64Image(),createProductDto.getName());

        product.setName(createProductDto.getName());
        product.setBrand(brand);
        product.setPartNumber(createProductDto.getPartNumber());
        product.setCategory(category);
        product.setDescription(createProductDto.getDescription());
        product.setSpecs(createProductDto.getSpecs());
        product.setPrice(createProductDto.getPrice());
        product.setOfferPrice(createProductDto.getOfferPrice());
        product.setStock(createProductDto.getStock());
        product.setImagePath(imagePath);
        product.setIsActive(createProductDto.getIsActive());
        product.setCostPrice(createProductDto.getCostPrice());
        product.setNotes(createProductDto.getNotes());

        return productRepository.save(product);
    }

}
