package com.example.testingandci.unitTesting;

import com.example.testingandci.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.testingandci.repository.IAccountRepository;
import com.example.testingandci.service.AccountService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private IAccountRepository repository;

    @InjectMocks
    private AccountService accountService;


    @Test
    void contextLoads() {
    }

    @Test
    public void testDeleteAccountService() {
        Long accountIdToDelete = 1L;

        accountService.deleteAccount(accountIdToDelete);

        verify(repository, times(1)).deleteById(accountIdToDelete);
    }
    @Test
    public void testSaveAccountService(){
        Account mockAccount = Account.builder()
                .id(1L)
                .username("John Doe")
                .contactInfo("112")
                .accountType("ADMIN")
                .paymentInfo(112)
                .build();

        when(repository.save(any(Account.class))).thenReturn(mockAccount);

        Account newAccount = Account.builder()
                .username("Jane")
                .contactInfo("112")
                .accountType("ADMIN")
                .paymentInfo(112)
                .build();

        Account savedAccount = accountService.saveAccount(newAccount);

        verify(repository, times(1)).save(newAccount);

        assertEquals(mockAccount, savedAccount);
    }
    @Test
    public void testFetchedAccountService() {

        Long accountId = 1L;
        Account mockAccount = new Account();
        mockAccount.setUsername("tests");

        //Account exists
        when(repository.findById(accountId)).thenReturn(java.util.Optional.of(mockAccount));

        Account result = accountService.fetchedAccount(accountId);


        assertNotNull(result);
        assertEquals(mockAccount, result);

        verify(repository, times(1)).findById(accountId);



        //Account does not exist
        when(repository.findById(accountId)).thenReturn(java.util.Optional.empty());

        result = accountService.fetchedAccount(accountId);

        assertNull(result);
        verify(repository, times(2)).findById(accountId);

    }

    @Test
    public void testUpdateAccountService() {

        Account oldAccount = Account.builder()
                .id(1L)
                .username("John Doe")
                .contactInfo("112")
                .accountType("ADMIN")
                .paymentInfo(112)
                .build();

        System.out.println("old account");
        System.out.println(oldAccount);
        when(repository.save(oldAccount)).thenReturn(oldAccount);

        Account updatedAccount = accountService.updateAccount(oldAccount);

        System.out.println("new account");
        System.out.println(updatedAccount);

        assertNotNull(updatedAccount);
        assertEquals(oldAccount, updatedAccount);

        verify(repository, times(1)).save(oldAccount);
        assertEquals(oldAccount, updatedAccount);

    }
}
