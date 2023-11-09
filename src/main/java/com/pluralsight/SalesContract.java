package com.pluralsight;

public class SalesContract extends Contract{
    /*---------------VARIABLES---------------*/

    private final double salesTaxAmount = 0.05;
    private final int recordingFee = 100;
    private int processingFee;
    private boolean willFinance;

    /*--------------CONSTRUCTORS-------------*/

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean willFinance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.willFinance = willFinance;
        processingFee = (vehicleSold.getPrice()<10000)?295:495;
        monthlyPayment = getMonthlyPayment();
        totalPrice = getTotalPrice();
    }

    /*------------GETTERS/SETTERS------------*/

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public int getRecordingFee() {
        return recordingFee;
    }

    public int getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(int processingFee) {
        this.processingFee = processingFee;
    }

    public boolean getWillFinance() {
        return willFinance;
    }

    public void setWillFinance(boolean willFinance) {
        this.willFinance = willFinance;
    }

    /*---------------FUNCTIONS---------------*/

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        int numberOfPayments = 0;
        double interestRate = 0;
        if (willFinance) {
            if (getVehicleSold().getPrice() >= 10000) {
                numberOfPayments = 48;
                interestRate = 4.25 / 1200;
            } else {
                numberOfPayments = 24;
                interestRate = 5.25 / 1200;
            }

            double monthlyPayment = getTotalPrice() * (interestRate * Math.pow(1 + interestRate, numberOfPayments)) / (Math.pow(1 + interestRate, numberOfPayments) - 1);
            monthlyPayment = Math.round(monthlyPayment * 100);
            monthlyPayment /= 100;
            return monthlyPayment;
        } else {
            return 0.0;
        }
    }

}
