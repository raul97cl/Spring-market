package com.market.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.model.Product;
import com.market.model.Purchase;
import com.market.model.User;
import com.market.services.ProductService;
import com.market.services.PurchaseService;
import com.market.services.UserService;

@Controller
@RequestMapping("/app")
public class PurchaseController {

	@Autowired
	PurchaseService purchaseService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HttpSession session;
	
	private User user;
	
	@ModelAttribute("cart")
	public List<Product> cartProducts(){
		List<Long> content = (List<Long>) session.getAttribute("cart");
		if(content == null)
			return null;
		else
			return productService.productsById(content);
	}
	
	@ModelAttribute("total_cart")
	public Double totalCart() {
		
		//Calculate the total price of the products in the cart
		List<Product> products = cartProducts();		
		if(products != null) {
			return products.stream().mapToDouble(product -> product.getPrice()).sum();
		}else
			return 0.0;
	}
	
	@ModelAttribute("my_purchases")
	public List<Purchase> myPurchases(){
		
		//Find the purchases of an user
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findByEmail(email);
		return purchaseService.findByOwner(user);
	}
	
	@GetMapping("/cart")
	public String showCart(Model model) {
		return "app/purchase/cart";
	}
	
	@GetMapping("/cart/add/{id}")
	public String addToCart(Model model ,@PathVariable Long id) {
		
		//If the cart not contains the product then add it to the cart
		List<Long> content = (List<Long>)session.getAttribute("cart");
		if(content == null)
			content = new ArrayList<>();
		if(!content.contains(id))
			content.add(id);
		session.setAttribute("cart", content);
		
		return "redirect:/app/cart";
	}
	
	@GetMapping("/cart/delete/{id}")
	public String deleteFromCart(Model model, @PathVariable Long id) {
		
		//Delete the product from the cart if exist
		List<Long> content = (List<Long>)session.getAttribute("cart");
		if(content == null)
			return "redirect:/public";
		content.remove(id);
				
		if(content.isEmpty())
			session.removeAttribute("cart");
		else
			session.setAttribute("cart", content);
		
		return "redirect:/app/cart";
			
	}
	
	@GetMapping("/cart/finalize")
	public String checkout() {
		
		//Create a purchase with the products of the cart
		List<Long> content = (List<Long>) session.getAttribute("cart");
		if(content == null)
			return "redirect:/public";
		
		List<Product> products = cartProducts();
		Purchase purchase = purchaseService.save(new Purchase(), user);
		products.forEach(product -> purchaseService.addProduct(product, purchase));
		session.removeAttribute("cart");
		
		return "redirect:/app/purchase/receipt/"+purchase.getId();
	}
	
	@GetMapping("/purchase/receipt/{id}")
	public String showReceipt(Model model, @PathVariable Long id) {
		
		//Search the purchase and show the products and the total cost
		Purchase purchase = purchaseService.findById(id);
		List<Product> products = productService.purchaseProducts(purchase);
		model.addAttribute("products",products);
		model.addAttribute("purchase", purchase);
		model.addAttribute("total_cart",products.stream().mapToDouble(product -> product.getPrice()).sum());
		return "/app/purchase/receipt";
	}
	
	//Shoe the receipts of the previous purchases
	@GetMapping("/my_purchases_list")
	public String myPurchasesList() {
		return "/app/purchase/purchaseList";
	}
}
