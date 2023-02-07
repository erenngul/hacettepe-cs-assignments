#include <fstream>
#include <sstream>
#include <vector>
#include "MainTree.h"

// This function reads the input file and puts the commands in vectors.
std::vector<std::vector<std::string>> readInput(std::ifstream& inputFile) {
	std::string line;
	std::vector<std::vector<std::string>> inputVectors;
	while (getline(inputFile, line)) {
		std::stringstream inputs(line);
		std::string input;
		std::vector<std::string> inputVector;
		while (getline(inputs, input, '\t'))
			inputVector.push_back(input);
		inputVectors.push_back(inputVector);
	}
	inputFile.close();
	return inputVectors;
}

// This function executes both first part and second part.
void run(char* inputFileName, char* firstOutputFileName, char* secondOutputFileName) {
	std::ifstream inputFile(inputFileName);
	std::vector<std::vector<std::string>> inputVectors = readInput(inputFile);
	std::ofstream firstOutputFile(firstOutputFileName);
	std::ofstream secondOutputFile(secondOutputFileName);
	MainTree* mainTreeAVL = new MainTree();
	MainTree* mainTreeLLRB = new MainTree();
	for (std::vector<std::string>& inputVector : inputVectors) {
		if (inputVector[0] == "insert") {
			mainTreeAVL->insertAVL(inputVector[1], inputVector[2], std::stoi(inputVector[3]));
			mainTreeLLRB->insertLLRB(inputVector[1], inputVector[2], std::stoi(inputVector[3]));
		}
		else if (inputVector[0] == "remove") {
			mainTreeAVL->removeAVL(inputVector[1], inputVector[2]);
			mainTreeLLRB->removeLLRB(inputVector[1], inputVector[2]);
		}
		else if (inputVector[0] == "printAllItems") {
			mainTreeAVL->printAllItems(firstOutputFile);
			mainTreeLLRB->printAllItems(secondOutputFile);
		}
		else if (inputVector[0] == "printAllItemsInCategory") {
			mainTreeAVL->printAllItemsInCategory(inputVector[1], firstOutputFile);
			mainTreeLLRB->printAllItemsInCategory(inputVector[1], secondOutputFile);
		}
		else if (inputVector[0] == "printItem") {
			mainTreeAVL->printItem(inputVector[1], inputVector[2], firstOutputFile);
			mainTreeLLRB->printItem(inputVector[1], inputVector[2], secondOutputFile);
		}
		else if (inputVector[0] == "find") {
			mainTreeAVL->find(inputVector[1], inputVector[2], firstOutputFile);
			mainTreeLLRB->find(inputVector[1], inputVector[2], secondOutputFile);
		}
		else if (inputVector[0] == "updateData") {
			mainTreeAVL->updateData(inputVector[1], inputVector[2], std::stoi(inputVector[3]));
			mainTreeLLRB->updateData(inputVector[1], inputVector[2], std::stoi(inputVector[3]));
		}
	}
	firstOutputFile.close();
	secondOutputFile.close();
}


int main(int argc, char** argv) {
	run(argv[1], argv[2], argv[3]);
	return 0;
}