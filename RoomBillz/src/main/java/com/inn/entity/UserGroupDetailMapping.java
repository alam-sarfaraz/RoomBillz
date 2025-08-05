package com.inn.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_GROUP_DETAIL_MAPPING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDetailMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @OneToOne(mappedBy = "userGroupDetailMapping", cascade = CascadeType.ALL)
    private GroupDetailMapping groupDetailMapping;
}
