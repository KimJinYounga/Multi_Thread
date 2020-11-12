package com.example.multithreadjpa.bank;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private static final ExecutorService service =
            Executors.newFixedThreadPool(10);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private long accountId;

    @Before
    public void setUp() {
        Account account = new Account("신한 S20");
        account = accountRepository.save(account);
        accountId = account.getAccountId();

    }

    @Test
    public void SimultaneousDepositPassWithNoRaceCondition() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        service.execute(() -> {
            accountService.deposit(accountId, 10, "first");
            latch.countDown();
        });
        service.execute(() -> {
            accountService.deposit(accountId, 30, "second");
            latch.countDown();
        });

        latch.await();
        Account richAccount = accountRepository.findById(accountId).orElseThrow(null);
        System.out.println("result_balance : " + richAccount.getBalance());
        System.out.println("result_name : " + richAccount.getName());
        assertThat(richAccount.getBalance()).isEqualTo(40);
    }

}