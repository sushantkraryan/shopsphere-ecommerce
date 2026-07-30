package com.shopsphere.backend;

import com.shopsphere.backend.entity.Category;
import com.shopsphere.backend.entity.Product;
import com.shopsphere.backend.repository.CategoryRepository;
import com.shopsphere.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
