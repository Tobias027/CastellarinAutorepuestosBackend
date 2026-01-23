package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.*;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.service.OrdersService;
import com.castellarin.autorepuestos.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final OrdersService ordersService;

    @GetMapping("/featured_products")
    public ResponseEntity<List<ProductDto>> getFeaturedProducts(){
        List<ProductDto> productFeaturedDtos = productsService.getFeaturedProducts();
        return ResponseEntity.ok(productFeaturedDtos);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse> getProducts(
            @RequestParam(value = "search_term", required = false) String searchTerm,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "brand",required = false) String brand,
            //@RequestParam(value =  "min_price",  required = false) Double minPrice,
            //@RequestParam(value = "max_price",  required = false) Double maxPrice,
            @RequestParam(value = "page",  required = false, defaultValue = "0") String page,
            @RequestParam(value = "direction",  required = false, defaultValue = "asc") String direction
    ){
        PageResponse pageResponse = productsService.getProducts(searchTerm,category,brand,page,direction);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{partNumber}")
    public ResponseEntity<ProductDetailsDto> getProductByPartNumber(@PathVariable String partNumber){
        ProductDetailsDto productDetailsDto = productsService.getProductDetailsByProductPart(partNumber);
        return ResponseEntity.ok(productDetailsDto);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<ProductCartDto>> getCartProducts(@RequestParam("ids") List<String> partNumbers){
        List<ProductCartDto> productDetailsDto = productsService.getProductByProductsParts(partNumbers);
        return ResponseEntity.ok(productDetailsDto);
    }

    @PostMapping("/create_product")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto createProductDto) throws URISyntaxException {
        Product createdProduct = productsService.createProduct(createProductDto);
        URI location = new URI("http://localhost:8081/products/"+createdProduct.getPartNumber());
        return ResponseEntity.created(location).body(createdProduct);
    }

}
