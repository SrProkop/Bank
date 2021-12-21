package ru.t1.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.service.CurrencyService;

import java.util.ArrayList;

@SpringBootApplication
public class BankApplication {

	@Autowired
	private CurrencyService currencyService;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	/*@EventListener(ApplicationReadyEvent.class)
	private void testJpaMethods(){

	}
*/
}
