package com.inn.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedBy
	@Column(name="CREATED_BY",updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(name="CREATED_AT",updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedBy
	@Column(name="MODIFIED_BY",insertable =  false)
	private String modifiedBy;

	@LastModifiedDate
	@Column(name="MODIFIED_AT",insertable =  false)
	private LocalDateTime modifiedAt;

}
