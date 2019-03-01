package me.kostevych.zonkyapi.services;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import me.kostevych.zonkyapi.api.RestClient;
import me.kostevych.zonkyapi.objects.Loan;

@Service
public class LoanService {
	private List<Loan> loans;
	private RestClient restClient;

	public LoanService(RestClient restClient) {
		this.restClient = restClient;
		if(loans == null || loans.isEmpty())
		{
			//as getInitialLoans returns immutable array should be created new one
			loans = new ArrayList<Loan>(restClient.getInitialLoans());
		}
	}
	
	public List<Loan> getLoans(){
		return loans;
	}
	
	public void storeNewLoans(){
		//If there is no initial data, just check loans for the last 10 minutes
		OffsetDateTime tm = loans.isEmpty()?OffsetDateTime.now().minus(10, ChronoUnit.MINUTES):
							loans.get(0).getDatePublished(); 

		List<Loan> array = restClient.getNewLoans(tm);
		loans.addAll(0, array);
	}
}
