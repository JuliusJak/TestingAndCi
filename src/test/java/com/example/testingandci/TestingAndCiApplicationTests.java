package com.example.testingandci;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.testingandci.repository.IAccountRepository;
import com.example.testingandci.service.AccountService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@SpringBootTest
class TestingAndCiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private IAccountRepository repository;

	@InjectMocks
	private AccountService accountService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDeleteAccount() {
		Long accountIdToDelete = 1L;

		accountService.deleteAccount(accountIdToDelete);

		verify(repository, times(1)).deleteById(accountIdToDelete);
	}
}
