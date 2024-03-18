package com.jsp.medishop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jsp.medishop.dao.AdminDao;
import com.jsp.medishop.dto.Admin;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.AdminService;
import jakarta.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private HttpSession httpSession;
	@Autowired
	private ResponseStructure<Admin> rs;
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public ResponseStructure<Admin> loginAdminByEmailAndPassword(Admin admin) {
		Admin admin2 = adminDao.loginAdminByEmailAndPassword(admin);
		if(admin2!=null) {
			
			if(admin2.getPassword().equals(admin.getPassword())){
				httpSession.setAttribute("adminEmail", admin2);
				rs.setStatus(HttpStatus.OK.value());
				admin.setPassword("******");
				rs.setMsg("Admin login success---");
				rs.setData(admin2);	
			}else {
				rs.setStatus(HttpStatus.NOT_FOUND.value());
				rs.setMsg("Invalid----Password----Please---check");
				rs.setData(admin);
			}
		}else {
			rs.setStatus(HttpStatus.NOT_FOUND.value());
			rs.setMsg("inavlid email and pass");
			admin.setPassword("******");
			rs.setData(admin);
		}
		return rs;
	}
	@Override
	public ResponseEntity<String> logoutAdmin() {
		if(httpSession.getAttribute("adminEmail")!=null) {
			httpSession.invalidate();
			return new ResponseEntity<String>("Log-out--successful",HttpStatus.OK);
		}else {
		return new ResponseEntity<String>("first login then logout",HttpStatus.OK);
		}
	}
}
