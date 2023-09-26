package com.example.testingandci;

import com.example.testingandci.controller.AccountController;
import com.example.testingandci.model.Account;
import com.example.testingandci.service.IAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.testingandci.repository.IAccountRepository;
import com.example.testingandci.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TestingAndCiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private IAccountRepository repository;
	@Mock
	private AccountService accountServiceMocked;
	@Mock
	private IAccountService IAccountServiceMocked;
	@InjectMocks
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;


	// AccountService Tests
	//Unit Tests

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
		System.out.println(result);

		verify(repository, times(1)).findById(accountId);



		//Account does not exist
		when(repository.findById(accountId)).thenReturn(java.util.Optional.empty());

		result = accountService.fetchedAccount(accountId);

		assertNull(result);
		System.out.println(result);
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


	//AccountController Tests
	//Unit Tests

	@Test
	public void testSaveAccountController() {

		String username = "John";
		String contactInfo = "123";
		String accountType = "USER";
		int paymentInfo = 100;

		Account mockAccount = new Account();
		mockAccount.setUsername(username);
		mockAccount.setContactInfo(contactInfo);
		mockAccount.setAccountType(accountType);
		mockAccount.setPaymentInfo(paymentInfo);

		when(accountServiceMocked.saveAccount(any(Account.class))).thenReturn(mockAccount);

		Account response = accountController.saveAccount(username, contactInfo, accountType, paymentInfo);

		assertNotNull(response);
		assertEquals(mockAccount, response);

		verify(accountServiceMocked, times(1)).saveAccount(any(Account.class));
	}

	@ParameterizedTest
	@CsvSource({
			"test,,,",
			"john_doe,john@example.com,premium,-100"})
	public void testSaveAccountWithInvalidParametersController(String username, String contactInfo, String accountType, Integer paymentInfo) {

		if (username == null
				|| contactInfo == null
				|| accountType == null
				|| paymentInfo == null){
			assertThrows(NullPointerException.class, () -> {
				accountController.saveAccount(username, contactInfo, accountType, paymentInfo);
			});
		} else {
			assertThrows(IllegalArgumentException.class, () -> {
				accountController.saveAccount(username, contactInfo, accountType, paymentInfo);
			});
		}
	}

	@ParameterizedTest
	@ValueSource(longs = { 1L, 2L })
	public void testGetAccountByIdController(long accountId) throws AccountNotFoundException {

		//Account can be found
		if (accountId == 1L) {

			Account mockAccount = new Account();
			mockAccount.setId(accountId);
			mockAccount.setUsername("john_doe");

			when(accountServiceMocked.fetchedAccount(accountId)).thenReturn(mockAccount);

			ResponseEntity<Account> response = accountController.getAccountById(accountId);

			assertEquals(200, response.getStatusCode().value());
			assertEquals(mockAccount, response.getBody());
		} else {
			//Account can not be found
			when(accountServiceMocked.fetchedAccount(accountId)).thenReturn(null);

			assertThrows(AccountNotFoundException.class, () -> {
				accountController.getAccountById(accountId);
			});
		}
	}

	@ParameterizedTest
	@CsvSource({
			"1, updatedUser, updated@example.com, USER, 200, true, 200",
			"2, updatedUser, updated@example.com, USER, 200, false, 0",
			"1, '', updated@example.com, ADMIN, 200, true, 200",
			"1, updatedUser, '', ADMIN, 200, true, 200",
			"2, updatedUser, updated@example.com, '', 200, true, 200",
			"1, updatedUser, updated@example.com, ADMIN, 0, false, 0",
			"1, updatedUser, updated@example.com, USER, -200, false, 0"
	})
	public void testUpdateAccount(
			long accountId, String username, String contactInfo, String accountType, int paymentInfo,
			boolean accountExists, int expectedPaymentInfo) throws AccountNotFoundException {

		if (accountExists) {
			Account existingAccount = new Account();
			existingAccount.setId(accountId);
			existingAccount.setUsername(username);
			existingAccount.setContactInfo(contactInfo);
			existingAccount.setAccountType(accountType);
			existingAccount.setPaymentInfo(paymentInfo);

			when(accountServiceMocked.fetchedAccount(accountId)).thenReturn(existingAccount);

			when(accountServiceMocked.saveAccount(existingAccount)).thenReturn(existingAccount);

			Account updatedAccount = accountController.updateAccount(accountId, username, contactInfo, accountType, paymentInfo);

			assertEquals(expectedPaymentInfo, updatedAccount.getPaymentInfo());
		} else {
			when(accountServiceMocked.fetchedAccount(accountId)).thenReturn(null);

			assertThrows(AccountNotFoundException.class, () -> {
				accountController.updateAccount(accountId, username, contactInfo, accountType, paymentInfo);
			});
		}
	}


	@Test
	public void testDeleteAccountController() {

		long accountId = 1L;

		accountController.deleteAccount(accountId);

		verify(accountServiceMocked, times(1)).deleteAccount(accountId);
	}

}
