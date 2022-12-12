package com.nttdata.banco.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Transaction document.
 */
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Document (collection = "transactions")
public class Transaction {
    @Id
    public String id;

    //Date of the transaction
    private String transactionDate;
    //Amount of the transaction
    private float amount;
    //Transaction's type: Deposit or WithDrawl
    private String type;
    //Associated customer's ID
    private String customerId;
    //Associated account's ID
    private String accountId;
    //Associated account's amount after transaction
    private float accountAmount;
    //Associated card's ID
    private String cardId;

}
