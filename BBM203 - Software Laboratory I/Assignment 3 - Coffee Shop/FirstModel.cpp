#include <algorithm>
#include <fstream>
#include <iomanip>
#include "FirstModel.h"

// This constructor reads the input vectors; creates cashiers, baristas and customers.
FirstModel::FirstModel(std::vector<std::vector<std::string>> inputVectors) : totalRunningTime(0), maximumLengthOfCashierQueue(0), maximumLengthOfBaristaQueue(0), cashierQueue(new CashierQueue()), baristaQueue(new BaristaQueue()) {
	int numberOfCashiers = stoi(inputVectors[0][0]);
	int numberOfOrders = stoi(inputVectors[1][0]);
	for (int i = 0; i < numberOfCashiers; i++)
		cashiers.push_back(new Cashier(i));
	for (int i = 0; i < numberOfCashiers / 3; i++)
		baristas.push_back(new Barista());
	for (int i = 2; i < numberOfOrders + 2; i++)
		customers.push_back(new Customer(stod(inputVectors[i][0]), stod(inputVectors[i][1]), stod(inputVectors[i][2]), stod(inputVectors[i][3])));
}

// This destructor deletes the properties in the model.
FirstModel::~FirstModel() {
	delete baristaQueue;
	baristaQueue = nullptr;
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
Cashier* FirstModel::findAvailableCashier(Customer* customer) {
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
Cashier* FirstModel::findNextAvailableCashier() {
	Cashier* leastTimeStartCashier = cashiers[0];
	for (Cashier* cashier : cashiers) {
		if (cashier->timeStart < leastTimeStartCashier->timeStart)
			leastTimeStartCashier = cashier;
	}
	return leastTimeStartCashier;
}

// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
void FirstModel::enqueueCustomersInCashierQueue(double nextAvailableCashierTimeStart) {
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

// This function finds the next available barista and returns that barista. If there is not any available barista, baristas are checked if any of them can be available for the current customer.
Barista* FirstModel::findAvailableBarista(Customer* customer) {
	for (Barista* barista : baristas) {
		if (!barista->servingCustomer)
			break;
		if (barista->timeStart <= customer->arrivalTimeBarista + customer->waitingBaristaTime) {
			barista->servingCustomer->takeCoffeeTime = barista->timeStart;
			barista->available = true;
			barista->servingCustomer = nullptr;
		}
	}
	for (Barista* barista : baristas) {
		if (barista->available)
			return barista;
	}
	return nullptr;
}

// This function returns the closest barista to be available if there isn't any available barista.
Barista* FirstModel::findNextAvailableBarista() {
	Barista* leastTimeStartBarista = baristas[0];
	for (Barista* barista : baristas) {
		if (barista->timeStart < leastTimeStartBarista->timeStart)
			leastTimeStartBarista = barista;
	}
	return leastTimeStartBarista;
}

// This function enqueues customers beforehand arriving before the next available cashier finishes taking order.
void FirstModel::enqueueCustomersInBaristaQueue(double nextAvailableBaristaTimeStart) {
	for (Customer* customer : customers) {
		if (customer->enqueued)
			continue;
		if (customer->arrivalTimeBarista < nextAvailableBaristaTimeStart) {
			baristaQueue->enqueue(customer);
			customer->enqueued = true;
		}
		else
			break;
	}
}

// This function turns enqueued property to false in all customers.
void FirstModel::resetEnqueued() {
	for (Customer* customer : customers)
		customer->enqueued = false;
}

// This function finds the total running time of the model.
double FirstModel::findTotalRunningTime() {
	double maximumTakeCoffeeTime = customers[0]->takeCoffeeTime;
	for (Customer* customer : customers) {
		if (customer->takeCoffeeTime > maximumTakeCoffeeTime)
			maximumTakeCoffeeTime = customer->takeCoffeeTime;
	}
	return maximumTakeCoffeeTime;
}

// This function calculates the turnaround times of customers.
void FirstModel::calculateTurnaroundTimes() {
	for (Customer* customer : customers)
		customer->turnaroundTime = customer->takeCoffeeTime - customer->arrivalTime;
}

// This function calculates the unit utilization times of cashiers and baristas in the model.
void FirstModel::calculateUnitUtilizationTimes() {
	for (Cashier* cashier : cashiers)
		cashier->utilization = cashier->busyTimeOfUnit / totalRunningTime;
	for (Barista* barista : baristas)
		barista->utilization = barista->busyTimeOfUnit / totalRunningTime;
}

// This function writes the output to output file.
void FirstModel::writeOutput(std::ofstream& outputFile) {
	outputFile << std::fixed << std::setprecision(2) << totalRunningTime << "\n";
	outputFile << maximumLengthOfCashierQueue << "\n";
	outputFile << maximumLengthOfBaristaQueue << "\n";
	for (Cashier* cashier : cashiers)
		outputFile << std::setprecision(2) << cashier->utilization << "\n";
	for (Barista* barista : baristas)
		outputFile << std::setprecision(2) << barista->utilization << "\n";
	for (Customer* customer : customers)
		outputFile << std::setprecision(2) << customer->turnaroundTime << "\n";
	outputFile << "\n";
}

// This function is a key for comparing customers according to their arrival time to baristas.
bool compareByArrivalTimeBarista(Customer* customer1, Customer* customer2) {
	return customer1->arrivalTimeBarista < customer2->arrivalTimeBarista;
}

// This function is a key for comparing customers according to their arrival times.
bool compareByArrivalTime(Customer* customer1, Customer* customer2) {
	return customer1->arrivalTime < customer2->arrivalTime;
}

// This function is the main function for this class. All orders are taken from cashiers first and then the baristas prepare the orders.
void FirstModel::runFirstModel(std::ofstream& outputFile) {
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
		}
	}

	// Customers are sorted by arrival time to baristas so the customer who first finishes giving order is iterated in the next part first.
	std::sort(customers.begin(), customers.end(), compareByArrivalTimeBarista);
	resetEnqueued();

	// Baristas prepare the orders in this part.
	for (Customer* customer : customers) {
		if (!customer->enqueued) {
			baristaQueue->enqueue(customer);
			customer->enqueued = true;
		}
		Barista* availableBarista = findAvailableBarista(customer);
		if (availableBarista) {
			Customer* currentCustomer = baristaQueue->front;
			availableBarista->servingCustomer = currentCustomer;
			availableBarista->available = false;
			availableBarista->busyTimeOfUnit += currentCustomer->brewTime;
			availableBarista->timeStart = currentCustomer->arrivalTimeBarista + currentCustomer->brewTime;
			currentCustomer->takeCoffeeTime = availableBarista->timeStart;
			baristaQueue->dequeue();
		}
		else {
			Barista* nextAvailableBarista = findNextAvailableBarista();
			enqueueCustomersInBaristaQueue(nextAvailableBarista->timeStart);
			Customer* currentCustomer = baristaQueue->front;
			currentCustomer->waitingBaristaTime = nextAvailableBarista->timeStart - currentCustomer->arrivalTimeBarista;
			nextAvailableBarista->servingCustomer = currentCustomer;
			nextAvailableBarista->available = false;
			nextAvailableBarista->busyTimeOfUnit += currentCustomer->brewTime;
			nextAvailableBarista->timeStart = currentCustomer->arrivalTimeBarista + currentCustomer->waitingBaristaTime + currentCustomer->brewTime;
			currentCustomer->takeCoffeeTime = nextAvailableBarista->timeStart;
			baristaQueue->dequeue();
		}
	}

	// Customers are sorted by arrival times so the output is in order.
	std::sort(customers.begin(), customers.end(), compareByArrivalTime);
	totalRunningTime = findTotalRunningTime();
	maximumLengthOfCashierQueue = cashierQueue->maximumLength;
	maximumLengthOfBaristaQueue = baristaQueue->maximumLength;
	calculateTurnaroundTimes();
	calculateUnitUtilizationTimes();
	writeOutput(outputFile);
}