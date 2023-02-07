#pragma once
#include <string>
#include "FlatLinkedList.h"

class Apartment {
public:
	std::string apartment_name;
	int max_bandwidth;
	int flatCount;
	FlatLinkedList* flatLinkedList;
	Apartment* next;

	Apartment(std::string apartment_name, int max_bandwidth);
};