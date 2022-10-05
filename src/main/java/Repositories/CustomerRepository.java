package Repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByUserName(String userName);
	
	@Transactional
	@Modifying
	@Query("update Customer c set c.cash = ?1 where c.id = ?2")
	void updateUserCash(int cash, Long id);
	
}
