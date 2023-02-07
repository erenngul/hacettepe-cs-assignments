#include <fstream>
#include <sstream>
#include <vector>
#include "FirstModel.h"
#include "SecondModel.h"

// This function reads the input file and puts the customer and order information in vectors.
std::vector<std::vector<std::string>> readInput(std::ifstream& inputFile) {
	std::string line;
	std::vector<std::vector<std::string>> inputVectors;
	while (getline(inputFile, line)) {
		std::stringstream inputs(line);
		std::string input;
		std::vector<std::string> inputVector;
		while (getline(inputs, input, ' '))
			inputVector.push_back(input);
		inputVectors.push_back(inputVector);
	}
	inputFile.close();
	return inputVectors;
}

// This function executes both of models.
void run(std::vector<std::vector<std::string>> inputVectors, std::ofstream& outputFile) {
	FirstModel* firstModel = new FirstModel(inputVectors);
	firstModel->runFirstModel(outputFile);
	delete firstModel;
	SecondModel* secondModel = new SecondModel(inputVectors);
	secondModel->runSecondModel(outputFile);
	delete secondModel;
}

int main(int argc, char** argv) {
	std::ifstream inputFile(argv[1]);
	std::vector<std::vector<std::string>> inputVectors = readInput(inputFile);
	std::ofstream outputFile(argv[2]);
	run(inputVectors, outputFile);
	outputFile.close();
	return 0;
}