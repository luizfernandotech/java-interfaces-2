package services;

import entities.Contract;
import entities.Installment;

import java.util.Calendar;
import java.util.Date;

public class ContractService {

    private PaymentService paymentService;

    public ContractService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processContract(Contract contract, int months) {
        double basicQuota = contract.getTotalValue() / months;
        for(int i = 1; i <= months; i++) {
            double updatedQuota = basicQuota + paymentService.interest(basicQuota, i);
            double fullQuota = updatedQuota + paymentService.paymentFee(updatedQuota);
            Date dueDate = addMonths(contract.getDate(), i) ;
            Installment installment = new Installment(dueDate, fullQuota);
            contract.addInstallment(installment);
        }
    }

    private Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, months);
        return calendar.getTime();
    }
}
