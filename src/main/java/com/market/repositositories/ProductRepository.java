package com.market.repositositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.model.Product;
import com.market.model.Purchase;
import com.market.model.User;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByOwner(User owner);
	List<Product> findByPurchase(Purchase purchase);
	List<Product> findByPurchaseIsNull();
	List<Product> findByNameContainsIgnoreCaseAndPurchaseIsNull(String name);
	List<Product> findByNameContainsIgnoreCaseAndOwner(String name, User owner);
}
