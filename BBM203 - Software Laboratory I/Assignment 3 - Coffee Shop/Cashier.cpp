#include "Cashier.h"

Cashier::Cashier(int id) : id(id), busyTimeOfUnit(0), utilization(0), timeStart(0), available(true), servingCustomer(nullptr) {}

Cashier::~Cashier() {
	servingCustomer = nullptr;
}