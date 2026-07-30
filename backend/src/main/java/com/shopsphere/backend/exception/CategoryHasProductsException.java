package com.shopsphere.backend.exception;

public class CategoryHasProductsException extends RuntimeException{

    public CategoryHasProductsException(Long categoryId, int productCount) {
        super("Cannot delete category with id " + categoryId + ": " + productCount + " product(s) still assigned to it");
    }
}
