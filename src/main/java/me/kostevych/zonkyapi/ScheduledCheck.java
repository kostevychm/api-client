package me.kostevych.zonkyapi;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.kostevych.zonkyapi.services.LoanService;

/** 
 * Checks new loans every {check-interval} milliseconds.
 */
@Component
public class ScheduledCheck {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private LoanService loanService;
		
	public ScheduledCheck(LoanService service) {
		loanService = service;
	}
	
	@Scheduled(fixedDelayString = "${app.check-interval}")
	public void checkNewLoans() {
		logger.debug("Checking for new loans at {}", Instant.now());
		
		loanService.storeNewLoans();
	}

}
