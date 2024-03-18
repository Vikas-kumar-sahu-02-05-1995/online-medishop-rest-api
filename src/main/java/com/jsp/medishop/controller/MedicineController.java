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
import com.jsp.medishop.dto.Medicine;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.MedicineService;
@RestController
@RequestMapping(value ="/medicine" )
public class MedicineController {

	@Autowired
	private MedicineService medicineservice;
	
	@PostMapping(value = "/savemedicine")
	public ResponseStructure<Medicine> saveMedicineController(@RequestBody Medicine medicine) {
		return medicineservice.saveMedicineService(medicine);
		
	}
   @GetMapping(value = "/getAlledicineByName/{name}")
	public ResponseStructure<List<Medicine>> getAllMedicineByNameController(@PathVariable String name) {
	   return medicineservice.getAllMedicineByNameService(name);
	}
}
