#include <algorithm>
#include <fstream>
#include <iomanip>
#include <iostream>
#include "SecondModel.h"

// This constructor reads the input vectors; creates cashiers, baristas and customers.
SecondModel::SecondModel(std::vector<std::vector<std::string>> inputVectors) : totalRunningTime(0), maximumLengthOfCashierQueue(0), cashierQueue(new CashierQueue()) {
	int numberOfCashiers = stoi(inputVectors[0][0]);
	int numberOfOrders = stoi(inputVectors[1][0]);
	for (int i = 0; i < numberOfCashiers; i++)
		cashiers.push_back(new Cashier(i));
	for (int i = 0; i < numberOfCashiers / 3; i++)
		baristas.push_back(new Barista(new BaristaQueue()));
	for (int i = 2; i < numberOfOrders + 2; i++)
		customers.push_back(new Customer(stod(inputVectors[i][0]), stod(inputVectors[i][1]), stod(inputVectors[i][2]), stod(inputVectors[i][3])));
}

// This destructor deletes the properties in the model.
SecondModel::~SecondModel() {
	delete cashierQueue;
	cashierQueue = nullptr;
	for (Customer* customer : customers)
		delete customer;
	for (Cashier* cashier : cashiers)
		delete cashier;
	for (Barista* barista : baristas)
		delete barista;
}

// This function finds the next available cashier and returns that cashier. If there is not any available cashier, cashiers are checked if any of them can be available for the current customer.
Cashier* SecondModel::findAvailableCashier(Customer* customer) {
	for (Cashier* cashier : cashiers) {
		if (!cashier->servingCustomer)
			break;
		if (cashier->timeStart <= customer->arrivalTime + customer->waitingCashierTime) {
			cashier->servingCustomer->arrivalTimeBarista = cashier->timeStart;
			cashier->available = true;
			cashier->servingCustomer = nullptr;
		}
	}
	for (Cashier* cashier : cashiers) {
		if (cashier->available)
			return cashier;
	}
	return nullptr;
}

// This function returns the closest cashier to be available if there isn't any available cashier.
Cashier* SecondModel::findNextAvailableCashier() {
	Cashier* leastTimeStartCashier = cashiers[0];
	for (Cashier* cashier : cashiers) {
		if (cashier->timeStart < leastTimeStartCashier->timeStart)
			leastTimeStartCashier = cashier;
	}
	return leastTimeStartCashier;
}

// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
void SecondModel::enqueueCustomersInCashierQueue(double nextAvailableCashierTimeStart) {
	for (Customer* customer : customers) {
		if (customer->enqueued)
			continue;
		if (customer->arrivalTime < nextAvailableCashierTimeStart) {
			cashierQueue->enqueue(customer);
			customer->enqueued = true;
		}
		else
			break;
	}
}

// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
void SecondModel::enqueueCustomersInBaristaQueue(Barista* servingBarista, double baristaTimeStart) {
	for (Customer* customer : customers) {
		if (customer->enqueued || servingBarista != baristas[customer->baristaIdToEnqueue])
			continue;
		if (customer->arrivalTimeBarista < baristaTimeStart) {
			servingBarista->baristaQueue->enqueue(customer);
			customer->enqueued = true;
		}
		else
			break;
	}
}

// This function turns enqueued property to false in all customers.
void SecondModel::resetEnqueued() {
	for (Customer* customer : customers)
		customer->enqueued = false;
}

// This function finds the total running time of the model.
double SecondModel::findTotalRunningTime() {
	double maximumTakeCoffeeTime = customers[0]->takeCoffeeTime;
	for (Customer* customer : customers) {
		if (customer->takeCoffeeTime > maximumTakeCoffeeTime)
			maximumTakeCoffeeTime = customer->takeCoffeeTime;
	}
	return maximumTakeCoffeeTime;
}

// This function calculates the turnaround times of customers.
void SecondModel::calculateTurnaroundTimes() {
	for (Customer* customer : customers)
		customer->turnaroundTime = customer->takeCoffeeTime - customer->arrivalTime;
}

// This function calculates the unit utilization times of cashiers and baristas in the model.
void SecondModel::calculateUnitUtilizationTimes() {
	for (Cashier* cashier : cashiers)
		cashier->utilization = cashier->busyTimeOfUnit / totalRunningTime;
	for (Barista* barista : baristas)
		barista->utilization = barista->busyTimeOfUnit / totalRunningTime;
}

// This function writes the output to output file.
void SecondModel::writeOutput(std::ofstream& outputFile) {
	outputFile << std::fixed << std::setprecision(2) << totalRunningTime << "\n";
	outputFile << maximumLengthOfCashierQueue << "\n";
	for (Barista* barista : baristas)
		outputFile << barista->baristaQueue->maximumLength << "\n";
	for (Cashier* cashier : cashiers)
		outputFile << std::setprecision(2) << cashier->utilization << "\n";
	for (Barista* barista : baristas)
		outputFile << std::setprecision(2) << barista->utilization << "\n";
	for (Customer* customer : customers)
		outputFile << std::setprecision(2) << customer->turnaroundTime << "\n";
	outputFile << "\n";
}

