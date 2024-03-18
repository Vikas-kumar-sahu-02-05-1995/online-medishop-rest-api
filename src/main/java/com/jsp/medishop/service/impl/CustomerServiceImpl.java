package com.jsp.medishop.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.medishop.dao.CustomerDao;
import com.jsp.medishop.dto.Customer;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.CustomerService;
import com.jsp.medishop.verification.EmailPasswordVerification;

import jakarta.servlet.http.HttpSession;

/**
 * @author Vikas
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private HttpSession session;
	@Autowired
	private CustomerDao dao;
	@Autowired
	private EmailPasswordVerification verification;
	@Autowired
	private ResponseStructure<Customer> structure;
	@Autowired
	private ResponseStructure<List<Customer>> structure2;

	@Override
	public ResponseStructure<Customer> saveCustomerService(Customer customer) {
		String email = verification.verifyEmail(customer.getEmail());
		String password = verification.verifyPassword(customer.getPassword());
		if (email != null) {
			if (password != null) {
				int currentYear = LocalDate.now().getYear();
				int CustomerDobYear = customer.getDob().getYear();
				int age = currentYear - CustomerDobYear;
				if(age>=18) {
					dao.saveCustomerDao(customer);
					structure.setData(customer);
					structure.setMsg("Data Inserted!!!!");
					structure.setStatus(HttpStatus.CREATED.value());
				}else {
					structure.setData(null);
					structure.setMsg("you are not eligible your age is less than 18");
					structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				}
			} else {
				structure.setData(customer);
				structure.setMsg("Please check your password!!!!");
				structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			}
		} else {
			structure.setData(customer);
			structure.setMsg("Please check your email!!!!");
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		}
		return structure;
	}

	@Override
	public ResponseStructure<Customer> getCustomerByIdService(int id) {
		Customer customer = dao.getCustomerByIdDao(id);
		if(customer!=null) {
			structure.setStatus(HttpStatus.ACCEPTED.value());
			structure.setMsg("Data-stored");
			structure.setData(customer);
		}else {
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure.setMsg("error-check your email");
			structure.setData(null);
		}
		
		return structure;
	}

	@Override
	public ResponseStructure<Customer> getCustomerByEmailService(String email) {
		Customer customer = dao.getCustomerByEmailDao(email);
		if(customer!=null) {
			structure.setStatus(HttpStatus.ACCEPTED.value());
			structure.setMsg("Data-stored");
			structure.setData(customer);
		}else {
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure.setMsg("error-check your email");
			structure.setData(null);
		}
		return structure;
	}

	@Override
	public List<Customer> getCustomersService() {
        List<Customer> customer = dao.getCustomersDao();
        if(!customer.isEmpty()) {
			structure2.setStatus(HttpStatus.ACCEPTED.value());
			structure2.setMsg("Data-stored");
			structure2.setData(customer);
		}else {
			structure2.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure2.setMsg("error-check your email");
			structure2.setData(null);
		}
		return null;
	}

	@Override
	public ResponseStructure<List<Customer>> updateCustomerByEmailService(Customer customer) {
            Customer customers = dao.updateCustomerByEmailDao(customer);
            if(customers!=null) {
    			structure.setStatus(HttpStatus.ACCEPTED.value());
    			structure.setMsg("Data-stored");
    			structure.setData(customer);
    		}else {
    			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
    			structure.setMsg("error-check your email");
    			structure.setData(null);
    		}
		return null;
	}

	@Override
	public ResponseStructure<Customer> deleteCustomerByEmailService(String email) {
		return null;
	}

	@Override
	public ResponseStructure<Customer> loginCustomerByEmailAndPasswordService(String email, String password) {
		 Customer customer = dao.getCustomerByEmailDao(email);
		 if(customer!=null) {
			 if(customer.getPassword().equals(customer)) {
			 session.setAttribute("CustomerEmail", customer);
			 customer.setPassword("*****");
			 structure.setStatus(HttpStatus.ACCEPTED.value());
			 structure.setMsg("login success"); 
			 structure.setData(null);
			 }else {
				 structure.setStatus(HttpStatus.ACCEPTED.value());
				 structure.setMsg("password invalid");
				 structure.setData(customer);
			 }
		 }else {
			 structure.setStatus(HttpStatus.NOT_FOUND.value());
			 structure.setMsg("invalid password invalid");
			 customer.setPassword("****");
			 structure.setData(customer);
		 }
		return structure;
	}

	@Override
	public ResponseEntity<String> logoutCustomer() {
		if(session.getAttribute("CustomerEmail")!=null) {
			session.invalidate();
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
