package com.example.testingandci.endToEndTesting;

import com.example.testingandci.model.Account;
import com.example.testingandci.repository.IAccountRepository;
import com.example.testingandci.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerEndToEndTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateAccount() throws Exception {
        // Create a new account via an HTTP POST request
        MvcResult createResult = mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
                        .param("username", "John")
                        .param("contactInfo", "john@example.com")
                        .param("accountType", "USER")
                        .param("paymentInfo", "100")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();

        assertEquals(200, createResult.getResponse().getStatus());

        // Extract the response content as a String
        String responseContent = createResult.getResponse().getContentAsString();

        // Convert the response content to an Account object (assuming JSON format)
        ObjectMapper objectMapper = new ObjectMapper();
        Account createdAccount = objectMapper.readValue(responseContent, Account.class);

        assertNotNull(createdAccount.getId());
    }

    @Test
    public void testUpdateAccount() throws Exception {
        // Create an account to update
        Account account = new Account();
        account.setUsername("John");
        account.setContactInfo("john@example.com");
        account.setAccountType("USER");
        account.setPaymentInfo(100);
        account = accountRepository.save(account);

        // Update the created account via an HTTP PATCH request
        MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.patch("/account/update/{id}", account.getId())
                        .param("username", "Updated John")
                        .param("contactInfo", "updated@example.com")
                        .param("paymentInfo", "200")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();

        assertEquals(200, updateResult.getResponse().getStatus());

        // Extract the response content as a String
        String responseContent = updateResult.getResponse().getContentAsString();

        // Convert the response content to an Account object (assuming JSON format)
        ObjectMapper objectMapper = new ObjectMapper();
        Account updatedAccount = objectMapper.readValue(responseContent, Account.class);

        // Fetch the updated account from the database to verify the changes
        Account retrievedAccount = accountService.fetchedAccount(updatedAccount.getId());

        assertNotNull(retrievedAccount);
        assertEquals("Updated John", retrievedAccount.getUsername());
        assertEquals("updated@example.com", retrievedAccount.getContactInfo());
        assertEquals(200, retrievedAccount.getPaymentInfo());
    }
}