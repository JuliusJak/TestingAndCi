package com.example.testingandci;

import com.example.testingandci.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.testingandci.repository.IAccountRepository;
import com.example.testingandci.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TestingAndCiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private IAccountRepository repository;

	@InjectMocks
	private AccountService accountService;

	@Test
	public void testDeleteAccount() {
		Long accountIdToDelete = 1L;

		accountService.deleteAccount(accountIdToDelete);

		verify(repository, times(1)).deleteById(accountIdToDelete);
	}
	@Test
	public void saveAccount(){
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
		System.out.println(newAccount);
		System.out.println(mockAccount);
		System.out.println(savedAccount);

	}
}
