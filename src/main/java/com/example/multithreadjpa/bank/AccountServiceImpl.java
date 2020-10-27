package com.example.multithreadjpa.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Transactional
    public void deposit(long accountId, long amount) {
        Account account = accountRepository.findByAccountId(accountId);
        long currBalance = account.getBalance();
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + currBalance);
        account.setBalance(currBalance + amount);
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + (currBalance + amount));
        accountRepository.save(account);
    }


}