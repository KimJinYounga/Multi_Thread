package com.example.multithreadjpa.bank;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private static final ExecutorService service =
            Executors.newFixedThreadPool(100);

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
        CountDownLatch latch = new CountDownLatch(200);

        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                accountService.deposit(accountId, 10);
                latch.countDown();
            });
        }

        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                accountService.deposit(accountId, 5);
                latch.countDown();
            });
        }
        latch.await();
        Account richAccount = accountRepository.findById(accountId).orElseThrow(null);
        System.out.println("result : " + richAccount.getBalance());
        assertThat(richAccount.getBalance()).isEqualTo(100*10+5*100);
    }

}