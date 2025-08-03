package com.inn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="GROUP_DETAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetail extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "GROUP_ID")
	private String groupId;

	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;
}
