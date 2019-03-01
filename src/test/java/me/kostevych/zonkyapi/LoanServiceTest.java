package me.kostevych.zonkyapi;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import me.kostevych.zonkyapi.api.RestClient;
import me.kostevych.zonkyapi.objects.Loan;
import me.kostevych.zonkyapi.services.LoanService;

public class LoanServiceTest {

	private RestClient restClient;

    private LoanService loanService;

    @Before
    public void setUp() {
        restClient = Mockito.mock(RestClient.class);

        loanService = new LoanService(restClient);
    }
    
    @Test
    public void storeNewLoansTest()
    {
    	int initialCount = loanService.getLoans().size();
    	
    	List<Loan> newLoans = Arrays.asList(LoanTestHelper.createTestLoan(1), LoanTestHelper.createTestLoan(5));
    	BDDMockito.given(restClient.getNewLoans(ArgumentMatchers.any())).willReturn(newLoans);

    	loanService.storeNewLoans();
    	
    	Assert.assertTrue(initialCount < loanService.getLoans().size());
    }
}
