package com.jsp.medishop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.medishop.dto.Customer;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.CustomerService;
/**
 * @author vikas
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@PostMapping(value = "/insert")
	public ResponseStructure<Customer> saveCustomerController(@RequestBody Customer customer) {
		return service.saveCustomerService(customer);
	}
	//
	@GetMapping(value = "/Customerlogin/{email}/{password}")
	public ResponseStructure<Customer> loginCustomerByEmailAndPasswordService(@PathVariable String email,@PathVariable String password) {
		return service.loginCustomerByEmailAndPasswordService(email, password);
		
	}
	@GetMapping(value = "/logoutcustomer")
	public ResponseEntity<String> logoutCustomer() {
		return service.logoutCustomer();	
	}
	@GetMapping(value = "/findById/{id}")
	public ResponseStructure<Customer> getCustomerByIdService(@PathVariable int id) {
		return service.getCustomerByIdService(id);	
	}
	@GetMapping(value = "/findByEmail/{email}")
	public ResponseStructure<Customer> getCustomerByEmailService(@PathVariable String email) {
		return service.getCustomerByEmailService(email);
	}
	@PostMapping(value = "/update/{email}")
	public ResponseStructure<List<Customer>> updateCustomerByEmailService(@RequestBody Customer customer) {
		return service.updateCustomerByEmailService(customer);
		
	}
}
