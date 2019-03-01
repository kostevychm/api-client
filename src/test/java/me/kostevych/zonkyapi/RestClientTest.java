package me.kostevych.zonkyapi;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.kostevych.zonkyapi.api.RestClient;
import me.kostevych.zonkyapi.objects.Loan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestClientTest {
	
	@Autowired
	private RestClient restClient;
	
	@Value("${api.initial-loans-count}")
	private int initialCount;
	
	@Test
    public void initialLoadTest() {
        List<Loan> loans = restClient.getInitialLoans();
        Assert.assertEquals(loans.size(), initialCount);
    }
	
	@Test
    public void getNewLoansTest() {
		//Assume that since yesterday there are some loans
        List<Loan> loans = restClient.getNewLoans(OffsetDateTime.now().minus(1, ChronoUnit.DAYS));
        Assert.assertTrue(loans.size()>0);
    }

}
