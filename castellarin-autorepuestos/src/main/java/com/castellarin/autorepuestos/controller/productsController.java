package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.CreateProductDto;
import com.castellarin.autorepuestos.domain.dto.ProductDetailsDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.mappers.ProductDetailsMapper;
import com.castellarin.autorepuestos.domain.mappers.ProductMapper;
import com.castellarin.autorepuestos.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/featured_products")
    public ResponseEntity<List<ProductDto>> getFeaturedProducts(){
        List<ProductDto> productFeaturedDtos = productsService.getFeaturedProducts();
        return ResponseEntity.ok(productFeaturedDtos);
    }

    //TODO: NO DEVOLVER UNA PAGE PORQUE LA ESTRUCTURA DE LA PAGE PUEDE CAMBIAR EN UN FUTURO Y ROMPERIA TODO
    @GetMapping("/")
    public Page getProducts(
            @RequestParam(value = "search_term", required = false) String searchTerm,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "brand",required = false) String brand,
            @RequestParam(value =  "min_price",  required = false) Double minPrice,
            @RequestParam(value = "max_price",  required = false) Double maxPrice,
            @RequestParam(value = "page",  required = false, defaultValue = "0") String page,
            @RequestParam(value = "direction",  required = false, defaultValue = "asc") String direction
    ){
        return productsService.getProducts(
                searchTerm,
                category,
                brand,
                minPrice,
                maxPrice,
                page,
                direction
        );
    }

    @GetMapping("/{part_number}")
    public ResponseEntity<ProductDetailsDto> getProductByPartNumber(@PathVariable String part_number){
        ProductDetailsDto productDetailsDto = productsService.getProductDetailsByProductPart(part_number);
        return ResponseEntity.ok(productDetailsDto);
    }

    @PostMapping("/create_product")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto createProductDto) throws URISyntaxException {
        Product createdProduct = productsService.createProduct(createProductDto);
        URI location = new URI("http://localhost:8081/products/"+createdProduct.getProductId());
        return ResponseEntity.created(location).body(createdProduct);
    }

}
