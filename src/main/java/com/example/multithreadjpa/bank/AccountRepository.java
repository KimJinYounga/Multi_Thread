package com.example.multithreadjpa.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT) // 조회기능에 락거는 방법은 좋지 않은듯함.
    public Account findByAccountId(long accountId);

    @Modifying
//    @Query("update account a set a.balance = a.balance + :amount, a.name = :name where a.accountId = :id")
//    int bulkCount(@Param("amount") long amount, @Param("name")String name,@Param("id") long id);
    @Query("update account a set a.balance = a.balance + :amount where a.accountId = :id")
    int bulkCount(@Param("amount") long amount, @Param("id") long id);
}