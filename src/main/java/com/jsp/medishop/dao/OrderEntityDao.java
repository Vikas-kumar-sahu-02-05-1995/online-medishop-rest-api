package com.jsp.medishop.dao;

import com.jsp.medishop.dto.OrderEntity;
/**
 * @author vikas
 */
public interface OrderEntityDao {

	public OrderEntity saveOrderEntityDao(OrderEntity entity);
	
	public OrderEntity getOrderEntityByIdDao(long orderId);
}
