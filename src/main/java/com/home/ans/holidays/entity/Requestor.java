package com.home.ans.holidays.entity;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Data
public class Requestor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NaturalId
    @NonNull
    @NotBlank(message = "login cannot be null nor empty and must be a string value of length between 5 and 20.")
    @Column(unique = true)
    @Size(min = 5, max = 20)
    private String login;

    @OneToMany(mappedBy = "requestor", cascade = CascadeType.ALL, orphanRemoval = true)
    private SearchCriteria searchCriteria;



}
