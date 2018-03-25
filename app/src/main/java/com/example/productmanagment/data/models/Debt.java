package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 10.03.2018.
 */

public class Debt {
    private int id;
    private String sum;
    private String remain;
    private String description;
    private String borrowDate;
    private String repayDate;
    private String borrower;
    private int debtType;
    private int accountId;

    private final int DEBT_TYPE_BORROWED = 1;
    private final int DEBT_TYPE_LENT= 2;

    public Debt(){

    }

    public Debt(String sum, String description, String borrowDate, String repayDate, String borrower, int debtType, int accountId) {
        this.sum = sum;
        this.description = description;
        this.borrowDate = borrowDate;
        this.repayDate = repayDate;
        this.borrower = borrower;
        this.debtType = debtType;
        this.accountId = accountId;
    }

    public Debt(String sum, String remain, String description, String borrowDate, String repayDate, String borrower, int debtType, int accountId) {
        this.sum = sum;
        this.remain = remain;
        this.description = description;
        this.borrowDate = borrowDate;
        this.repayDate = repayDate;
        this.borrower = borrower;
        this.debtType = debtType;
        this.accountId = accountId;
    }

    public Debt(int id, String sum, String remain, String description, String borrowDate, String repayDate, String borrower, int debtType, int accountId) {
        this.id = id;
        this.sum = sum;
        this.remain = remain;
        this.description = description;
        this.borrowDate = borrowDate;
        this.repayDate = repayDate;
        this.borrower = borrower;
        this.debtType = debtType;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public String getDescription() {
        return description;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public String getBorrower() {
        return borrower;
    }

    public int getDebtType() {
        return debtType;
    }

    public String getRemain() {
        return remain;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setSum(String cost) {
        this.sum = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public void setDebtType(int debtType) {
        this.debtType = debtType;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public static class DebtPart{
        int id;
        int debtId;
        int partSum;
        String date;

        public DebtPart(int id, int debtId, int partSum, String date) {
            this.id = id;
            this.debtId = debtId;
            this.partSum = partSum;
            this.date = date;
        }

        public DebtPart(int debtId, int partSum, String date) {
            this.debtId = debtId;
            this.partSum = partSum;
            this.date = date;
        }

        public int getDebtId() {
            return debtId;
        }

        public void setDebtId(int debtId) {
            this.debtId = debtId;
        }

        public int getPartSum() {
            return partSum;
        }

        public void setPartSum(int partSum) {
            this.partSum = partSum;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }


}
