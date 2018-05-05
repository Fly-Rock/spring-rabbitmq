
package com.oaup.support.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ProductUnitPrice {

	
	/**
	 *主键、自增
	 */
	private Integer unitPriceId;
	
	/**
	 *价格单位名称
	 */
	private String unitName;
	
	/**
	 *1 可用 0  删除
	 */
	private Byte status;
	
	/**
	 *创建用户ID
	 */
	private Long createUserId;
	
	/**
	 *更新时间
	 */
	private Date updateTime;
	
	/**
	 *创建时间
	 */
	private Date createTime;
}