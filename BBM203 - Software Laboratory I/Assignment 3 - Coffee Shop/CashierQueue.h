#pragma once
#include "Customer.h"

// This queue class is a normal queue class.
class CashierQueue {
public:
	Customer* front;
	Customer* rear;
	int length;
	int maximumLength;

	CashierQueue();

	~CashierQueue();

	void enqueue(Customer* customer);

	void dequeue();
};