// This function is a key for comparing customers according to their arrival time to baristas.
bool compareByArrivalTimeBaristaSecond(Customer* customer1, Customer* customer2) {
	return customer1->arrivalTimeBarista < customer2->arrivalTimeBarista;
}

// This function is a key for comparing customers according to their arrival times.
bool compareByArrivalTimeSecond(Customer* customer1, Customer* customer2) {
	return customer1->arrivalTime < customer2->arrivalTime;
}

// This function is the main function for this class. All orders are taken from cashiers first and then the baristas prepare the orders.
void SecondModel::runSecondModel(std::ofstream& outputFile) {
	// Cashiers take the orders in this part.
	for (Customer* customer : customers) {
		if (!customer->enqueued) {
			cashierQueue->enqueue(customer);
			customer->enqueued = true;
		}
		Cashier* availableCashier = findAvailableCashier(customer);
		Customer* currentCustomer = cashierQueue->front;
		if (availableCashier) {
			availableCashier->servingCustomer = currentCustomer;
			availableCashier->available = false;
			availableCashier->busyTimeOfUnit += currentCustomer->orderTime;
			availableCashier->timeStart = currentCustomer->arrivalTime + currentCustomer->orderTime;
			currentCustomer->arrivalTimeBarista = availableCashier->timeStart;
			cashierQueue->dequeue();
			currentCustomer->baristaIdToEnqueue = availableCashier->id / 3;
		}
		else {
			Cashier* nextAvailableCashier = findNextAvailableCashier();
			currentCustomer->waitingCashierTime = nextAvailableCashier->timeStart - currentCustomer->arrivalTime;
			enqueueCustomersInCashierQueue(nextAvailableCashier->timeStart);
			nextAvailableCashier->servingCustomer = currentCustomer;
			nextAvailableCashier->available = false;
			nextAvailableCashier->busyTimeOfUnit += currentCustomer->orderTime;
			nextAvailableCashier->timeStart = currentCustomer->arrivalTime + currentCustomer->waitingCashierTime + currentCustomer->orderTime;
			currentCustomer->arrivalTimeBarista = nextAvailableCashier->timeStart;
			cashierQueue->dequeue();
			currentCustomer->baristaIdToEnqueue = nextAvailableCashier->id / 3;
		}
	}

	// Customers are sorted by arrival time to baristas so the customer who first finishes giving order is iterated in the next part first.
	std::sort(customers.begin(), customers.end(), compareByArrivalTimeBaristaSecond);
	resetEnqueued();

	// Baristas prepare the orders in this part.
	for (Customer* customer : customers) {
		Barista* servingBarista = baristas[customer->baristaIdToEnqueue];
		if (!customer->enqueued) {
			servingBarista->baristaQueue->enqueue(customer);
			customer->enqueued = true;
		}
		if (servingBarista->available) {
			Customer* currentCustomer = servingBarista->baristaQueue->front;
			servingBarista->servingCustomer = currentCustomer;
			servingBarista->available = false;
			servingBarista->busyTimeOfUnit += currentCustomer->brewTime;
			servingBarista->timeStart = currentCustomer->arrivalTimeBarista + currentCustomer->brewTime;
			currentCustomer->takeCoffeeTime = servingBarista->timeStart;
			servingBarista->baristaQueue->dequeue();
		}
		else {
			enqueueCustomersInBaristaQueue(servingBarista, servingBarista->timeStart);
			Customer* currentCustomer = servingBarista->baristaQueue->front;
			currentCustomer->waitingBaristaTime = servingBarista->timeStart - currentCustomer->arrivalTimeBarista;
			servingBarista->servingCustomer = currentCustomer;
			servingBarista->available = false;
			servingBarista->busyTimeOfUnit += currentCustomer->brewTime;
			servingBarista->timeStart = currentCustomer->arrivalTimeBarista + currentCustomer->waitingBaristaTime + currentCustomer->brewTime;
			currentCustomer->takeCoffeeTime = servingBarista->timeStart;
			servingBarista->baristaQueue->dequeue();
		}
	}

	// Customers are sorted by arrival times so the output is in order.
	std::sort(customers.begin(), customers.end(), compareByArrivalTimeSecond);
	totalRunningTime = findTotalRunningTime();
	maximumLengthOfCashierQueue = cashierQueue->maximumLength;
	calculateTurnaroundTimes();
	calculateUnitUtilizationTimes();
	writeOutput(outputFile);
}