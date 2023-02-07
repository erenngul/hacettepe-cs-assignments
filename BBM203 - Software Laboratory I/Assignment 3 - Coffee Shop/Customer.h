#pragma once

class Customer {
public:
	double arrivalTime;
	double orderTime;
	double brewTime;
	double priceOfOrder;
	double waitingCashierTime; // The time of customer waiting in cashier queue.
	double arrivalTimeBarista; // The time when the cashier finishes taking order of customer.
	double waitingBaristaTime; // The time of customer waiting in barista queue.
	double takeCoffeeTime; // The time when the barista finishes making the coffee.
	double turnaroundTime;
	int baristaIdToEnqueue; // This variable is for customer to go to which barista they are supposed to go for second model.
	bool enqueued; // This variable is true if customer is in queue.
	Customer* next;

	Customer(double arrivalTime, double orderTime, double brewTime, double priceOfOrder);

	~Customer();
};