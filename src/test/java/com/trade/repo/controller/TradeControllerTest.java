package com.trade.repo.controller;

import com.trade.repo.model.Trade;
import com.trade.repo.repository.TradeService;
import com.trade.repo.repository.TradeRepository;
import com.trade.repo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TradeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Set up MockMvc for testing the controller
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    // Test for creating a single trade
    @Test
    void testCreateTrade() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId("T1");
        trade.setBookId("1");
        trade.setCounterpartyId("1");
        trade.setCreatedDate("04/05/2025");
        trade.setMaturityDate("05/05/2025");
        trade.setIsExpired("N");

        // Mock the createTrades method in TradeService
        doNothing().when(tradeService).createTrades(any());

        // Mock the request and verify the response
        mockMvc.perform(post("/api/v1/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeId\":\"T1\", \"bookId\":\"1\", \"counterpartyId\":\"1\", \"createdDate\":\"04/05/2025\", \"maturityDate\":\"04/05/2025\", \"isExpired\":\"N\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tradeId").value("T1"));
    }

    // Test for creating multiple trades
    @Test
    void testCreateTrades() throws Exception {
        Trade trade1 = new Trade();
        trade1.setTradeId("T1");
        trade1.setBookId("1");
        trade1.setCounterpartyId("1");
        trade1.setCreatedDate("04/05/2025");
        trade1.setMaturityDate("05/05/2025");
        trade1.setIsExpired("N");

        Trade trade2 = new Trade();
        trade2.setTradeId("T2");
        trade2.setBookId("2");
        trade2.setCounterpartyId("2");
        trade2.setCreatedDate("04/05/2025");
        trade2.setMaturityDate("05/05/2025");
        trade2.setIsExpired("N");

        // Mock the createTrades method in TradeService
        doNothing().when(tradeService).createTrades(any());

        // Mock the request and verify the response
        mockMvc.perform(post("/api/v1/trade/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"tradeId\":\"T1\", \"bookId\":\"1\", \"counterpartyId\":\"1\", \"createdDate\":\"04/05/2025\", \"maturityDate\":\"04/05/2025\", \"isExpired\":\"N\"}, " +
                                "{\"tradeId\":\"T2\", \"bookId\":\"1\", \"counterpartyId\":\"1\", \"createdDate\":\"04/05/2025\", \"maturityDate\":\"04/05/2025\", \"isExpired\":\"N\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value("T1"))
                .andExpect(jsonPath("$[1].tradeId").value("T2"));
    }

    // Test for getting trade by id
    @Test
    void testGetTradeById() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId("T1");
        trade.setBookId("1");
        trade.setCounterpartyId("1");
        trade.setCreatedDate("04/05/2025");
        trade.setMaturityDate("05/05/2025");
        trade.setIsExpired("N");

        // Mock the findById method of TradeRepository
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(trade));

        // Mock the request and verify the response
        mockMvc.perform(get("/api/v1/trade/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T1"));
    }

    // Test for updating a trade
    @Test
    void testUpdateTrade() throws Exception {
        Trade existingTrade = new Trade();
        existingTrade.setTradeId("T1");
        existingTrade.setBookId("1");
        existingTrade.setCounterpartyId("1");
        existingTrade.setCreatedDate("04/05/2025");
        existingTrade.setMaturityDate("05/05/2025");
        existingTrade.setIsExpired("N");

        Trade tradeDetails = new Trade();
        tradeDetails.setTradeId("T2");
        tradeDetails.setBookId("2");
        tradeDetails.setCounterpartyId("2");
        tradeDetails.setCreatedDate("04/05/2025");
        tradeDetails.setMaturityDate("05/05/2025");
        tradeDetails.setIsExpired("N");

        // Mock the findById and createTrades methods
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(existingTrade));
        doNothing().when(tradeService).createTrades(any());

        // Mock the request and verify the response
        mockMvc.perform(put("/api/v1/trade/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeId\":\"T2\", \"bookId\":\"1\", \"counterpartyId\":\"1\", \"createdDate\":\"04/05/2025\", \"maturityDate\":\"04/05/2025\", \"isExpired\":\"N\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T2"));
    }

    // Test for deleting a trade
    @Test
    void testDeleteTrade() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId("T1");
        trade.setBookId("1");
        trade.setCounterpartyId("1");
        trade.setCreatedDate("04/05/2025");
        trade.setMaturityDate("05/05/2025");
        trade.setIsExpired("N");

        // Mock the findById and delete methods
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).delete(any());

        // Mock the request and verify the response
        mockMvc.perform(delete("/api/v1/trade/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    // Test for ResourceNotFoundException in getTradeById
    @Test
    void testGetTradeByIdNotFound() throws Exception {
        // Mock the findById method to return empty
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Mock the request and verify the exception handling
        mockMvc.perform(get("/api/v1/trade/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Trade not exist with id:999"));
    }
}
