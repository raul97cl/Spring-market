package com.market.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.model.Product;
import com.market.model.Purchase;
import com.market.model.User;
import com.market.repositositories.PurchaseRepository;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository repository;
	
	@Autowired
	ProductService productService;
	
	public Purchase save(Purchase purchase, User user) {
		purchase.setOwner(user);
		return repository.save(purchase);
	}
	
	public Purchase save(Purchase purchase) {
		return repository.save(purchase);
	}
	
	public Product addProduct(Product product, Purchase purchase) {
		product.setPurchase(purchase);
		return productService.edit(product);
	}
	
	public Purchase findById(long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Purchase> findAll(){
		return repository.findAll();
	}
	
	public List<Purchase> findByOwner(User user){
		return repository.findByOwner(user);
	}
}
