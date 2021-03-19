package com.market.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.model.Product;
import com.market.model.Purchase;
import com.market.model.User;
import com.market.repositositories.ProductRepository;
import com.market.upload.StorageService;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	
	@Autowired
	StorageService storageService;
	
	
	public void delete(long id) {
		Product product = findById(id);
		if(!product.getImage().isEmpty())
			storageService.delete(product.getImage());
		repository.deleteById(id);
	}
	
	public void delete(Product product) {
		if(!product.getImage().isEmpty())
			storageService.delete(product.getImage());
		repository.delete(product);
	}
	
	public Product save(Product product) {
		return repository.save(product);
	}
	
	public Product edit(Product product) {
		return repository.save(product);
	}
	
	public Product findById(long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public List<Product> ownerProducts(User user){
		return repository.findByOwner(user);
	}
	
	public List<Product> purchaseProducts(Purchase purchase){
		return repository.findByPurchase(purchase);
	}
	
	public List<Product> productsNotPurchased(){
		return repository.findByPurchaseIsNull();
	}
	
	public List<Product> search(String query){
		return repository.findByNameContainsIgnoreCaseAndPurchaseIsNull(query);
	}
	
	public List<Product> searchMyProducts(String query, User owner){
		return repository.findByNameContainsIgnoreCaseAndOwner(query, owner);
	}
	
	public List<Product> productsById(List<Long> ids){
		return repository.findAllById(ids);
	}
}
