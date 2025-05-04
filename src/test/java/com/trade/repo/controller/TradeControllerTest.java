package com.trade.repo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.repo.model.Trade;
import com.trade.repo.model.TradeValidator;
import com.trade.repo.repository.TradeRepository;
import com.trade.repo.repository.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private Trade trade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trade = new Trade();
        trade.setId(1L);
        trade.setTradeId("T1");
        trade.setVersion(1);
        trade.setBookId("B1");
        trade.setCounterpartyId("C1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        trade.setCreatedDate(LocalDate.now().format(formatter));
        trade.setMaturityDate(LocalDate.now().plusDays(10).format(formatter));
        trade.setIsExpired("N");

    }

    @Test
    void testCreateTrade() throws Exception {
        // Arrange
        doNothing().when(tradeService).createTrades(anyList()); // Mocking the service
        String tradeJson = "{\"tradeId\":\"T1\", \"bookId\":1, \"counterpartyId\":1, \"createdDate\":\"04/05/2025\", \"maturityDate\":\"05/05/2025\", \"isExpired\":false}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tradeId").value("T1"));
    }
/*
    @Test
    void testGetAllTrades() throws Exception {
        when(tradeRepository.findAll()).thenReturn(List.of(trade));

        mockMvc.perform(get("/api/v1/trade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value("T1"));
    }

    @Test
    void testCreateTrade() throws Exception {
        mockMvc.perform(post("/api/v1/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trade)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tradeId").value("T1"));

        verify(tradeService).createTrades(anyList());
    }

    @Test
    void testCreateTrades() throws Exception {
        List<Trade> trades = List.of(trade);
        mockMvc.perform(post("/api/v1/trade/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trades)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value("T1"));

        verify(tradeService).createTrades(trades);
    }

/*    @Test
    void testGetTradeByIdSuccess() throws Exception {
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/api/v1/trade/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T1"));
    }

    @Test
    void testGetTradeByIdNotFound() throws Exception {
        when(tradeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/trade/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTradeSuccess() throws Exception {
        Trade updatedTrade = new Trade();
        updatedTrade.setId(1L);
        updatedTrade.setTradeId("T2");
        updatedTrade.setVersion(2);
        updatedTrade.setBookId("B2");
        updatedTrade.setCounterpartyId("C2");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        trade.setCreatedDate(LocalDate.now().format(formatter));
        trade.setMaturityDate(LocalDate.now().plusDays(10).format(formatter));
        updatedTrade.setIsExpired("N");

        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        mockMvc.perform(put("/api/v1/trade/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T2"));

        verify(tradeService).createTrades(List.of(any(Trade.class)));
    }

    @Test
    void testUpdateTradeWithLowerVersion() throws Exception {
        Trade lowerVersionTrade = new Trade();
        lowerVersionTrade.setVersion(0);

        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        mockMvc.perform(put("/api/v1/trade/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lowerVersionTrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTradeSuccess() throws Exception {
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).delete(trade);

        mockMvc.perform(delete("/api/v1/trade/1"))
                .andExpect(status().isNoContent());

        verify(tradeRepository).delete(trade);
    }

    @Test
    void testDeleteTradeNotFound() throws Exception {
        when(tradeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/trade/1"))
                .andExpect(status().isNotFound());
    }*/
}
