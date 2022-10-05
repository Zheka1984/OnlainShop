package Entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@Entity
public class Customer implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	 @NotEmpty(message = "User's name cannot be empty")
	 @Size(min = 2, max = 100)
	 @Column(unique = true)
	private String userName;
	private String password;

	private int cash;
	
    private String role;
	
	private ArrayList<GrantedAuthority> authList = new ArrayList<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authList;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;;
	}
	
	public void buying(int sum) {
		this.cash = this.cash - sum;
	}

	public Customer setUsername(String username) {
		this.userName = username;
		return this;
	}

	public Customer setPassword(String password) {
		this.password = password;
		return this;
	}


	public String getRole() {
		return role;
	}

	public Customer setRole(String role) {
		this.role = role;
		GrantedAuthority ga = new SimpleGrantedAuthority(role);
		this.authList.add(ga);
		return this;
	}

	public Long getId() {
		return id;
	}
}
