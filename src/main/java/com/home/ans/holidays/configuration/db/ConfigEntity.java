package com.home.ans.holidays.configuration.db;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
@Data
@Builder
@AllArgsConstructor
public class ConfigEntity {

    @Id
    private String name;

    @Column(nullable = false)
    private String value;

    private String description;

}
