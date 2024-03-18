package com.jsp.medishop.service;

import org.springframework.http.ResponseEntity;

import com.jsp.medishop.dto.Admin;
import com.jsp.medishop.dto.Vendor;
import com.jsp.medishop.response.ResponseStructure;

public interface AdminService {

	public ResponseStructure<Admin> loginAdminByEmailAndPassword(Admin admin);
	
	public ResponseEntity<String> logoutAdmin();

    
	
}
