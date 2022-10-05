package Controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Entities.Customer;
import Entities.Store;
import Repositories.CustomerRepository;
import Repositories.ProductRepository;



@Controller
public class ProductController {
	
	@Autowired
	ProductRepository rep;
	
	@Autowired
	CustomerRepository cr;

	@GetMapping("/save")
	public String save(Model model) {
		Store store = new Store();
		model.addAttribute("store", store);
		return "save";
	}
	
	@PostMapping("/save")
	public String saveProduct(@Valid @ModelAttribute("store") Store store, BindingResult result, Model model) {
		try {
			rep.save(store);
		}
		catch(Exception e) {
			System.out.println("an error has occurred");
			result.addError(new ObjectError("key is already exist", "key is already exist"));
		}
		model.addAttribute("store", store);
	return "save";
	}
	@GetMapping("/allproducts")
	public String showProducts() {
		return "allproducts";
	}
	@PostMapping("/allproducts")
	public String showProducts(@RequestParam("maxprice") Integer maxprice, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Store> list = rep.findAll();
		List<Store> result = list.stream().sorted().filter(t -> t.getPrice() <= maxprice).collect(Collectors.toList());
		model.addAttribute("result", result);
		authentication.getAuthorities().forEach(t -> System.out.println(t.getAuthority()));
		if(authentication.getAuthorities().toArray()[0].toString().contains("ADMIN"))
		return "showProductsAdmin";
		return "showProductsUser";
	}
	@GetMapping("/changeProduct{id}")
	public String changeProduct(@PathVariable("id") int id, Model model) {
		Store store = rep.findById((long) id).get();
		model.addAttribute("product1", store);
		return "changeProduct";
	}
	@PostMapping("/change")
	public String changeProduct(@Valid @ModelAttribute("product1") Store product, BindingResult result) {
		rep.updateProductById(product.getId(), product.getProduct(), product.getQuantity(), product.getPrice());
		return "allproducts";
	}
	
	@GetMapping("/buyProduct{id}")
	public String buyProduct(@PathVariable("id") int id, Model model) {
		Store product = rep.findById((long) id).get();
		model.addAttribute("form", new BuyingForm());
		model.addAttribute("product", product);
		return "buyProduct";
	}
	@PostMapping("/buyProduct{id}")
	public String buyProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("form") BuyingForm form, BindingResult result,
			Principal principal, Model model) {	
		Store product = rep.findById(id).get();
		int quantity = form.getQuantity();
		int price = product.getPrice();
		Customer customer = cr.findByUserName(principal.getName()).get(0);
		Long idCustomer = customer.getId();
		int cash = customer.getCash() - (quantity * price);
		System.out.println(cash);
		customer.setCash(cash);
		model.addAttribute("product", product);
		quantity = product.getQuantity() - quantity;
		if(result.hasErrors() == false) {
		cr.updateUserCash(customer.getCash(), idCustomer);
		product.buying(quantity);
		if(quantity > 0) {
		rep.updateProductQuantity(quantity);
		}	
		}
		if(quantity <= 0) 
			result.addError(new ObjectError("there is not enough product in the store", "there is not enough product in the store"));
		return "buyProduct";
	}
	@PostMapping("/delete")
	public String deleteProduct(@ModelAttribute("product1") Store product, Model modelt) {
		try{
			rep.deleteById(product.getId());
			modelt.addAttribute("message", "delete successful");
		}
		catch(Exception e) {
			modelt.addAttribute("message", "delete fail");
		}
		return "results";
	}	
}











