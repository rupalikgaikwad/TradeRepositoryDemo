package com.trade.repo.controller;
import com.trade.repo.repository.TradeService;
import org.springframework.web.bind.annotation.PostMapping;
import com.trade.repo.repository.TradeRepository;
import com.trade.repo.exception.ResourceNotFoundException;
import com.trade.repo.model.Trade;
import com.trade.repo.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/trade")
public class TradeController {

    private final TradeRepository tradeRepository;
    private final TradeService tradeService;

    public TradeController(TradeRepository tradeRepository, TradeService tradeService) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        tradeService.createTrades(List.of(trade));
        return ResponseEntity.status(HttpStatus.CREATED).body(trade);
    }

    @PostMapping("/trades")
    public List<Trade> createTrades(@RequestBody List<Trade> trades) {
        tradeService.createTrades(trades);
        return trades;
    }

    @GetMapping("{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id:" + id));
        return ResponseEntity.ok(trade);
    }

    @PutMapping("{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable long id, @RequestBody Trade tradeDetails) {
        Trade updateTrade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id: " + id));

        if (updateTrade.getVersion() > tradeDetails.getVersion()) {
            throw new IllegalArgumentException("Lower version received");
        }

        updateTrade.setTradeId(tradeDetails.getTradeId());
        updateTrade.setVersion(tradeDetails.getVersion());
        updateTrade.setBookId(tradeDetails.getBookId());
        updateTrade.setCounterpartyId(tradeDetails.getCounterpartyId());
        updateTrade.setCreatedDate(tradeDetails.getCreatedDate());
        updateTrade.setMaturityDate(tradeDetails.getMaturityDate());
        updateTrade.setIsExpired(tradeDetails.getIsExpired());

        tradeService.createTrades(List.of(updateTrade));

        return ResponseEntity.ok(updateTrade);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTrade(@PathVariable long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id: " + id));
        tradeRepository.delete(trade);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
