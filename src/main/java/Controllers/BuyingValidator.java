/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import Entities.Customer;
import Repositories.CustomerRepository;

/**
 *
 * @author Админ
 */
public class BuyingValidator implements ConstraintValidator<EqualFields, BuyingForm>{
	
	@Autowired
	CustomerRepository rep;

    @Override
    public void initialize(EqualFields constraint) {
    	
    }

    @Override
    public boolean isValid(BuyingForm bf, ConstraintValidatorContext cvc) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Customer> list = rep.findByUserName(name);
        if(list.isEmpty() == false) {
        Customer customer = list.get(0);
        int cost = bf.getPrice()*bf.getQuantity();
        System.out.println("cost"+bf.getPrice()+" "+bf.getQuantity());
        System.out.println("cash"+customer.getCash());
        if(cost <= customer.getCash())
            return true;
        }
        return false; 
    }
}
