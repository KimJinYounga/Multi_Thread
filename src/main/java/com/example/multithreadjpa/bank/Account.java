package com.example.multithreadjpa.bank;

import lombok.*;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "account")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private String name;

    private long balance;


    public Account(String name) {
        this.name = name;
        this.balance = 0;
    }
}