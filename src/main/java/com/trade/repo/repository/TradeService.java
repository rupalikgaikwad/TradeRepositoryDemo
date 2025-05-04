package com.trade.repo.repository;

import com.trade.repo.model.Trade;
import com.trade.repo.model.TradeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    private static final Logger log = LogManager.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;
    private final TradeValidator tradeValidator;

    public TradeService(TradeRepository tradeRepository, TradeValidator tradeValidator) {
        this.tradeRepository = tradeRepository;
        this.tradeValidator = tradeValidator;
    }

    public void createTrades(List<Trade> trades) {
        List<String> errorMessages = new ArrayList<>();
        try {
            for (Trade trade : trades) {
                TradeValidator validated = tradeValidator.validate(trade);
                if ("Awaiting Replication".equals(validated.getStatus())) {
                    tradeRepository.save(trade);
                } else {
                    log.info(String.join("\n", validated.getErrorMessages()));
                    errorMessages.addAll(validated.getErrorMessages());
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errorMessages));
        }
    }
}
