package com.trade.repo.repository;

import com.trade.repo.model.Trade;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository  extends JpaRepository<Trade, Long> {
}

/*@NoArgsConstructor
@AllArgsConstructor

public class TradeRepository {
    public Trade save(Trade tradeDetails){
        System.out.println("Trade object save successful");
        return tradeDetails;
    }

    public Trade findById(long id){
        System.out.println("Trade object save successful");
        return new Trade();
    }
}*/