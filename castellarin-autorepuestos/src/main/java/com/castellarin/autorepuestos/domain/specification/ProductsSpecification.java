package com.castellarin.autorepuestos.domain.specification;

import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecification {

    public static Specification<Product> hasCategory(String category){
        return (root, query, criteriaBuilder) -> {
            if(category != null){
                return criteriaBuilder.equal(root.get("category"),category);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> priceGreaterthan(Double minPrice){
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null) {
                return criteriaBuilder.greaterThan(root.get("offerPrice"), minPrice);
            }
            else{
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Product> priceLowerthan(Double maxPrice){
        return (root, query, criteriaBuilder) -> {
            if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("offerPrice"), maxPrice);
            }
            else{
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Product> hasBrand(String brand){
        return (root, query, criteriaBuilder) -> {
            if(brand != null){
                return criteriaBuilder.equal(root.get("brand"), brand);
            }
            else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Product> sortByPriceDesc(String desc){
        return (root, query, criteriaBuilder) -> {
            if(desc.equals("desc")){
                return (jakarta.persistence.criteria.Predicate) criteriaBuilder.desc(root.get("offerPrice"));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Product> sortByPriceAsc(String asc){
        return (root, query, criteriaBuilder) -> {
            if(asc.equals("asc")){
                return (jakarta.persistence.criteria.Predicate) criteriaBuilder.asc(root.get("price"));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Product> isActive(){
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }

    public static Specification<Product> contains(String searchTerm){
        return (root, query, criteriaBuilder) -> {
            if(searchTerm != null){
                String likeSearchTerm = "%"+searchTerm.toLowerCase()+"%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeSearchTerm),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), likeSearchTerm),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),likeSearchTerm),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("notas")), likeSearchTerm)
                );
            }
            else {
                return criteriaBuilder.conjunction();
            }
        };
    }



}
