package com.shopsphere.backend.service;

import com.shopsphere.backend.dto.ProductDTO;
import com.shopsphere.backend.entity.Category;
import com.shopsphere.backend.entity.Product;
import com.shopsphere.backend.exception.CategoryNotFoundException;
import com.shopsphere.backend.exception.DuplicateSkuException;
import com.shopsphere.backend.exception.ProductNotFoundException;
import com.shopsphere.backend.mapper.ProductMapper;
import com.shopsphere.backend.repository.CategoryRepository;
import com.shopsphere.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException((dto.getCategoryId())));

        if (productRepository.existsBySku(dto.getSku())) {
            throw new DuplicateSkuException(dto.getSku());
        }

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .sku(dto.getSku())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }
}
