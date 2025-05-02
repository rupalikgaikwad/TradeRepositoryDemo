package com.trade.repo.controller;

import com.trade.repo.repository.TradeRepository;
import com.trade.repo.exception.ResourceNotFoundException;
import com.trade.repo.model.Trade;
import com.trade.repo.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    @Autowired
    private TradeRepository tradeRepository;

    /*@GetMapping
    public List<Trade> getAllTrades(){
        return tradeRepository.findAll();
    }*/

    // build create employee REST API
    @PostMapping
    public Trade createTrade(@RequestBody Trade employee) {
        return tradeRepository.save(employee);
    }

    // build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable  long id){
        Trade employee = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id:" + id));
        return ResponseEntity.ok(employee);
    }

    // build update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable long id,@RequestBody Trade tradeDetails) {
        Trade updateTrade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id: " + id));

        updateTrade.setTradeId(tradeDetails.getTradeId());
        updateTrade.setVersion(tradeDetails.getVersion());
        updateTrade.setBookId(tradeDetails.getBookId());
        updateTrade.setCounterpartyId(tradeDetails.getCounterpartyId());
        updateTrade.setCreatedDate(tradeDetails.getCreatedDate());
        updateTrade.setMaturityDate(tradeDetails.getMaturityDate());
        updateTrade.setIsExpired(tradeDetails.getIsExpired());

        tradeRepository.save(updateTrade);

        return ResponseEntity.ok(updateTrade);
    }

    // build delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTrade(@PathVariable long id){

        Trade employee = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not exist with id: " + id));

        tradeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
