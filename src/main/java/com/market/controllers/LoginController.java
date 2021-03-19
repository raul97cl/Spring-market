package com.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.market.model.User;
import com.market.services.UserService;
import com.market.upload.StorageService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;

	@GetMapping("/")
	public String index() {
		return "redirect:/public/";
	}

	@GetMapping("/auth/login")
	public String login(Model model) {
		model.addAttribute("usuario", new User());
		return "login";
	}

	@PostMapping("/auth/signup")
	public String signup(@ModelAttribute User user, @RequestParam("file") MultipartFile file) {
		
		if (!file.isEmpty()) {

			// Process the file
			String image = storageService.store(file);
			user.setAvatar(MvcUriComponentsBuilder.
						fromMethodName(FileController.class, "serveFile", image)
						.build().toUriString());
		}
		// Add a new product
		userService.signUp(user);
		return "redirect:/auth/login";
	}
}
