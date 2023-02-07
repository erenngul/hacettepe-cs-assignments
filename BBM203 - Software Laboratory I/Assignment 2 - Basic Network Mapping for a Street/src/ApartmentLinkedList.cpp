#include <sstream>
#include <string>
#include <vector>
#include <algorithm>
#include <fstream>
#include "ApartmentLinkedList.h"

ApartmentLinkedList::ApartmentLinkedList() : length(0), head(nullptr), tail(nullptr) {}

// This function adds a new apartment at required position in the apartment linked list.
void ApartmentLinkedList::add_apartment(std::string apartment_name, std::string position, int max_bandwidth) {
	Apartment* newApartment = new Apartment(apartment_name, max_bandwidth);
	if (position == "head") {
		head = newApartment;
		tail = head;
		tail->next = head;
	}
	else {
		std::stringstream positionString(position);
		std::string string;
		std::vector<std::string> positionVector;
		while (getline(positionString, string, '_')) // This part splits the position string by '_'
			positionVector.push_back(string);
		if (positionVector[0] == "before") {
			Apartment* currentNode = head;
			while (currentNode->next->apartment_name != positionVector[1])
				currentNode = currentNode->next;
			if (currentNode->next == head) {
				head = newApartment;
				head->next = currentNode;
				tail->next = head;
			}
			else {
				newApartment->next = currentNode->next;
				currentNode->next = newApartment;
			}
		}
		else if (positionVector[0] == "after") {
			Apartment* currentNode = head;
			while (currentNode->apartment_name != positionVector[1])
				currentNode = currentNode->next;
			if (currentNode == tail) {
				tail->next = newApartment;
				tail = newApartment;
				tail->next = head;
			}
			else {
				newApartment->next = currentNode->next;
				currentNode->next = newApartment;
			}
		}
	}
	length++;
}

// This function calculates the maximum bandwidth of the newly added flat can have.
int ApartmentLinkedList::calculate_available_maximum_bandwidth(Apartment* apartment, int initial_bandwidth) {
	int sumOfInitialBandwidths = 0;
	Flat* currentNode = apartment->flatLinkedList->head;
	while (currentNode != nullptr) {
		sumOfInitialBandwidths += currentNode->initial_bandwidth;
		currentNode = currentNode->next;
	}
	int availableMaximumBandwidth = apartment->max_bandwidth - sumOfInitialBandwidths;
	if (availableMaximumBandwidth <= 0)
		return 0;
	else if (availableMaximumBandwidth < initial_bandwidth)
		return availableMaximumBandwidth;
	else
		return initial_bandwidth;
}

// This function adds a new flat at required index in the flat linked list of the apartment whose name is given apartment_name.
void ApartmentLinkedList::add_flat(std::string apartment_name, int index, int initial_bandwidth, int flat_id) {
	Apartment* currentApartment = head;
	while (currentApartment->apartment_name != apartment_name)
		currentApartment = currentApartment->next;
	if (currentApartment->flatLinkedList == nullptr) // If current apartment does not have a flat linked list, creates new one.
		currentApartment->flatLinkedList = new FlatLinkedList();
	FlatLinkedList* flatLinkedList = currentApartment->flatLinkedList;

	int availableMaximumBandwidth = calculate_available_maximum_bandwidth(currentApartment, initial_bandwidth);
	Flat* newFlat = nullptr;
	if (availableMaximumBandwidth == 0)
		newFlat = new Flat(flat_id, 0, 1);
	else
		newFlat = new Flat(flat_id, availableMaximumBandwidth, 0);

	Flat* head = flatLinkedList->head;
	Flat* tail = flatLinkedList->tail;
	if (index == 0 && flatLinkedList->head == nullptr) {
		flatLinkedList->head = newFlat;
		flatLinkedList->tail = flatLinkedList->head;
	}
	else if (index == 0 && currentApartment->flatCount == 1) {
		newFlat->next = head;
		head->prev = newFlat;
		flatLinkedList->head = newFlat;
	}
	else if (index == 0) {
		newFlat->next = head;
		head->prev = newFlat;
		flatLinkedList->head = newFlat;
	}
	else if (index == currentApartment->flatCount) {
		tail->next = newFlat;
		newFlat->prev = tail;
		flatLinkedList->tail = newFlat;
	}
	else {
		Flat* currentNode = head;
		int counter = 0;
		while (counter < index - 1) {
			currentNode = currentNode->next;
			counter++;
		}
		newFlat->next = currentNode->next;
		newFlat->prev = currentNode;
		currentNode->next->prev = newFlat;
		currentNode->next = newFlat;
	}
	currentApartment->flatCount++;
}

