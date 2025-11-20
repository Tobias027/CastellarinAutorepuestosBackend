package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.CreateProductDto;
import com.castellarin.autorepuestos.domain.dto.ProductDetailsDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.entity.ProductBrand;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final ProductBrandRepository productBrandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVehicleCompRepository productVehicleCompRepository;
    private final ImageService imageService;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    //TODO es correcto devolver asi o debo devolver un ResponseEntity?
    public List<ProductDto> getFeaturedProducts(){
        List<ProductDto> productDtos = productRepository.findFeaturedProducts();
        return productDtos;
    }

    //TODO es correcto devolver una page ??
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
        List<Vehicle> compatible_vehicles = productVehicleCompRepository.findByProductId(productId);
        if(compatible_vehicles.isEmpty()) {
            throw new EntityNotFoundException("Compatibilidad no encontrada");
        }
        return ProductDetailsMapper.toDto(product,compatible_vehicles);
    }

    public ProductDetailsDto getProductDetailsByProductPart(String productPart){
        Product product = productRepository.findByPartNumber(productPart);
        if(product == null){
            //throw new EntityNotFoundException("Producto no encontrado");
            return null;
        }
        List<Vehicle> compatible_vehicles = productVehicleCompRepository.findByProductId(product.getProductId());
        if(compatible_vehicles.isEmpty()) {
            //throw new EntityNotFoundException("Compatibilidad no encontrada");
            return null;
        }
        return ProductDetailsMapper.toDto(product,compatible_vehicles);
    }

    public Product getProductByProductPart(String productPart){
        Product product = productRepository.findByPartNumber(productPart);
        if(product == null){
            throw new EntityNotFoundException("Producto no encontrado");
        }
        return product;
    }

    public Product createProduct(CreateProductDto createProductDto){
        Product product = new Product();
        System.out.println("BRANDS: "+productBrandRepository.findAll());
        System.out.println("CATEGORIES: "+categoryRepository.findAll());
        ProductBrand brand = productBrandRepository.findProductBrandByBrand(createProductDto.getBrand());

        //Category category = categoryRepository.findAll(createProductDto.getCategory());
        String imagePath = imageService.uploadProductsImage(createProductDto.getBase64Image(),createProductDto.getName());

        product.setName(createProductDto.getName());
        product.setBrand(brand);
        product.setPartNumber(createProductDto.getPartNumber());
        //product.setCategory(category);
        product.setDescription(createProductDto.getDescription());
        product.setSpecs(createProductDto.getSpecs());
        product.setPrice(createProductDto.getPrice());
        product.setOfferPrice(createProductDto.getOfferPrice());
        product.setStock(createProductDto.getStock());
        product.setImagePath(imagePath);
        product.setIsActive(product.getIsActive());
        product.setCostPrice(product.getCostPrice());
        product.setNotes(createProductDto.getNotes());

        Product createdProduct = productRepository.save(product);

        return createdProduct;
    }

}
