package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.ProductDetailsDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.mappers.ProductMapper;
import com.castellarin.autorepuestos.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class productsController {

    private final ProductsService productsService;

    @GetMapping("/featured_products")
    public List<ProductDto> getFeaturedProducts(){
        return productsService.getFeaturedProducts();
    }

    @GetMapping("/")
    public Page getProducts(
            @RequestParam(value = "search_term", required = false) String searchTerm,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "brand",required = false) String brand,
            @RequestParam(value =  "min_price",  required = false) Double minPrice,
            @RequestParam(value = "max_price",  required = false) Double maxPrice,
            @RequestParam(value = "page",  required = false, defaultValue = "1") String page,
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

    @GetMapping("/{id}")
    public ProductDetailsDto getProductById(@PathVariable long id){
        return productsService.getProductById(id);
    }

}
