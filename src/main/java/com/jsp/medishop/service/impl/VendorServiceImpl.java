package com.jsp.medishop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.medishop.dao.VendorDao;
import com.jsp.medishop.dto.Vendor;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.VendorService;
import com.jsp.medishop.verification.EmailPasswordVerification;

import jakarta.servlet.http.HttpSession;

/**
 * @author vikas
 */
@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private HttpSession session;
	@Autowired
	private VendorDao dao;
	@Autowired
	private EmailPasswordVerification verification;
	@Autowired
	private ResponseStructure<Vendor> structure;
	@Autowired
	private ResponseStructure<List<Vendor>> structure2;

	@Override
	public ResponseStructure<Vendor> saveVendorService(Vendor vendor) {
		String email = verification.verifyEmail(vendor.getEmail());
		String password = verification.verifyPassword(vendor.getPassword());
		if (email != null) {
			if (password != null) {
				dao.saveVendorDao(vendor);
				structure.setData(vendor);
				structure.setMsg("Data Inserted!!!");
				structure.setStatus(HttpStatus.CREATED.value());
			} else {
				structure.setData(vendor);
				structure.setMsg("Please create a vaild password!!!");
				structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			}
		} else {
			structure.setData(vendor);
			structure.setMsg("Please enter a vaild email!!!");
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		}
		return structure;

	}

	@Override
	public ResponseStructure<Vendor> getVendorByIdService(int id) {
		Vendor vendor = dao.getVendorByIdDao(id);
		if(vendor!=null) {
			structure.setStatus(HttpStatus.ACCEPTED.value());
			structure.setMsg("Data-stored");
			structure.setData(vendor);
		}else {
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure.setMsg("error-check your email");
			structure.setData(null);
		}
		return structure;
	}

	@Override
	public ResponseStructure<Vendor> getVendorByEmailService(String email) {
		Vendor vendor = dao.getVendorByEmailDao(email);
		if(vendor!=null) {
			structure.setData(vendor);
			structure.setMsg("vendor found");
			structure.setStatus(HttpStatus.FOUND.value());
		}else {
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure.setMsg("error-check your email");
			structure.setData(null);
		}
		return structure;
	}

	@Override
	public List<Vendor> getVendorsService() {
		List<Vendor> vendor = dao.getVendorsDao();
		if(vendor!=null) {
			structure2.setData(vendor);
			structure2.setMsg("vendor found");
			structure2.setStatus(HttpStatus.FOUND.value());
		}else {
			structure2.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure2.setMsg("error-check your email");
			structure2.setData(null);
		}
		return null;
	}

	@Override
	public ResponseStructure<List<Vendor>> updateVendorByEmailService(Vendor vendor) {
        Vendor vendor2 = dao.updateVendorByEmailDao(vendor);
        if(vendor2!=null){
        	structure.setData(vendor);
			structure.setMsg("vendor found");
			structure.setStatus(HttpStatus.FOUND.value());
		}else {
			structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			structure.setMsg("error-check your email");
			structure.setData(null);
		}
		return null;
	}

	@Override
	public ResponseStructure<Vendor> deleteVendorByEmailService(String email) {
	   Vendor vendor = dao.deleteVendorByEmailDao(email);
	   if(vendor!=null) {
		   structure.setData(null);
			structure.setMsg("vendor deleted");
			structure.setStatus(HttpStatus.FOUND.value());
	   }
		return structure;
	}

	@Override
	public ResponseStructure<Vendor> loginVendorByEmailAndPasswordService(String email, String password) {
		Vendor vendor = dao.getVendorByEmailDao(email);
		if(vendor!=null) {
			if((vendor.getPassword().equals(password)) && (vendor.getVendorStatus().equalsIgnoreCase("active"))) {
				session.setAttribute("vendorEmail", vendor);
				vendor.setPassword("******");
				structure.setStatus(HttpStatus.OK.value());
				structure.setMsg("vendor-----login----successfully");
				structure.setData(vendor);
			}else {
				structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				structure.setMsg("password is wrong...or you are not verified vendor please contact with admin");
				structure.setData(vendor);
			}
		}else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMsg("vendor email is wrong");
			vendor.setPassword("*****");
			vendor.setEmail(email);
			structure.setData(vendor);
		}
		return structure ;
	}

	@Override
	public ResponseEntity<String> logoutVendor() {
		if(session.getAttribute("vendorEmail")!=null) {
			session.invalidate();
			return new ResponseEntity<String>("vendor logout successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("please login then logout successful", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> vendorVerifyByIdService(int id) {
		
		String adminEmail = (String) session.getAttribute("adminEmail");
		
		if(adminEmail!=null){
			dao.vendorVerifyByDao(id);
			return new ResponseEntity<String>("vendor verify success", HttpStatus.OK );
		 }else {
			return new ResponseEntity<String>("Please Login with admin and then verify", HttpStatus.NOT_ACCEPTABLE);
		}
	  }

}
