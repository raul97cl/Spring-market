package com.market.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.market.model.Product;
import com.market.model.User;
import com.market.services.ProductService;
import com.market.services.UserService;
import com.market.upload.StorageService;

@Controller
@RequestMapping("/app")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	StorageService storageService;
	
	private User user;
	
	@ModelAttribute("my_products")
	public List<Product> myProducts(){
		
		//Find the products of an user
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findByEmail(email);
		return productService.ownerProducts(user);
	}
	
	@GetMapping("/my_products_list")
	public String myProductsList(Model model, @RequestParam(name="q",required = false) String query) {
		if(query != null)
			model.addAttribute("my_products", productService.searchMyProducts(query, user));
		return "/app/product/list";
	}
	
	@GetMapping("/my_products_list/{id}/delete")
	public String deleteProduct(@PathVariable Long id) {
		
		//Delete the product of an user
		Product product = productService.findById(id);
		if(product.getPurchase() == null)
			productService.delete(product);
		return "redirect:/app/my_products_list";
	}
	
	@GetMapping("/product/new")
	public String newProductForm(Model model) {
		
		model.addAttribute("product",new Product());
		
		return "app/product/form";
	}
	
	@PostMapping("/product/new/submit")
	public String newProductSubmit(@ModelAttribute Product product, 
			@RequestParam("file") MultipartFile file) {
		//Add a new product
		if(!file.isEmpty()) {
			
			//Process the file
			String image = storageService.store(file);
			product.setImage(MvcUriComponentsBuilder
							.fromMethodName(FileController.class, "serveFile", image)
							.build().toUriString());
		}
		product.setProductOwner(user);
		productService.save(product);
		
		return "redirect:/app/my_products_list";
	}
}
