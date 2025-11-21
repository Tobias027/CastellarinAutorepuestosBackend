package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategory(String category);
}
