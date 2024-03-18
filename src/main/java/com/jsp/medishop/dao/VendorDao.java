package com.jsp.medishop.dao;

import java.util.List;

import com.jsp.medishop.dto.Vendor;


/**
 * @author vikas 
 *
 */
public interface VendorDao {

	public Vendor saveVendorDao(Vendor vendor);

	public Vendor getVendorByIdDao(int id);

	public Vendor getVendorByEmailDao(String email);

	public List<Vendor> getVendorsDao();

	public Vendor updateVendorByEmailDao(Vendor vendor);

	public Vendor deleteVendorByEmailDao(String email);
	
	public Vendor vendorVerifyByDao(int id);

}
