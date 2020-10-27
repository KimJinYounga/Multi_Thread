package com.example.multithreadjpa.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;


    @Transactional
    public long deposit(long accountId, long amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(null);
        long currBalance = account.getBalance();
        account.setBalance(currBalance + amount);
        accountRepository.save(account);
        return currBalance + amount;
    }


}