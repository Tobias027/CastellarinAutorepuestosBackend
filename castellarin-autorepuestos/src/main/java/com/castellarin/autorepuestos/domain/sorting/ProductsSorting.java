package com.castellarin.autorepuestos.domain.sorting;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class ProductsSorting {


    /**TODO
    Esta Truncado para solo price sino habria que crear un arraylist final con los posibles campos y que elija el campo que matchea con el parametro*/
    public static Sort createSort(String direction){
        return direction.equalsIgnoreCase("asc" )?
                Sort.by("price").ascending()
                : Sort.by("price").descending();
    }

    public static Pageable createPageable(int page, int size, String direction){
        Sort sort = createSort(direction);
        return PageRequest.of(page, size, sort);
    }
}
