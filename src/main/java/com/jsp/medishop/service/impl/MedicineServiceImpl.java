package com.jsp.medishop.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.medishop.dao.MedicineDao;
import com.jsp.medishop.dao.VendorDao;
import com.jsp.medishop.dto.Medicine;
import com.jsp.medishop.dto.Vendor;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.MedicineService;

import jakarta.servlet.http.HttpSession;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	private VendorDao vendorDao;
	@Autowired
	private MedicineDao dao;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private ResponseStructure<Medicine> responseStructure;
	
	@Autowired
	private ResponseStructure<List<Medicine>> responseStructure2;

	@Override
	public ResponseStructure<Medicine> saveMedicineService(Medicine medicine) {
		String email =(String) httpSession.getAttribute("vendorEmail");
		if(email!=null) {
			Vendor vendor = vendorDao.getVendorByEmailDao(email);  
			medicine.setVendors(new ArrayList<Vendor>(Arrays.asList(vendor)));
		    Medicine medicine2=dao.saveMedicineDao(medicine);
		       if(medicine2!=null) {
			    responseStructure.setStatus(HttpStatus.ACCEPTED.value());
			    responseStructure.setMsg("medicine addedd");
			    responseStructure.setData(medicine2);
		      }else {
			    responseStructure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			    responseStructure.setMsg("Data is not stored check your code");
			    responseStructure.setData(null);
		    }
		}else {
			 responseStructure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			 responseStructure.setMsg("please login with vendor and then add medicine....");
			 responseStructure.setData(null);
		}
		 return responseStructure;
	}

	@Override
	public ResponseStructure<List<Medicine>> getAllMedicineService() {
		     List<Medicine> medicines=dao.getAllMedicineDao();
		     if(!medicines.isEmpty()) {
				 responseStructure2.setStatus(HttpStatus.OK.value());
				 responseStructure2.setMsg("Data----Found----");
				 responseStructure2.setData(medicines);
			 }else {
				 responseStructure2.setStatus(HttpStatus.NOT_FOUND.value());
				 responseStructure2.setMsg("Data is not found might be table is empty or check your code");
				 responseStructure2.setData(medicines);
			 }
			 return responseStructure2;
	}

	@Override
	public ResponseEntity<String> verifyMedicineStatusByAdminSetrvice(int medicineId, int vendorId) {
		String adminEmail = (String) httpSession.getAttribute("adminEmail");
		
		if(adminEmail!= null) {
			
			Vendor vendor = vendorDao.getVendorByIdDao(vendorId);
			
			if(vendor!=null) {
				List<Medicine> medicines = vendor.getMedicines();
				
	            if(!medicines.isEmpty()) {
	            	for(Medicine medicine1 : medicines) {
	            		if(medicine1.getId() == medicineId) {
	            			medicine1.setMedicineStatus("active");
	            		    boolean  b= dao.verifyMedicineStatusByAdminDao(medicine1);
	            			
	            			return (b)?new ResponseEntity<String>("medicine1--verified",HttpStatusCode.valueOf(200)):new ResponseEntity<String>("not--verified", HttpStatusCode.valueOf(404));
	            		}
	            	}
	            }
			}
		}
		return new ResponseEntity<String>("please login as admin then verified",HttpStatusCode.valueOf(200));
	}

	@Override
	public ResponseStructure<List<Medicine>> getAllMedicineByNameService(String name) {
		String customerEmail = (String) httpSession.getAttribute("customerEmail");
		String vendorEmail = (String) httpSession.getAttribute("vendorEmail");
		String adminEmail = (String) httpSession.getAttribute("adminEmail");
		
		if((customerEmail!=null) || (vendorEmail!=null)||(adminEmail!=null)) {
			
			List<Medicine> medicines = dao.getAllMedicineByNameDao(name);
			  if(!medicines.isEmpty()) {
			  responseStructure2.setMsg("medicines are not available");
			  responseStructure2.setStatus(HttpStatus.FOUND.value());
			  responseStructure2.setData(medicines);
		     }else {
			  responseStructure2.setMsg("please login and search");
			  responseStructure2.setStatus(HttpStatus.NOT_FOUND.value());
			  responseStructure2.setData(medicines);
		    }
		}else {
			responseStructure2.setMsg("please login and search");
			responseStructure2.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure2.setData(null);
		}
		return responseStructure2;
 }


}
