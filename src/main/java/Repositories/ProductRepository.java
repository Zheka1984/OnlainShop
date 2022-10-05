package Repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Entities.Customer;
import Entities.Store;


public interface ProductRepository extends JpaRepository<Store, Long> {
	
	@Transactional
	@Modifying
	@Query("update Store s set s.quantity = ?1")
	void updateProductQuantity(Integer quantity);
	
	@Transactional
	@Modifying
	@Query("update Store s set s.product = ?2, s.quantity = ?3, s.price = ?4 where s.id = ?1")
	void updateProductById(Long long1, String product, int quantity, int price);
	
	List<Store> findByProduct(String product);
	
}
