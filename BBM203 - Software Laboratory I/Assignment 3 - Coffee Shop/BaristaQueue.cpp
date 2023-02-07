#include "BaristaQueue.h"

BaristaQueue::BaristaQueue() : front(nullptr), rear(nullptr), length(0), maximumLength(0) {}

BaristaQueue::~BaristaQueue() {
	front = nullptr;
	rear = nullptr;
}

// This function enqueues customer orders according to their price of order.
void BaristaQueue::enqueue(Customer* customerOrder) {
	if (front == nullptr)
		front = rear = customerOrder;
	else {
		Customer* currentOrder = front;
		Customer* beforeCurrentOrder = nullptr;
		bool enqueued = false;
		while (currentOrder != nullptr) {
			if (customerOrder->priceOfOrder > currentOrder->priceOfOrder) {
				enqueued = true;
				customerOrder->next = currentOrder;
				if (currentOrder == front) {
					front = customerOrder;
					break;
				}
				beforeCurrentOrder->next = customerOrder;
				break;
			}
			beforeCurrentOrder = currentOrder;
			currentOrder = currentOrder->next;
		}
		if (!enqueued) {
			rear->next = customerOrder;
			rear = customerOrder;
		}
	}
	length++;
	if (length > maximumLength)
		maximumLength = length;
}

// This function dequeues customers from front of priority queue.
void BaristaQueue::dequeue() {
	if (front == nullptr)
		return;
	else if (front == rear)
		front = rear = nullptr;
	else
		front = front->next;
	length--;
}