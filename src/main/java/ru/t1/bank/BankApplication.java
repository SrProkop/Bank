package ru.t1.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.t1.bank.enums.Role;
import ru.t1.bank.enums.Type;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.TransactionService;
import ru.t1.bank.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class BankApplication {

	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void testJpaMethods(){
		/*List<Account> accounts = accountService.findAll();
		for (int i = 0; i < 5; i ++) {
			Transaction transaction = new Transaction();
			transaction.setAccountTo(accounts.get(i));
			transaction.setDate(LocalDateTime.of(2021, 02, 12, 12, 49));
			transaction.setType(Type.Deposit);
			transaction.setSumTo(BigDecimal.valueOf(1000));
			transaction.setSumFrom(BigDecimal.valueOf(1000));
			Account account = accounts.get(i);
			account.setMoney(BigDecimal.valueOf(1000));
			transactionService.createTransaction(transaction);
			accountService.createAccount(account);

		}
		List<User> users = userService.findAll();
		Account account = new Account();
		account.setClient(users.get(3));
		account.setCurrency(currencyService.findById(1l));
		account.setMoney(BigDecimal.ZERO);
		accountService.createAccount(account);*/
		/*List<Account> accounts = accountService.findAll();
		for (int i = 0; i < 3; i++) {
			Account account = accounts.get(1);
			Transaction transaction = new Transaction();
			transaction.setDate(LocalDateTime.of(2020, i+1, 20, 20, 20));
			transaction.setAccountTo(account);
			transaction.setType(Type.Deposit);
			transaction.setSumTo(new BigDecimal(10000));
			account.getTransactionsTo().add(transaction);
		}
		Account account = accounts.get(2);
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDateTime.of(2020, 4, 20, 20, 20));
		transaction.setAccountTo(account);
		transaction.setType(Type.Deposit);
		transaction.setSumTo(new BigDecimal(2000000));
		account.getTransactionsTo().add(transaction);
		accountService.saveAll(accounts);*/
		System.out.println(transactionService.findTransactionWhereSumWhere(100000l).get(0).getAccountTo().getId());
		System.out.println(transactionService.findAll().size());
		System.out.println(transactionService.findTransactionByDateBefore(LocalDateTime.of(2020, 3, 10, 20, 21)).size());
	}


	private void createUsersAndAccounts() {
		List<User> users = new ArrayList<>();
		Currency currency = new Currency();
		currency.setName("Рубль");
		currency.setCode("RUB");
		currencyService.createCurrency(currency);
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setFullName("Alex" + i);
			user.setDateOfBirth(LocalDate.of(1990 + i, 4, 10));
			user.getRoles().add(Role.Client);
			userService.createUser(user);
			users.add(user);
		}
		for (int i = 0; i < 5; i++) {
			Account account = new Account();
			account.setNumber(String.valueOf(100000 + i));
			account.setClient(users.get(i));
			account.setCurrency(currencyService.findById(1l));
			account.setMoney(BigDecimal.ZERO);
			accountService.createAccount(account);
		}
	}

	private void createTransaction() {
		List<Account> accounts = accountService.findAll();
		for (int i = 0; i < accounts.size() - 1; i++) {
			Transaction transaction = new Transaction();
			transaction.setSumTo(new BigDecimal(1000));
			transaction.setType(Type.Deposit);
			transaction.setAccountTo(accounts.get(i));
			transaction.setDate(LocalDateTime.of(2020, 2, 20, 21, i));
			accounts.get(i).setMoney(accounts.get(i).getMoney().add(new BigDecimal(1000)));
		}
	}

}
