package nl.roydemmers.invmanager.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.roydemmers.invmanager.constants.JspPage;
import nl.roydemmers.invmanager.dao.InventoryLogItemDao;
import nl.roydemmers.invmanager.dao.SupplierDao;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.InventoryItemDoubleValue;
import nl.roydemmers.invmanager.objects.InventoryLogItem;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.service.FinancialCalculationService;
import nl.roydemmers.invmanager.service.InventoryMailService;
import nl.roydemmers.invmanager.service.InventoryService;
import nl.roydemmers.invmanager.service.SupplierService;

@Controller
public class InventoryController extends AbstractController {


	// Shows main inventorytable
	@RequestMapping(value= {"/inventoryitems", "/"})
	public String showInventoryOverview(Model model) {

		List<InventoryItem> allInventoryItems = inventoryService.getAllInventoryItems();

		model.addAttribute("inventoryitem", allInventoryItems);
		model.addAttribute("inventoryitembarcode", currentItem);

		return JspPage.INVENTORY_OVERVIEW;
	}

	// Shows a form to create a new item
	// Temporarily maps the item to a seperate object, needed to convert price double to a long
	@RequestMapping("/createinventoryitem")
	public String createNewInventoryItemForm(Model model) {
		model.addAttribute("inventoryItemTemp", new InventoryItemDoubleValue());
		model.addAttribute("suppliers", supplierService.getAllSuppliers());
		return JspPage.NEW_ITEM_FORM;
	}

	// Logic to add the item created in /createinventoryitem
	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String createNewInventoryItemPOST(Model model, @Valid InventoryItemDoubleValue inventoryItemTemp, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {

			return JspPage.NEW_ITEM_FORM;
		}
		
		inventoryItemTemp.setSupplier(supplierService.getSupplier(Integer.parseInt(request.getParameter("supplierId"))));

		InventoryItem inventoryItem = inventoryItemTemp.convertPriceToLong();

		inventoryService.create(inventoryItem);

		return JspPage.NEW_ITEM_SUCCES;
	}

	@RequestMapping(value = "/edititem", method = RequestMethod.GET)
	public String editItemForm(Model model, @RequestParam("id") int id) {

		currentItem = inventoryService.getInventoryItem(id);
		InventoryItemDoubleValue doubleItem = currentItem.convertPriceToDouble();
		model.addAttribute("inventoryItemTemp", doubleItem);
		return JspPage.EDIT_ITEM_FORM;
	}

	@RequestMapping(value = "/doupdate", method = RequestMethod.POST)
	public String editItemPOST(Model model, InventoryItemDoubleValue inventoryItemTemp, BindingResult result) {

		if (result.hasErrors()) {
			return JspPage.EDIT_ITEM_FORM;
		}

		InventoryItem inventoryItem = inventoryItemTemp.convertPriceToLong();

		inventoryItem.setId(currentItem.getId());
		inventoryItem.setSupplier(currentItem.getSupplier());
		inventoryService.updateInventoryItem(inventoryItem);
		inventoryService.checkStockForMail(inventoryItem.getId());

		return JspPage.NEW_ITEM_SUCCES;
	}

	@RequestMapping(value = "/dodelete", method = RequestMethod.POST)
	public String deleteSelectedItemPOST(Model model, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("product_id"));
		inventoryService.deleteInventoryItem(id);
		return JspPage.INVENTORY_OVERVIEW;
	}

	// Used to select an item from the html table in JspPage.INVENTORY_OVERVIEW.
	// Returns an
	// item based on the barcode, parsed via the POST parameter "product_id"
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
	@RequestMapping(value = "/refreshinfo", method = RequestMethod.GET)
	public String refreshInfo(Model model, HttpServletRequest request) {
		model.addAttribute("inventoryitembarcode", currentItem);

		return JspPage.REFRESH_INFO_SIDEBAR;

	}

	@RequestMapping(value ="/statistics")
	public String showInventoryStatistics(Model model) {

		Map<Integer, Long> calculated = financialCalculationService.getSuppliersWithTotalWorth();
		List<Supplier> allSuppliers = supplierService.convertSupplierHashmapToObjectList(calculated);

		List<InventoryItem> lowItems = inventoryService.getLowInventoryItems();
		List<InventoryLogItem> recentChanges = inventoryService.getRecentChanges(true);

		model.addAttribute("suppliers", allSuppliers);
		model.addAttribute("lowitems", lowItems);
		model.addAttribute("recentchanges", recentChanges);
		
		
		

		return JspPage.INVENTORY_STATISTICS;
	}
	
	@RequestMapping(value="/getnotification", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public InventoryLogItem getNotification(){
		InventoryLogItem logItem = inventoryLogItemDao.getLastLogItem();
		InventoryItem inventoryItem = inventoryService.getInventoryItem(logItem.getItemID());
		inventoryService.fillLogItemWithItemProps(logItem, inventoryItem);
		
		return inventoryService.checkIfObjectChangeIsLessThanXMs(logItem, 10000);
		
		
	}
	
	

}
