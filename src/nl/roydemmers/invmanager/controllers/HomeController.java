package nl.roydemmers.invmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
// Controller solely used for mapping the homepage to "/"
@Controller
public class HomeController {
	@RequestMapping("/")
	public String showHome(Model model) {

		return "home";
	}

}**/
