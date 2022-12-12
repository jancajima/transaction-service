package com.nttdata.banco.repository;

import com.nttdata.banco.document.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Transaction Repository.
 */
@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Flux<Transaction> findAllByAccountId(String accountId);

    Flux<Transaction> findAllByCustomerId(String customerId);

    Flux<Transaction> findAllByCardId(String cardId);

    Flux<Transaction> findAllByTypeAndAccountId(String type, String accountId);
}
