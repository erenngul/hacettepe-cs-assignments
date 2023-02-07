#include "CashierQueue.h"

CashierQueue::CashierQueue() : front(nullptr), rear(nullptr), length(0), maximumLength(0) {}

CashierQueue::~CashierQueue() {
	front = nullptr;
	rear = nullptr;
}

// This function enqueues customers from rear of queue.
void CashierQueue::enqueue(Customer* customer) {
	if (front == nullptr)
		front = rear = customer;
	else {
		rear->next = customer;
		rear = customer;
	}
	length++;
	if (length > maximumLength)
		maximumLength = length;
}

// This function dequeues customers from front of queue.
void CashierQueue::dequeue() {
	if (front == nullptr)
		return;
	else if (front == rear)
		front = rear = nullptr;
	else
		front = front->next;
	length--;
}