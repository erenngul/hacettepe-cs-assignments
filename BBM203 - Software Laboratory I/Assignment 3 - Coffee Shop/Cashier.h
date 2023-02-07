#pragma once
#include "Customer.h"

class Cashier {
public:
	int id;
	double busyTimeOfUnit;
	double utilization;
	double timeStart; // Shows when the cashier will be available.
	bool available;
	Customer* servingCustomer;

	Cashier(int id);

	~Cashier();
};