// This function removes the apartment whose name is equal to given apartment name from the apartment linked list.
ApartmentLinkedList* ApartmentLinkedList::remove_apartment(std::string apartment_name) {
	Apartment* currentApartment = head;
	while (currentApartment->apartment_name != apartment_name)
		currentApartment = currentApartment->next;
	FlatLinkedList* flatLinkedList = currentApartment->flatLinkedList;
	if (flatLinkedList) {
		Flat* currentFlat = flatLinkedList->head;
		Flat* afterCurrentFlat = nullptr;
		while (currentFlat != nullptr) {
			afterCurrentFlat = currentFlat->next;
			delete currentFlat;
			currentFlat = afterCurrentFlat;
			if (currentFlat)
				afterCurrentFlat = currentFlat->next;
		}
		delete flatLinkedList;
	}
	if (currentApartment == head && currentApartment == tail) {
		head = nullptr;
		tail = nullptr;
	}
	else if (currentApartment == head) {
		head = currentApartment->next;
		tail->next = head;
	}
	else if (currentApartment == tail) {
		Apartment* beforeCurrentApartment = head;
		while (beforeCurrentApartment->next->apartment_name != apartment_name)
			beforeCurrentApartment = beforeCurrentApartment->next;
		beforeCurrentApartment->next = currentApartment->next;
		tail = beforeCurrentApartment;
	}
	else {
		Apartment* beforeCurrentApartment = head;
		while (beforeCurrentApartment->next->apartment_name != apartment_name)
			beforeCurrentApartment = beforeCurrentApartment->next;
		beforeCurrentApartment->next = currentApartment->next;
	}
	delete currentApartment;
	length--;
	if (length == 0) // If there is not any apartment in the apartment linked list, it returns nullptr.
		return nullptr;
	return this;
}

// This function should find the flat whose ID is equal to given flat_id of the apartment whose name is equal to given apartment name. It only changes its empty flag to 1 and initial bandwidth to 0.
void ApartmentLinkedList::make_flat_empty(std::string apartment_name, int flat_id) {
	Apartment* currentApartment = head;
	while (currentApartment->apartment_name != apartment_name)
		currentApartment = currentApartment->next;
	FlatLinkedList* flatLinkedList = currentApartment->flatLinkedList;
	Flat* currentFlat = flatLinkedList->head;
	while (currentFlat->flat_id != flat_id)
		currentFlat = currentFlat->next;
	currentFlat->initial_bandwidth = 0;
	currentFlat->is_empty = 1;
}

// This function sums the max bandwidth values of the apartments in the given apartment linked list, then returns the sum.
int ApartmentLinkedList::find_sum_of_max_bandwidth(std::ofstream& outputFile) {
	if (head == nullptr) {
		outputFile << "sum of bandwidth: 0\n\n";
		return 0;
	}
	else if (head == tail) {
		outputFile << "sum of bandwidth: " << head->max_bandwidth << "\n\n";
		return head->max_bandwidth;
	}
	else {
		Apartment* currentApartment = head;
		int sumOfMaxBandwidth = 0;
		while (currentApartment != tail) {
			sumOfMaxBandwidth += currentApartment->max_bandwidth;
			currentApartment = currentApartment->next;
		}
		sumOfMaxBandwidth += tail->max_bandwidth;
		outputFile << "sum of bandwidth: " << sumOfMaxBandwidth << "\n\n";
		return sumOfMaxBandwidth;
	}
}

