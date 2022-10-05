package onlineShop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;

import Entities.Store;
import MainService.BasicApplication;
import Repositories.ProductRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicApplication.class)
class TestProductRepositiry {
	
	@Autowired
    ProductRepository rep;
	static Store product = new Store();

	@Test
	void test() {
		product.setPrice(100);
		product.setProduct("potato1561");
		product.setQuantity(200);
	    rep.save(product);	
	    product = rep.findByProduct("potato1561").get(0);
		rep.updateProductById(product.getId(), "potato1552", 250, 150);
		List<Store> list = rep.findByProduct("potato1552");
		Store store = list.get(0);
		rep.delete(store);
		if(store.getPrice()==150&&store.getQuantity()==250&&store.getProduct().equals("potato1552"))
			assertTrue(true);
		else assertTrue(false);
	}

}
