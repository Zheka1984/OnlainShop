package Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Entities.Customer;
import Repositories.CustomerRepository;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerRepository rep;
	
	@Autowired
	BCryptPasswordEncoder bpe;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/signOn") //создание нового юзера
	public String registration(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "signon";
	}
	@PostMapping("/signOn")
	public String registration(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model) {
		String password = customer.getPassword();
		customer = customer.setPassword(bpe.encode(password));
		model.addAttribute("customer", customer);
		rep.save(customer);
		return "redirect:/allproducts"; //после сохранения нового пользователя редирект на выбор продукта, где нужно будет ввести логин и пароль
	}
	@GetMapping("/signOut")
	public void signOut() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.setAuthenticated(false);
	}
	@GetMapping("/showRoles")
	public String showRoles(Model model) {
		List<Customer> list = rep.findAll();
		model.addAttribute("list", list);
		return "showUsers";
	}
}
