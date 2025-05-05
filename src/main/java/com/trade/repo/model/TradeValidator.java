package com.trade.repo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Component
public class TradeValidator {

    private String status;
    private List<String> errorMessages = new ArrayList<>();


    public TradeValidator validate(Trade trade) {
        this.setStatus("Awaiting Replication");

        //Check maturity date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate maturityDate = null;
        try {
            maturityDate = LocalDate.parse(trade.getMaturityDate(), formatter);
        } catch (DateTimeParseException e) {
            this.setStatus("Invalid");
            this.errorMessages.add(trade.getTradeId() + ": " + " Maturity date format is incorrect, it should be in the format of dd/mm/yyyy");
        }

        //Check creation date format
        LocalDate createdDate = null;
        try {
            createdDate = LocalDate.parse(trade.getCreatedDate(), formatter);
        } catch (DateTimeParseException e) {
            this.setStatus("Invalid");
            this.errorMessages.add(trade.getTradeId() + ": " + " created date format is incorrect, it should be in the format of dd/mm/yyyy");
        }

        //Check if creation date is <today
        LocalDate today = LocalDate.now();
        assert createdDate != null;
        if (createdDate.isBefore(today)) {
            this.setStatus("Invalid");
            this.errorMessages.add(trade.getTradeId() + ": " + " creation date is before today");
        }

        //Set isExpired yes if maturity date > today
        assert maturityDate != null;
        if (maturityDate.isBefore(today)) {
            this.setStatus("Invalid");
            trade.setIsExpired("Y");
        }



        return this;
    }
}
