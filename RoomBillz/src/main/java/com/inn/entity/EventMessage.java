package com.inn.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EVENT_MESSAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessage extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PURCHASE_ID")
	private String purchaseId;

	@Column(name = "EVENT_TYPE")
	private String eventType;

	@Lob
	@Column(name = "MESSAGE", columnDefinition = "TEXT")
	private String message;

	@Column(name = "SOURCE_SERVICE")
	private String sourceService;

	@Column(name = "TIME_STAMP")
	private String timestamp;

	@Column(name = "STATUS")
	private String status;

}