package com.inn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "GROUP_DETAIL_MAPPING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "userGroupDetailMapping")
public class GroupDetailMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "GROUP_ID")
    private String groupId;

    @OneToOne
    @JoinColumn(name = "USER_GROUP_DETAIL_MAPPING_FK", referencedColumnName = "ID")
    @JsonIgnore
    private UserGroupDetailMapping userGroupDetailMapping;
}
