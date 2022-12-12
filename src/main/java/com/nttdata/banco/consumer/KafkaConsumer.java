package com.nttdata.banco.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.banco.document.Transaction;
import com.nttdata.banco.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    public KafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payCredit" , groupId = "default")
    public void payCredit(String message) throws JsonProcessingException {
        Transaction transaction = objectMapper.readValue(message, Transaction.class);
        logger.info("consumer message getAccountId" + transaction.getAccountId());
        transactionRepository.save(transaction).subscribe(t -> {
            logger.info("transaction created Id" + t.getId());
        });
    }

}