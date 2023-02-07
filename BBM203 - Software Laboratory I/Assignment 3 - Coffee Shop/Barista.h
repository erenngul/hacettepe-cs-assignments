#pragma once
#include "Customer.h"
#include "BaristaQueue.h"

class Barista {
public:
	double busyTimeOfUnit;
	double utilization;
	double timeStart; // Shows when the cashier will be available.
	bool available;
	Customer* servingCustomer;
	BaristaQueue* baristaQueue;

	// This constructor is used in first model.
	Barista();

	// This constructor is used in second model.
	Barista(BaristaQueue* baristaQueue);

	~Barista();
};