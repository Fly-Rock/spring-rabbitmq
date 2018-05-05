
package com.oaup.support.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDescription {

	
	/**
	 *商品id
	 */
	private Integer productId;
	
	/**
	 *商品名称
	 */
	private String content;
	
	/**
	 *更新时间
	 */
	private Date updateTime;
	
	/**
	 *创建时间
	 */
	private Date createTime;

	private String contentStyle;
}