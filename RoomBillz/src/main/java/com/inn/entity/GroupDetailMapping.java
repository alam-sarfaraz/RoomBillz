package com.inn.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GROUP_DETAIL_MAPPING")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private UserGroupDetailMapping userGroupDetailMapping;
}