// This function appends the flats of the second apartment whose name is apartment name 2 to the end of the first apartment whose name is apartment name 1.
ApartmentLinkedList* ApartmentLinkedList::merge_two_apartments(std::string apartment_name_1, std::string apartment_name_2) {
	Apartment* apartment1 = nullptr;
	Apartment* apartment2 = nullptr;
	Apartment* currentApartment = head;

	do {
		if (apartment_name_1 == currentApartment->apartment_name)
			apartment1 = currentApartment;

		else if (apartment_name_2 == currentApartment->apartment_name)
			apartment2 = currentApartment;

		currentApartment = currentApartment->next;
	} while (currentApartment != head);

	apartment1->max_bandwidth += apartment2->max_bandwidth;
	FlatLinkedList* flatLinkedList1 = apartment1->flatLinkedList;
	FlatLinkedList* flatLinkedList2 = apartment2->flatLinkedList;
	if (flatLinkedList1 == nullptr) {
		flatLinkedList1 = new FlatLinkedList();
		flatLinkedList1->head = flatLinkedList2->head;
		flatLinkedList1->tail = flatLinkedList2->tail;
	}
	else if (flatLinkedList1 != nullptr && flatLinkedList2 != nullptr) {
		flatLinkedList1->tail->next = flatLinkedList2->head;
		flatLinkedList2->head->prev = flatLinkedList1->tail;
		flatLinkedList1->tail = flatLinkedList2->tail;
	}
	apartment2->flatLinkedList = nullptr;
	apartment1->flatCount += apartment2->flatCount;
	remove_apartment(apartment_name_2);
	return this;
}

// This function relocates the different flats in different apartments to a specific place at the same apartment consecutively.
void ApartmentLinkedList::relocate_flats_to_same_apartment(std::string new_apartment_name, int flat_id_to_shift, std::vector<int> flat_id_list) {
	Apartment* newCurrentApartment = head;
	while (newCurrentApartment->apartment_name != new_apartment_name)
		newCurrentApartment = newCurrentApartment->next;
	FlatLinkedList* newFlatLinkedList = newCurrentApartment->flatLinkedList;
	Flat* flatToShift = newFlatLinkedList->head;
	while (flatToShift->flat_id != flat_id_to_shift)
		flatToShift = flatToShift->next;

	std::reverse(flat_id_list.begin(), flat_id_list.end()); // To preserve the order of flat list
	for (int id : flat_id_list) {
		Apartment* currentApartment = head;
		while (true) {
			FlatLinkedList* currentFlatLinkedList = currentApartment->flatLinkedList;
			if (currentFlatLinkedList != nullptr) {
				Flat* currentFlat = currentFlatLinkedList->head;
				while (currentFlat != nullptr && currentFlat->flat_id != id)
					currentFlat = currentFlat->next;
				if (currentFlat != nullptr) { // If currentFlat is found

					if (currentFlat == currentFlatLinkedList->head) {
						currentFlatLinkedList->head = currentFlat->next;
						currentFlatLinkedList->head->prev = nullptr;
					}
					else if (currentFlat == currentFlatLinkedList->tail) {
						currentFlatLinkedList->tail = currentFlat->prev;
						currentFlatLinkedList->tail->next = nullptr;
					}
					else {
						currentFlat->next->prev = currentFlat->prev;
						currentFlat->prev->next = currentFlat->next;
					}

					// Adds the current flat before the flat to shift.
					Flat* beforeFlatToShift = flatToShift->prev;
					flatToShift->prev = currentFlat;
					currentFlat->next = flatToShift;

					if (flatToShift == newFlatLinkedList->head) {
						currentFlat->prev = nullptr;
						newFlatLinkedList->head = currentFlat;
					}
					else {
						currentFlat->prev = beforeFlatToShift;
						if (beforeFlatToShift)
							beforeFlatToShift->next = currentFlat;
					}
					currentApartment->max_bandwidth -= currentFlat->initial_bandwidth;
					newCurrentApartment->max_bandwidth += currentFlat->initial_bandwidth;
					flatToShift = currentFlat;
					break;
				}
			}
			currentApartment = currentApartment->next;
		}
	}
}

// This function lists apartments and their flats with max_bandwidth value and initial_bandwidth values, respectively.
void ApartmentLinkedList::list_apartments(std::ofstream& outputFile) {
	Apartment* currentApartment = head;
	if (currentApartment) {
		do {
			outputFile << currentApartment->apartment_name << "(" << currentApartment->max_bandwidth << ")";
			FlatLinkedList* flatLinkedList = currentApartment->flatLinkedList;
			if (flatLinkedList) {
				Flat* currentFlat = flatLinkedList->head;
				while (currentFlat != nullptr) {
					outputFile << "\tFlat" << currentFlat->flat_id << "(" << currentFlat->initial_bandwidth << ")";
					currentFlat = currentFlat->next;
				}
			}
			outputFile << "\n";
			currentApartment = currentApartment->next;
		} while (currentApartment != head);
	}
	else
		outputFile << "There is no apartment\n";
	outputFile << "\n";
}
