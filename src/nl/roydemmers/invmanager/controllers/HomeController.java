package nl.roydemmers.invmanager.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import nl.roydemmers.invmanager.objects.Product;


@Controller
public class HomeController extends AbstractController {

	// Shows main inventorytable
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = {"/", "/orders"})
	public String showReactHome(HttpServletRequest request) {

		

		return "react";
	}

	
}