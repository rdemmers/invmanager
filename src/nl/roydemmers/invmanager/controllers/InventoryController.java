package nl.roydemmers.invmanager.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.roydemmers.invmanager.constants.JspPage;
import nl.roydemmers.invmanager.objects.EmailMessage;
import nl.roydemmers.invmanager.objects.InventoryLogItem;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;

@Controller
public class InventoryController extends AbstractController {

	// Shows main inventorytable
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = { "/inventoryitems", "/" })
	public String showInventoryOverview(Model model) {

		List<Product> allInventoryItems = inventoryService.getAllInventoryItems();

		model.addAttribute("inventoryitem", allInventoryItems);
		model.addAttribute("inventoryitembarcode", currentItem);

		return JspPage.INVENTORY_OVERVIEW;
	}

	

	

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/dodelete", method = RequestMethod.POST)
	public String deleteSelectedItemPOST(Model model, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("product_id"));
		inventoryService.deleteInventoryItem(id);
		return JspPage.INVENTORY_OVERVIEW;
	}

	// Used to select an item from the html table in JspPage.INVENTORY_OVERVIEW.
	// Returns an
	// item based on the barcode, parsed via the POST parameter "product_id"
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/databasecall", method = RequestMethod.POST)
	public String displayAdditionItemInformation(Model model, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("product_id"));
		currentItem = inventoryService.getInventoryItem(id);

		return JspPage.NEW_ITEM_SUCCES;
		// returns a page that is not being used. Removing the String return breaks
		// Spring.

	}

	// Uses POST_param "product_mutate" to add or subtract stock from an item based
	// on the barcode.
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/mutateitem", method = RequestMethod.POST)
	public String mutateSelectedItemPOST(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		int id = Integer.parseInt(request.getParameter("product_id"));
		int quantityMutate = Integer.parseInt(request.getParameter("product_mutate"));

		currentItem = inventoryService.getInventoryItem(id);
		int adjustedStock = currentItem.getCurrentStock() + quantityMutate;

		currentItem.setCurrentStock(adjustedStock);

		inventoryService.updateInventoryItem(currentItem);
		inventoryLogItemDao.appendUsernameToLogItem(principal.getName());

		inventoryService.checkStockForMail(id);
		return JspPage.NEW_ITEM_SUCCES;

	}

	// Contains the sidebar with detailed information about the selected item.
	// Seperated to auto-refresh it using javacript.
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/refreshinfo", method = RequestMethod.GET)
	public String refreshInfo(Model model, HttpServletRequest request) {
		model.addAttribute("inventoryitembarcode", currentItem);

		return JspPage.REFRESH_INFO_SIDEBAR;

	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/statistics")
	public String showInventoryStatistics(Model model) {

		Map<Integer, Long> calculated = financialCalculationService.getSuppliersWithTotalWorth();
		List<Supplier> allSuppliers = supplierService.convertSupplierHashmapToObjectList(calculated);

		List<Product> lowItems = inventoryService.getLowInventoryItems();
		List<InventoryLogItem> recentChanges = inventoryService.getRecentChanges(true);

		model.addAttribute("suppliers", allSuppliers);
		model.addAttribute("lowitems", lowItems);
		model.addAttribute("recentchanges", recentChanges);

		return JspPage.INVENTORY_STATISTICS;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping("/about")
	public String showAbout(Model model) {
		model.addAttribute("emailMessage", new EmailMessage());
		return "about";
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value="/submitissue", method= RequestMethod.POST)
	public String submitIssue(Model model, @Valid EmailMessage emailMessage, BindingResult result, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		User user = userService.getUserEmail(principal.getName());
		inventoryMailService.createIssueEmail(emailMessage, user);
		
		// added this so the form displays that the message was actually sent
		// I had to create a new EmailMessage because the mailsender runs on a seperate thread and the body would be changed before the mail was sent.
		model.addAttribute("emailMessage", new EmailMessage("Bericht is verzonden!"));
		return "about";
	}

}
