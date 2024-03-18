package com.jsp.medishop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.medishop.dto.Vendor;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.VendorService;

/**
 * @author vikas
 */
@RestController
@RequestMapping(value = "/vendor")
public class VendorController {

	@Autowired
	private VendorService service;

	@PostMapping(value = "/save")
	public ResponseStructure<Vendor> saveVendorController(@RequestBody Vendor vendor) {
		return service.saveVendorService(vendor);
	}
	@GetMapping(value = "/loginVendor/{email}/{password}")
	public ResponseStructure<Vendor> loginVendorByEmailAndPasswordService(@PathVariable String email,@PathVariable String password) {
		return service.loginVendorByEmailAndPasswordService(email, password);
	}
	@GetMapping(value = "/logoutVendor")
	public ResponseEntity<String> logoutVendor() {
		return service.logoutVendor();
	}
	@GetMapping(value = "/getByid/{id}")
	public ResponseStructure<Vendor> getVendorByIdController(@PathVariable int id) {
		return service.getVendorByIdService(id);
	}
}
