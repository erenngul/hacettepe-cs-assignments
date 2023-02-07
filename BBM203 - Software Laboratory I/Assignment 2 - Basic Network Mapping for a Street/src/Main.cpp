#include <fstream>
#include <sstream>
#include <vector>
#include "ApartmentLinkedList.h"

// Reads input file and gives the commands in a vector of vectors.
std::vector<std::vector<std::string>> readInput(std::ifstream& inputFile) {
	std::string line;
	std::vector<std::vector<std::string>> commandVectors;
	while (getline(inputFile, line)) {
		std::stringstream commands(line);
		std::string command;
		std::vector<std::string> commandVector;
		while (getline(commands, command, '\t'))
			commandVector.push_back(command);
		commandVectors.push_back(commandVector);
	}
	inputFile.close();
	return commandVectors;
}

// This function reads the command vectors and executes the functions according to the functions in it.
void run(std::vector<std::vector<std::string>>& commandVectors, std::ofstream& outputFile) {
	ApartmentLinkedList* apartmentLinkedList = new ApartmentLinkedList();
	for (std::vector<std::string>& commandVector : commandVectors) {

		if (commandVector[0] == "add_apartment")
			apartmentLinkedList->add_apartment(commandVector[1], commandVector[2], std::stoi(commandVector[3]));
		else if (commandVector[0] == "add_flat")
			apartmentLinkedList->add_flat(commandVector[1], std::stoi(commandVector[2]), std::stoi(commandVector[3]), std::stoi(commandVector[4]));
		else if (commandVector[0] == "remove_apartment")
			apartmentLinkedList->remove_apartment(commandVector[1]);
		else if (commandVector[0] == "make_flat_empty")
			apartmentLinkedList->make_flat_empty(commandVector[1], std::stoi(commandVector[2]));
		else if (commandVector[0] == "find_sum_of_max_bandwidths")
			apartmentLinkedList->find_sum_of_max_bandwidth(outputFile);
		else if (commandVector[0] == "merge_two_apartments")
			apartmentLinkedList->merge_two_apartments(commandVector[1], commandVector[2]);
		else if (commandVector[0] == "relocate_flats_to_same_apartment") {
			std::string command = commandVector[3].substr(1, commandVector[3].size() - 2);
			std::stringstream commandLine(command);
			std::string commandString;
			std::vector<int> flat_id_list;
			while (getline(commandLine, commandString, ','))
				flat_id_list.push_back(std::stoi(commandString));
			apartmentLinkedList->relocate_flats_to_same_apartment(commandVector[1], std::stoi(commandVector[2]), flat_id_list);
		}
		else if (commandVector[0] == "list_apartments")
			apartmentLinkedList->list_apartments(outputFile);

	}
	Apartment* currentApartment = apartmentLinkedList->head;
	Apartment* beforeCurrentApartment = nullptr;
	// In the end, all apartments are deleted to free the memory.
	do {
		beforeCurrentApartment = currentApartment;
		currentApartment = currentApartment->next;
		apartmentLinkedList = apartmentLinkedList->remove_apartment(beforeCurrentApartment->apartment_name);
	} while (currentApartment != apartmentLinkedList->head);
	delete apartmentLinkedList;
}


int main(int argc, char** argv) {
	std::ifstream inputFile(argv[1]);
	std::vector<std::vector<std::string>> commandVectors = readInput(inputFile);
	std::ofstream outputFile(argv[2]);
	run(commandVectors, outputFile);
	outputFile.close();
	return 0;
}