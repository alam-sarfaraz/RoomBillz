package com.inn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "LINE_ITEM_DETAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDetail{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "ITEM_PRICE")
	private Double itemPrice;

	@Column(name = "UNIT_PRICE")
	private Double unitPrice;

	@Column(name = "QUANTITY")
	private String quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private PurchaseOrderDetail purchaseOrder;

}
