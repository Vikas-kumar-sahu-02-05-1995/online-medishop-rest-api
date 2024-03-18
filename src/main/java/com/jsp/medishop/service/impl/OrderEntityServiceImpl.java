package com.jsp.medishop.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.medishop.dao.CustomerDao;
import com.jsp.medishop.dao.MedicineDao;
import com.jsp.medishop.dao.OrderEntityDao;
import com.jsp.medishop.dto.Customer;
import com.jsp.medishop.dto.Medicine;
import com.jsp.medishop.dto.OrderEntity;
import com.jsp.medishop.response.ResponseStructure;
import com.jsp.medishop.service.OrderEntityService;

import jakarta.servlet.http.HttpSession;

@Service
public class OrderEntityServiceImpl implements OrderEntityService{

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private OrderEntityDao entityDao;
	
	@Autowired
	private MedicineDao medicineDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private ResponseStructure<OrderEntity> responseStructure;
	
	@Override
	public ResponseStructure<OrderEntity> saveOrderEntitiyService(OrderEntity entitiy, int medicineId) {		
		String customerEmail = (String) httpSession.getAttribute("customerEmail");
		if(customerEmail!=null) {
			/*
			 * it will generate random 10 digits orderId
			 */
			long orderId = (long) (Math.floor(Math.random()*9000000000L) +1000000000L);
			Medicine medicine = medicineDao.getMedicineByIdDao(medicineId);
			Customer customer = customerDao.getCustomerByEmailDao(customerEmail);
			entitiy.setCustomer(customer);
			entitiy.setMedicine(medicine);
			entitiy.setOrderId(orderId);
			entitiy.setOrderDate(LocalDate.now());
			/**
			 * this line will calculate final amount
			 */
			entitiy.setTotalAmmount(medicine.getPrice()*entitiy.getQuantity());
			/**
			 * date calculation 
			 */
			entitiy.setEstimatedDeliveryDate(LocalDate.now().plusDays(4));
			if(entitiy.getCustomerDeliveryDate()==null) {
				entitiy.setCustomerDeliveryDate(entitiy.getEstimatedDeliveryDate());
			}
			entityDao.saveOrderEntityDao(entitiy);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMsg("Order placed successful");
			responseStructure.setData(entitiy);
		}else {
			responseStructure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure.setMsg("Please login and then order");
			responseStructure.setData(null);
		}
		return responseStructure;
	}
	@Override
	public ResponseStructure<OrderEntity> getOrderEntitiyByIdService(int orderId) {
               OrderEntity entity = entityDao.getOrderEntityByIdDao(orderId);
         if(entity!=null) {
        	 responseStructure.setStatus(HttpStatus.ACCEPTED.value());
 			responseStructure.setMsg("Data-stored");
 			responseStructure.setData(entity);
         }else {
        	 responseStructure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
  			responseStructure.setMsg("Data-not found");
  			responseStructure.setData(null);
         }
		return responseStructure;
	}

}
