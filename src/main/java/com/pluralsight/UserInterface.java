package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell/Lease vehicle");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseRequest();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        System.out.print("Enter vehicle year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();

        System.out.print("Enter vehicle mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        dealership.addVehicle(vehicle);
        System.out.println("Vehicle added successfully!");
        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();

        boolean vehicleRemoved = false;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle removed successfully!");
                vehicleRemoved = true;
                break;
            }
        }

        if (!vehicleRemoved) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }

    private void processSellLeaseRequest(){
        System.out.print("Please type the VIN of the car for this contract: ");
        int vin = scanner.nextInt();
        scanner.nextLine();
        Vehicle onContract = null;
        boolean vehicleFound = false;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                onContract = vehicle;
                vehicleFound = true;
                System.out.println("Car found!");
            }
        }
        if(!vehicleFound){
            System.out.println("No car found with given VIN!");
            return;
        }

        System.out.print("Please type the date of the contract (YYYYMMDD): ");
        String date = scanner.nextLine().trim().replace("|","");
        System.out.print("Please type your name: ");
        String name = scanner.nextLine().trim().replace("|","");
        System.out.print("Please type your email: ");
        String email = scanner.nextLine().trim().replace("|","");

        System.out.print("Is this contract for a (S)ale or (L)ease? ");
        char contractType = scanner.nextLine().trim().toUpperCase().charAt(0);
        if(contractType == 'L'){
            LeaseContract leaseContract = new LeaseContract(date, name, email, onContract);
            int currentYear = LocalDate.now().getYear();
            if(currentYear - onContract.getYear() > 3){
                System.out.println("This car is too old! It cannot be leased!");
                return;
            }
            System.out.printf("For a lease on your chosen a car, your monthly payment will be $%.2f, and your total cost will be $%.2f! Are you sure you would like to create this contract (Y/N)? ", leaseContract.getMonthlyPayment(), leaseContract.getTotalPrice());
            char createContract = scanner.nextLine().trim().toUpperCase().charAt(0);
            if(createContract != 'Y'){return;}
            ContractDataManager contractDataManager = new ContractDataManager();
            contractDataManager.saveContract(leaseContract);
            dealership.removeVehicle(onContract);

        }
        else if(contractType == 'S'){
            System.out.print("Are you going to finance this vehicle (Y/N)? ");
            char finance = scanner.nextLine().trim().toUpperCase().charAt(0);
            SalesContract salesContract = new SalesContract(date, name, email, onContract, finance == 'Y');
            System.out.printf("For this given sale, your monthly payment will be $%.2f, and your total cost will be $%.2f! Are you sure you would like to create this contract (Y/N)? ", salesContract.getMonthlyPayment(), salesContract.getTotalPrice());
            char createContract = scanner.nextLine().trim().toUpperCase().charAt(0);
            if(createContract != 'Y'){return;}
            ContractDataManager contractDataManager = new ContractDataManager();
            contractDataManager.saveContract(salesContract);
            dealership.removeVehicle(onContract);
        }
        else{
            System.out.println("Invalid contract type!");
            return;
        }

        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        dealershipFileManager.saveDealership(dealership);
    }

}
