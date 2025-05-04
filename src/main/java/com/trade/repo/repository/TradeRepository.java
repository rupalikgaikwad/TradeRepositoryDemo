package com.trade.repo.repository;

import com.trade.repo.model.Trade;
import com.trade.repo.model.TradeValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface TradeRepository  extends JpaRepository<Trade, Long> {
}


