package com.jsp.medishop.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderEntity {

	@Id
	private long orderId;
	private LocalDate estimatedDeliveryDate;
	private LocalDate customerDeliveryDate;
	private LocalDate orderDate;
	private String orderStatus="pending";
	private double totalAmmount;
	private String deliveryAddress;
	private String paymentMode;
	private int quantity;
	
	@OneToOne
	private Vendor vendor;
	@OneToOne
	private Medicine medicine;
	@OneToOne
	private Customer customer;
}
