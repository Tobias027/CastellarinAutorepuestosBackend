package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse {
    private List<ProductDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public static PageResponse of(Page<Product> page) {
        return new PageResponse(
                ProductMapper.toDtos(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
