package com.trade.repo.model;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Trade")

public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "version")
    private int version;

    @Column(name = "counter_party_id")
    private String counterpartyId;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "maturity_date")
    private String maturityDate;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "is_expired")
    private String isExpired;

    @Column(name = "status")
    private String status;
}


