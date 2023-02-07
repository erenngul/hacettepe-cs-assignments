#pragma once
#include "Customer.h"

// This queue class is a priority queue class.
class BaristaQueue {
public:
	Customer* front;
	Customer* rear;
	int length;
	int maximumLength;

	BaristaQueue();

	~BaristaQueue();

	void enqueue(Customer* customerOrder);

	void dequeue();
};