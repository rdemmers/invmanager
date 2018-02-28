package nl.roydemmers.invmanager.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class InventoryController extends AbstractController {

	// Shows main inventorytable
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value ="/")
	public String showReactHome(HttpServletRequest request) {

		

		return "react";
	}


}