package com.ankit.studentmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)

private Integer id;
private int age;
private String name;
private String email;
}
