#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

// This function reads the input file and dynamically allocates the matrix. Returns matrix.
int** readFile(int rowSize, int columnSize, char* mapMatrix) {
	int** matrix = new int* [rowSize]; // Dynamically allocates rows
	for (int row = 0; row < rowSize; row++) {
		matrix[row] = new int[columnSize]; // Dynamically allocates columns
	}
	std::ifstream mapMatrixFile(mapMatrix);
	std::string line;
	int rowIndex = 0;
	// Reads map matrix line by line
	while (getline(mapMatrixFile, line)) {
		std::stringstream row(line);
		std::string number;
		int columnIndex = 0;
		// Splits the line by space and assigns numbers to the matrix
		while (getline(row, number, ' ')) {
			matrix[rowIndex][columnIndex] = std::stoi(number);
			if (columnIndex < columnSize - 1)
				columnIndex++;
		}
		if (rowIndex < rowSize - 1)
			rowIndex++;
	}
	mapMatrixFile.close();
	return matrix;
}

// This function multiplies the key matrix by the obtained sub-matrix and takes mod five of the multiplication result. Returns final result.
int multiplicateMatrices(int** keyMatrix, int keyMatrixSize, int** mapMatrix, int rowIndex, int columnIndex, std::ofstream& outputFile) {
	int sum = 0;
	int keyMatrixRow = 0;
	for (int row = rowIndex - keyMatrixSize / 2; row <= rowIndex + keyMatrixSize / 2; row++) {
		int keyMatrixColumn = 0;
		for (int column = columnIndex - keyMatrixSize / 2; column <= columnIndex + keyMatrixSize / 2; column++) {
			int multiplication = keyMatrix[keyMatrixRow][keyMatrixColumn] * mapMatrix[row][column];
			sum += multiplication;
			keyMatrixColumn++;
		}
		keyMatrixRow++;
	}
	while (sum < 0)
		sum += 5;
	outputFile << rowIndex << ',' << columnIndex << ':' << sum << std::endl;
	int result = sum % 5;
	return result;
}

// This recursive function uses the result of multiplication of matrices to find the direction of the next sliding.
void search(int** keyMatrix, int keyMatrixSize, int** mapMatrix, int* mapMatrixSizeArray, int rowIndex, int columnIndex, std::ofstream& outputFile) {
	int result = multiplicateMatrices(keyMatrix, keyMatrixSize, mapMatrix, rowIndex, columnIndex, outputFile);

	if (result == 0) // Found treasure
		return;

	int mapMatrixRowSize = mapMatrixSizeArray[0];
	int mapMatrixColumnSize = mapMatrixSizeArray[1];

	// If key matrix is on the boundary of the map where there is no way to slide through the determined direction, it moves in the opposite direction.
	if (result == 1 && rowIndex == keyMatrixSize / 2)
		result = 2;
	else if (result == 2 && rowIndex == mapMatrixRowSize - (keyMatrixSize / 2) - 1)
		result = 1;
	else if (result == 3 && columnIndex == mapMatrixColumnSize - (keyMatrixSize / 2) - 1)
		result = 4;
	else if (result == 4 && columnIndex == keyMatrixSize / 2)
		result = 3;

	switch (result) {
	case 1: // Go up
		search(keyMatrix, keyMatrixSize, mapMatrix, mapMatrixSizeArray, rowIndex - keyMatrixSize, columnIndex, outputFile);
		break;

	case 2: // Go down
		search(keyMatrix, keyMatrixSize, mapMatrix, mapMatrixSizeArray, rowIndex + keyMatrixSize, columnIndex, outputFile);
		break;

	case 3: // Go right
		search(keyMatrix, keyMatrixSize, mapMatrix, mapMatrixSizeArray, rowIndex, columnIndex + keyMatrixSize, outputFile);
		break;

	case 4: // Go left
		search(keyMatrix, keyMatrixSize, mapMatrix, mapMatrixSizeArray, rowIndex, columnIndex - keyMatrixSize, outputFile);
		break;
	}
}

// This function splits the matrix size by 'x' and adds the row size and column size to an array. Returns this matrix size array.
int* splitMatrixSize(char* matrixSize) {
	int* matrixSizeArray = new int[2];
	std::stringstream matrixSizeStream(matrixSize);
	std::string matrixSizeElement;
	int index = 0;
	while (getline(matrixSizeStream, matrixSizeElement, 'x')) {
		matrixSizeArray[index] = std::stoi(matrixSizeElement);
		index++;
	}
	return matrixSizeArray;
 }

// This function deletes the dynamically allocated matrix.
void deleteDynamicMatrix(int** dynamicArray, int numberOfArrays) {
	for (int i = 0; i < numberOfArrays; i++)
		delete[] dynamicArray[i];
	delete[] dynamicArray;
}

// This is the main function of the program.
int main(int argc, char** argv) {
	int* mapMatrixSizeArray = splitMatrixSize(argv[1]); // for example 15x20 -> {15, 20}
	int** mapMatrix = readFile(mapMatrixSizeArray[0], mapMatrixSizeArray[1], argv[3]);
	int** keyMatrix = readFile(std::stoi(argv[2]), std::stoi(argv[2]), argv[4]);
	std::ofstream outputFile("output.txt");
	search(keyMatrix, std::stoi(argv[2]), mapMatrix, mapMatrixSizeArray, std::stoi(argv[2]) / 2, std::stoi(argv[2]) / 2, outputFile);
	delete[] mapMatrixSizeArray;
	outputFile.close();
	deleteDynamicMatrix(mapMatrix, std::stoi(argv[1]));
	deleteDynamicMatrix(keyMatrix, std::stoi(argv[2]));
	return 0;
}