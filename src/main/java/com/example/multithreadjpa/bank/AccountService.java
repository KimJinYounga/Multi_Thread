package com.example.multithreadjpa.bank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AccountService {

    @Transactional
    public long deposit(long accountId, long amount);

//    @Transactional
//    public Account findById(long accountId);
}