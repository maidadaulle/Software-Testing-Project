package Controllers;

import Models.Admin;
import Models.BillsType;
import Models.StandardViewResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class StatisticsController {
    public StandardViewResponse<Integer> numberOfBooksSoldDuringPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return new StandardViewResponse<>(0, "Dates cannot be null");
        }
        Date endDateF = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDateF = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Integer result = 0;

        if (endDate.isBefore(startDate)) {
            return new StandardViewResponse<>(0, "End date cannot be before starting date");
        }
        if(endDateF.after(Date.from(ZonedDateTime.now().plusDays(1).toInstant())) || startDateF.after(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))){
            return new StandardViewResponse<>(0, "Dates must be actual!");
        }
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getType() == BillsType.Sold) {
                var quantities = bill.getQuantity();
                if (bill.getCreatedDate().before(endDateF) && bill.getCreatedDate().after(startDateF)) {
                    for(var quantity : quantities)
                    {
                        result+=quantity;
                    }
                }
            }
        }
        return new StandardViewResponse<>(result, null);
    }

    public StandardViewResponse<Integer> numberOfBooksBoughtDuringPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return new StandardViewResponse<>(0, "Dates cannot be null!");
        }
        Date endDateF = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDateF = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Integer result = 0;

        if (endDate.isBefore(startDate)) {
            return new StandardViewResponse<>(0, "End date cannot be before starting date!");
        }
        if(endDateF.after(Date.from(ZonedDateTime.now().plusDays(1).toInstant())) || startDateF.after(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))){
            return new StandardViewResponse<>(0, "Dates must be actual!");
        }
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getType() == BillsType.Bought) {
                var quantities = bill.getQuantity();
                if (bill.getCreatedDate().before(endDateF) && bill.getCreatedDate().after(startDateF)) {
                  for(var quantity : quantities)
                  {
                      result+=quantity;
                  }
                }
            }
        }
        return new StandardViewResponse<>(result, null);
    }

    public Double getProfitThroughPeriod(LocalDate startDate, LocalDate endDate) {
        Double result = (double) 0;
        Date endDateF = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDateF = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getType() == BillsType.Sold)
                if (bill.getCreatedDate().before(endDateF) && bill.getCreatedDate().after(startDateF)) {
                    result += bill.getTotalPrice();
                }
        }
        return result;
    }

    public Double getBookCostsThroughPeriod(LocalDate startDate, LocalDate endDate) {
        Double result = (double) 0;
        Date endDateF = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDateF = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getType() == BillsType.Bought)
                if (bill.getCreatedDate().before(endDateF) && bill.getCreatedDate().after(startDateF)) {
                    result += bill.getTotalPrice();
                }
        }
        return result;
    }

    public Double getTotalSalary() {
        Double salary = (double) 0;
        var users = FileController.users;
        for (var user : users) {
            if (!(user instanceof Admin)) {

            }
            salary += user.getSalary();
        }
        return salary;
    }

}
