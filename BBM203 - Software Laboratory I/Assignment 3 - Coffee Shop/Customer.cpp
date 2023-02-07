#include "Customer.h"

Customer::Customer(double arrivalTime, double orderTime, double brewTime, double priceOfOrder) : arrivalTime(arrivalTime), orderTime(orderTime), brewTime(brewTime), priceOfOrder(priceOfOrder), waitingCashierTime(0), arrivalTimeBarista(0), waitingBaristaTime(0), takeCoffeeTime(0), turnaroundTime(0), baristaIdToEnqueue(0), enqueued(false), next(nullptr) {}

Customer::~Customer() {
	next = nullptr;
}