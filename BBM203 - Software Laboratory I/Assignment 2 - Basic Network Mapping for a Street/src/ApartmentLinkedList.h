#pragma once
#include <string>
#include "Apartment.h"

class ApartmentLinkedList {
public:
	int length;
	Apartment* head;
	Apartment* tail;

	ApartmentLinkedList();

	void add_apartment(std::string apartment_name, std::string position, int max_bandwidth);

	int calculate_available_maximum_bandwidth(Apartment* apartment, int initial_bandwidth);

	void add_flat(std::string apartment_name, int index, int initial_bandwidth, int flat_id);

	ApartmentLinkedList* remove_apartment(std::string apartment_name);

	void make_flat_empty(std::string apartment_name, int flat_id);

	int find_sum_of_max_bandwidth(std::ofstream& outputFile);

	ApartmentLinkedList* merge_two_apartments(std::string apartment_name_1, std::string apartment_name_2);

	void relocate_flats_to_same_apartment(std::string new_apartment_name, int flat_id_to_shift, std::vector<int> flat_id_list);

	void list_apartments(std::ofstream& outputFile);
};