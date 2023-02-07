#include "Barista.h"

// This constructor is used in first model.
Barista::Barista() : busyTimeOfUnit(0), utilization(0), timeStart(0), available(true), servingCustomer(nullptr), baristaQueue(nullptr) {}

// This constructor is used in second model.
Barista::Barista(BaristaQueue* baristaQueue) : busyTimeOfUnit(0), utilization(0), timeStart(0), available(true), servingCustomer(nullptr), baristaQueue(new BaristaQueue()) {}

Barista::~Barista() {
	servingCustomer = nullptr;
	delete baristaQueue;
	baristaQueue = nullptr;
}