package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractDataManager {
    /*---------------VARIABLES---------------*/



    /*--------------CONSTRUCTORS-------------*/



    /*------------GETTERS/SETTERS------------*/



    /*---------------FUNCTIONS---------------*/

    public void saveContract(Contract contract){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv",true))) {
            String contractHeader = String.format("%s|%s|%s|",contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail());

            Vehicle vehicle = contract.getVehicleSold();
            String vehicleInfo = String.format("%s|%s|%s|%s|%s|%s|%s|%s|", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());

            if (contract instanceof SalesContract salesContract){
                String contractInfo = String.format("%s|%s|%s|%s|%s|%s", salesContract.getSalesTaxAmount(), salesContract.getRecordingFee(), salesContract.getProcessingFee(), salesContract.getTotalPrice(), (salesContract.getWillFinance()?"YES":"NO"), salesContract.getMonthlyPayment());

                bw.write("SALE|"+contractHeader+vehicleInfo+contractInfo);
                bw.newLine();
                System.out.println("Sales contract successfully added to contracts.csv!");
            }
            else if(contract instanceof LeaseContract leaseContract){
                String contractInfo = String.format("%s|%s|%s|%s", leaseContract.getExpectedEndingValue(), leaseContract.getLeaseFee(), leaseContract.getTotalPrice(), leaseContract.getMonthlyPayment());

                bw.write("LEASE|"+contractHeader+vehicleInfo+contractInfo);
                bw.newLine();
                System.out.println("Lease contract successfully added to contracts.csv!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
