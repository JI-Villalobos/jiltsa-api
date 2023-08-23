package com.jiltsa.admin;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.repository.AccountingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Testcontainers
class AdminApplicationTests {

	@Autowired
	public AccountingRepository repository;

	@Test
	@Disabled
	void contextLoads() {
		List<Accounting> accountingList = repository.findAll();
		Assertions.assertThat(accountingList).isNotNull();
		Assertions.assertThat(accountingList.size()).isEqualTo(2);
	}

}
