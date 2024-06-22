package com.example.wallet_java.controller;

import com.example.wallet_java.dto.WalletRequest;
import com.example.wallet_java.exception.WalletNotFoundException;
import com.example.wallet_java.model.OperationType;
import com.example.wallet_java.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    public void testUpdateWallet() throws Exception {
        WalletRequest request = new WalletRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(BigDecimal.valueOf(1000));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + request.getWalletId() + "\",\"operationType\":\"DEPOSIT\",\"amount\":1000}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWalletBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        when(walletService.getWalletBalance(walletId)).thenReturn(BigDecimal.valueOf(1000));

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWalletBalanceNotFound() throws Exception {
        UUID walletId = UUID.randomUUID();
        doThrow(new WalletNotFoundException("Wallet not found")).when(walletService).getWalletBalance(walletId);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isNotFound());
    }
}
