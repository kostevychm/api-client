package me.kostevych.zonkyapi;

import java.time.OffsetDateTime;

import me.kostevych.zonkyapi.objects.Loan;

public class LoanTestHelper {
    public static Loan createTestLoan(Integer id) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setName("Test Loan id: "+id);
        loan.setDatePublished(OffsetDateTime.now());
        return loan;
    }
}
