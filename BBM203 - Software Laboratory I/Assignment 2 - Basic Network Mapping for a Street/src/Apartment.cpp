#include <string>
#include "Apartment.h"

Apartment::Apartment(std::string apartment_name, int max_bandwidth) : apartment_name(apartment_name), max_bandwidth(max_bandwidth), flatCount(0), flatLinkedList(nullptr), next(nullptr) {}