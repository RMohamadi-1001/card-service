package ir.co.isc.cardservice;

import ir.co.isc.cardservice.dao.AccountEntity;
import ir.co.isc.cardservice.dao.CardEntity;
import ir.co.isc.cardservice.dao.PersonEntity;
import ir.co.isc.cardservice.enums.CardTypeEnum;
import ir.co.isc.cardservice.repository.AccountRepository;
import ir.co.isc.cardservice.repository.CardRepository;
import ir.co.isc.cardservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class CardServiceApplication implements CommandLineRunner {

	PersonRepository personRepository;
	AccountRepository accountRepository;
	CardRepository cardRepository;

	public static void main(String[] args) {
		SpringApplication.run(CardServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Add Person
		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName("Reza");
		personEntity.setLastName("Mohamadi");
		personEntity.setNationalCode("1931038597");
		personEntity.setHomeAddress("Tehran");
		personEntity.setPhoneNumber("09127589680");
		PersonEntity personEntity1 = personRepository.saveAndFlush(personEntity);

		PersonEntity personEntity2 = new PersonEntity();
		personEntity2.setFirstName("Parham");
		personEntity2.setLastName("Mohamadi");
		personEntity2.setNationalCode("1931038598");
		personEntity2.setHomeAddress("Tehran");
		personEntity2.setPhoneNumber("09127589681");
		PersonEntity personEntity3 = personRepository.saveAndFlush(personEntity2);

		// Add Account
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setAccountNumber("7362948574");
		accountEntity.setBalance(20000000L);
		accountEntity.setIsActive(true);
		accountEntity.setPersonEntity(personEntity1);
		AccountEntity accountEntity1 = accountRepository.saveAndFlush(accountEntity);

		AccountEntity accountEntity2 = new AccountEntity();
		accountEntity2.setAccountNumber("7362948575");
		accountEntity2.setBalance(30000000L);
		accountEntity2.setIsActive(true);
		accountEntity2.setPersonEntity(personEntity3);
		AccountEntity accountEntity3 = accountRepository.saveAndFlush(accountEntity2);

		// Add Card
		CardEntity cardEntity = new CardEntity();
		cardEntity.setAccountEntity(accountEntity1);
		cardEntity.setCardNumber("6280231487654567");
		cardEntity.setCardType(CardTypeEnum.CASH);
		cardEntity.setIssuerName("Markazi");
		cardEntity.setExpireDate("202305");
		cardEntity.setIssuerCode("111111");
		cardEntity.setIsActive(true);
		cardRepository.saveAndFlush(cardEntity);

		CardEntity cardEntity2 = new CardEntity();
		cardEntity2.setAccountEntity(accountEntity3);
		cardEntity2.setCardNumber("6280231487654568");
		cardEntity2.setCardType(CardTypeEnum.CREDIT);
		cardEntity2.setIssuerName("Theran");
		cardEntity2.setExpireDate("202308");
		cardEntity2.setIssuerCode("222222");
		cardEntity2.setIsActive(true);
		cardRepository.saveAndFlush(cardEntity2);

		CardEntity cardEntity3 = new CardEntity();
		cardEntity3.setAccountEntity(accountEntity1);
		cardEntity3.setCardNumber("6280231487654566");
		cardEntity3.setCardType(CardTypeEnum.CREDIT);
		cardEntity3.setIssuerName("Markazi");
		cardEntity3.setExpireDate("202309");
		cardEntity3.setIssuerCode("111111");
		cardEntity3.setIsActive(true);
		cardRepository.saveAndFlush(cardEntity3);
	}
}
