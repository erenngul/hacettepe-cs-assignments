#pragma once
#include <string>
#include <vector>
#include "CashierQueue.h"
#include "BaristaQueue.h"
#include "Cashier.h"
#include "Barista.h"

class SecondModel {
public:
	double totalRunningTime;
	int maximumLengthOfCashierQueue;
	std::vector<Customer*> customers;
	std::vector<Cashier*> cashiers;
	std::vector<Barista*> baristas;
	CashierQueue* cashierQueue;

	// This constructor reads the input vectors; creates cashiers, baristas and customers.
	SecondModel(std::vector<std::vector<std::string>> inputVectors);

	// This destructor deletes the properties in the model.
	~SecondModel();

	// This function finds the next available cashier and returns that cashier. If there is not any available cashier, cashiers are checked if any of them can be available for the current customer.
	Cashier* findAvailableCashier(Customer* customer);

	// This function returns the closest cashier to be available if there isn't any available cashier.
	Cashier* findNextAvailableCashier();

	// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
	void enqueueCustomersInCashierQueue(double nextAvailableCashierTimeStart);

	// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
	void enqueueCustomersInBaristaQueue(Barista* servingBarista, double baristaTimeStart);

	// This function turns enqueued property to false in all customers.
	void resetEnqueued();

	// This function finds the total running time of the model.
	double findTotalRunningTime();

	// This function calculates the turnaround times of customers.
	void calculateTurnaroundTimes();

	// This function calculates the unit utilization times of cashiers and baristas in the model.
	void calculateUnitUtilizationTimes();

	// This function writes the output to output file.
	void writeOutput(std::ofstream& outputFile);

	// This function is the main function for this class. All orders are taken from cashiers first and then the baristas prepare the orders.
	void runSecondModel(std::ofstream& outputFile);
};