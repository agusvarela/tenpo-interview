package com.tenpo.interview.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

/**
 * This entity represents the user information for tha data base table.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @JsonIgnore
    private long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String password;

}
