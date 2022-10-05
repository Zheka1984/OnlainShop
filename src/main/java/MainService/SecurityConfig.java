package MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import Repositories.CustomerRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService  userDetailsService;
	
	@Bean
	   public PasswordEncoder encoder() {
	      return new BCryptPasswordEncoder();
	   }
	 @Autowired
	 CustomerRepository rep;
	 
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//System.out.println(rep.findByUserName("admin").get(0).getRole());
    	UserDetails ud = userDetailsService.loadUserByUsername("admin");
    	System.out.println(ud.getUsername()+" "+ud.getPassword());
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

	   @Override
	   protected void configure(HttpSecurity http) throws Exception {
		   System.out.println("start");
		   http.csrf().disable();
	       http
	        .authorizeRequests().antMatchers("/save").hasAuthority("ADMIN")
	        .and()
	        .authorizeRequests().antMatchers("/allproducts").hasAnyAuthority("ADMIN", "USER")
	        .and()
	        .authorizeRequests().antMatchers("/showRoles").hasAuthority("ADMIN")
           .and()
           .authorizeRequests().antMatchers("/changeProduct{id}").hasAuthority("ADMIN")
           .and()
           .authorizeRequests().antMatchers("/buyProduct{id}").hasAuthority("USER")
           .and()
           .authorizeRequests().antMatchers("/deleteProduct{id}").hasAuthority("ADMIN")
           .and()
           .authorizeRequests()
           .anyRequest().permitAll()
           .and()
           .formLogin().permitAll();
           
	   }

}
