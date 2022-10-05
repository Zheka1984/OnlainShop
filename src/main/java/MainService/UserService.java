package MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import Repositories.CustomerRepository;

@Component
public class UserService implements UserDetailsService {
	
	 @Autowired
	 CustomerRepository customerRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return customerRep.findByUserName(username).get(0);
	}

}
