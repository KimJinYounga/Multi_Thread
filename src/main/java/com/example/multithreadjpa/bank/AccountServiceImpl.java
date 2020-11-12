package com.example.multithreadjpa.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Transactional
    public void deposit2(long accountId, long amount, String changeName) {
        Account account = accountRepository.findByAccountId(accountId);
        long currBalance = account.getBalance();
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + currBalance + "currName = "+account.getName());
        account.setName(changeName);
//        account.setBalance(currBalance + amount);
        accountRepository.bulkCount(amount, accountId);
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + (currBalance + amount) + "currName = testName");
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(long accountId, long amount, String changeName) {
        Account account = accountRepository.findByAccountId(accountId);
        long currBalance = account.getBalance();
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + currBalance + "currName = "+account.getName());
        account.setName(changeName);
//        account.setBalance(currBalance + amount);
        accountRepository.bulkCount(amount, accountId);
        System.out.println("thread = " + Thread.currentThread().getName() + ", " + "currBalance = " + (currBalance + amount) + "currName = testName");
        accountRepository.save(account);
    }

}