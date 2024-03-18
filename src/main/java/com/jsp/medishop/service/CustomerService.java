package com.jsp.medishop.service;

/**
 * @author Vikas
 */

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.medishop.dto.Customer;
import com.jsp.medishop.response.ResponseStructure;

public interface CustomerService {

	public ResponseStructure<Customer> saveCustomerService(Customer customer);

	public ResponseStructure<Customer> getCustomerByIdService(int id);

	public ResponseStructure<Customer> getCustomerByEmailService(String email);

	public List<Customer> getCustomersService();

	public ResponseStructure<List<Customer>> updateCustomerByEmailService(Customer customer);

	public ResponseStructure<Customer> deleteCustomerByEmailService(String email);
	
	public ResponseStructure<Customer> loginCustomerByEmailAndPasswordService(String email, String password);
	
	public ResponseEntity<String> logoutCustomer();
	
}
