package com.shopsphere.backend.service;

import com.shopsphere.backend.dto.CategoryDTO;
import com.shopsphere.backend.entity.Category;
import com.shopsphere.backend.entity.Product;
import com.shopsphere.backend.exception.CategoryHasProductsException;
import com.shopsphere.backend.exception.CategoryNotFoundException;
import com.shopsphere.backend.mapper.CategoryMapper;
import com.shopsphere.backend.repository.CategoryRepository;
import com.shopsphere.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        List<Product> existingProducts =
                productRepository.findByCategoryId(id);

        if (!existingProducts.isEmpty()) {
            throw new CategoryHasProductsException(id, existingProducts.size());
        }

        categoryRepository.delete(category);
    }
}