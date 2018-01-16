package nl.roydemmers.invmanager.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javassist.bytecode.Descriptor.Iterator;
import nl.roydemmers.invmanager.constants.JspPage;
import nl.roydemmers.invmanager.objects.GlobalPref;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.InventoryItemDoubleValue;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;
import nl.roydemmers.invmanager.service.PreferenceService;
import nl.roydemmers.invmanager.service.UserService;

@Controller
public class AdminController extends AbstractController {
	
	private Supplier currentSupplier;

	@RequestMapping(value = "/admin")
	public String showAdminDashboard(Model model) {
		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);

		return "admindashboard";
	}

	@RequestMapping("/admin/newaccount")
	public String showNewAccount(Model model) {

		model.addAttribute("user", new User());
		return "newaccount";
	}

	@RequestMapping(value = "/admin/createaccount", method = RequestMethod.POST)
	public String createAccount(@Valid User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "newaccount";
		}

		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		userService.create(user);

		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);

		return "admindashboard";
	}
	
	@RequestMapping(value ="/admin/suppliers")
	public String showSupplierConfig(Model model) {
		List<Supplier> supplierList = supplierService.getAllSuppliers();
		model.addAttribute("supplierList", supplierList);
		
		return "suppliers";
	}
	
	
	@RequestMapping(value = "/admin/supplierdatabasecall", method = RequestMethod.POST)
	public String displayAdditionItemInformation(Model model, HttpServletRequest request) {
		currentSupplier = supplierService.getSupplier(Integer.parseInt(request.getParameter("supplier_id")));
		
		model.addAttribute("currentSupplier", currentSupplier);

		return "suppliercreated";
		// returns a page that is not being used. Removing the String return breaks
		// Spring.

	}
	
	@RequestMapping(value = "/admin/refreshsupplier", method = RequestMethod.GET)
	public String refreshInfo(Model model, HttpServletRequest request) {
		model.addAttribute("currentSupplier", currentSupplier);

		return "refreshsupplier";

	}
	
	@RequestMapping(value = "/admin/doupdate", method = RequestMethod.POST)
	public String editItemPOST(Model model, Supplier editCurrentSupplier, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "refreshsupplier";
		}
		editCurrentSupplier.setSupplierId(currentSupplier.getSupplierId());
		
		supplierService.update(editCurrentSupplier);

		return "suppliercreated";
	}
	
	@RequestMapping(value = "admin/dodeletesupplier", method = RequestMethod.POST)
	public String deleteSelectedSupplierPOST(Model model, HttpServletRequest request) {
		currentSupplier = supplierService.getSupplier(Integer.parseInt(request.getParameter("supplier_id")));
		supplierService.delete(currentSupplier);
		return "suppliers";
	}

	@RequestMapping("/admin/createsupplier")
	public String createNewInventoryItemForm(Model model) {
		model.addAttribute("supplier", new Supplier());

		return "createsupplier";
	}
	
	@RequestMapping(value = "/admin/docreate", method = RequestMethod.POST)
	public String createNewInventoryItemPOST(Model model, @Valid Supplier supplier, BindingResult result) {

		if (result.hasErrors()) {

			return "createsupplier";
		}

		supplierService.create(supplier);
		

		return "suppliercreated";
	}
	
	@RequestMapping(value = "/admin/mail")
	public String refreshInfo(Model model) {
		Map<String, String> preferenceMap = preferenceService.getPreferenceGroup("mail");
		model.addAttribute("preferenceMap", preferenceMap);
		
		return "mail";

	}
	
	@RequestMapping(value = "/admin/updatemail", method = RequestMethod.POST)
	public String updateMail(Model model, @RequestParam Map<String,String> allRequests) {
		
		preferenceService.updateAllPreferencesInDb(allRequests);
		
		Map<String, String> preferenceMap = preferenceService.getPreferenceGroup("mail");
		model.addAttribute("preferenceMap", preferenceMap);

		return "mail";
	}

}
