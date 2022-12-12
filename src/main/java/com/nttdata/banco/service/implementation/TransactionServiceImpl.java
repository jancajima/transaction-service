package com.nttdata.banco.service.implementation;

import com.nttdata.banco.document.Transaction;
import com.nttdata.banco.repository.TransactionRepository;
import com.nttdata.banco.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transaction service implementation.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> register(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Void> delete(String id) {
        return transactionRepository.deleteById(id);
    }

    @Override
    public Mono<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Flux<Transaction> findTransactionsByAccountId(String accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }

    @Override
    public Mono<Map<String, Double>> makeAmountAvgReport(String customerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        YearMonth monthYear = YearMonth.now();
        return transactionRepository.findAllByCustomerId(customerId).filter(x ->
                YearMonth.from(LocalDate.parse(x.getTransactionDate(), formatter)).equals(monthYear)).
                collect(Collectors.groupingBy(Transaction::getAccountId,Collectors.averagingDouble(Transaction::getAccountAmount)));
    }

    @Override
    public Flux<Transaction> findCommissionByAccountId(String accountId, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return transactionRepository.findAllByTypeAndAccountId("commission", accountId)
                .filter(x-> LocalDate.from(LocalDate.parse(x.getTransactionDate(),formatter))
                        .compareTo(LocalDate.from(LocalDate.parse(startDate,formatter))) > 0 &&
                        LocalDate.from(LocalDate.parse(x.getTransactionDate(),formatter))
                                .compareTo(LocalDate.from(LocalDate.parse(endDate,formatter))) < 0);
    }

    @Override
    public Flux<Transaction> findTransactionsByCardId(String cardId) {
        return transactionRepository.findAllByCardId(cardId)
                .sort(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .take(10);
    }

}
