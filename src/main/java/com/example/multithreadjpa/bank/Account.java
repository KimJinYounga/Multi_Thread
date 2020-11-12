package com.example.multithreadjpa.bank;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "account")
@NoArgsConstructor
@DynamicUpdate
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private String name;

    private long balance;

//    @Version // lock 걸때 사용됨.
//    private Integer version;

    public Account(String name) {
        this.name = name;
        this.balance = 0;
    }
}