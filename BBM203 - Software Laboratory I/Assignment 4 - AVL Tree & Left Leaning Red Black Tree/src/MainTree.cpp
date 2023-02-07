#include <queue>
#include <fstream>
#include "MainTree.h"

MainTree::MainTree() : root(nullptr) {}

// This function inserts a new primary node at appropriate location.
PrimaryNode* MainTree::insertPrimaryNode(PrimaryNode* currentPrimaryNode, std::string category) {
	if (!currentPrimaryNode)
		return new PrimaryNode(category);
	else if (category < currentPrimaryNode->category)
		currentPrimaryNode->left = insertPrimaryNode(currentPrimaryNode->left, category);
	else if (category > currentPrimaryNode->category)
		currentPrimaryNode->right = insertPrimaryNode(currentPrimaryNode->right, category);
	return currentPrimaryNode;
}

// This function finds the target category and inserts a new secondary node at appropriate location in AVL model.
void MainTree::insertAVL(std::string category, std::string name, int price) {
	root = insertPrimaryNode(root, category);
	PrimaryNode* insertedPrimaryNode = root;
	while (insertedPrimaryNode) {
		if (category == insertedPrimaryNode->category)
			break;
		else if (category < insertedPrimaryNode->category)
			insertedPrimaryNode = insertedPrimaryNode->left;
		else if (category > insertedPrimaryNode->category)
			insertedPrimaryNode = insertedPrimaryNode->right;
	}
	insertedPrimaryNode->rootSecondaryNode = insertedPrimaryNode->insertSecondaryNodeAVL(insertedPrimaryNode->rootSecondaryNode, name, price);
}

// This function finds the target category and removes the target secondary node in AVL model.
void MainTree::removeAVL(std::string category, std::string name) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}
	currentPrimaryNode->rootSecondaryNode = currentPrimaryNode->removeSecondaryNodeAVL(currentPrimaryNode->rootSecondaryNode, name);
}

// This function finds the target category and inserts a new secondary node at appropriate location in LLRB model.
void MainTree::insertLLRB(std::string category, std::string name, int price) {
	root = insertPrimaryNode(root, category);
	PrimaryNode* insertedPrimaryNode = root;
	while (insertedPrimaryNode) {
		if (category == insertedPrimaryNode->category)
			break;
		else if (category < insertedPrimaryNode->category)
			insertedPrimaryNode = insertedPrimaryNode->left;
		else if (category > insertedPrimaryNode->category)
			insertedPrimaryNode = insertedPrimaryNode->right;
	}
	insertedPrimaryNode->rootSecondaryNode = insertedPrimaryNode->insertSecondaryNodeLLRB(insertedPrimaryNode->rootSecondaryNode, name, price);
	insertedPrimaryNode->rootSecondaryNode->isRed = false;
}

// This function finds the target category and removes the target secondary node in LLRB model.
void MainTree::removeLLRB(std::string category, std::string name) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}
	currentPrimaryNode->rootSecondaryNode = currentPrimaryNode->removeSecondaryNodeLLRB(currentPrimaryNode->rootSecondaryNode, name);
	if (currentPrimaryNode->rootSecondaryNode)
		currentPrimaryNode->rootSecondaryNode->isRed = false;
}

// This function prints nodes of both primary tree and secondary tree level by level.
void MainTree::printAllItems(std::ofstream& outputFile) {
	if (!root) {
		outputFile << "{}\n";
		return;
	}
	outputFile << "command:printAllItems\n{\n";
	std::queue<PrimaryNode*> primaryNodeQueue;
	primaryNodeQueue.push(root);

	while (primaryNodeQueue.size() > 0) {
		PrimaryNode* currentPrimaryNode = primaryNodeQueue.front();

		if (currentPrimaryNode->rootSecondaryNode) {
			outputFile << "\"" << currentPrimaryNode->category << "\":\n\t";

			std::queue<SecondaryNode*> secondaryNodeQueue;
			secondaryNodeQueue.push(currentPrimaryNode->rootSecondaryNode);

			while (secondaryNodeQueue.size() > 0) {

				size_t secondaryNodeCount = secondaryNodeQueue.size();

				while (secondaryNodeCount > 0) {
					SecondaryNode* currentSecondaryNode = secondaryNodeQueue.front();

					outputFile << "\"" << currentSecondaryNode->name << "\":\"" << currentSecondaryNode->price << "\"";
					secondaryNodeQueue.pop();

					if (currentSecondaryNode->left)
						secondaryNodeQueue.push(currentSecondaryNode->left);
					if (currentSecondaryNode->right)
						secondaryNodeQueue.push(currentSecondaryNode->right);

					secondaryNodeCount--;

					if (secondaryNodeCount > 0)
						outputFile << ",";
					else if (secondaryNodeQueue.empty())
						outputFile << "\n";
					else
						outputFile << "\n\t";

				}
			}
		}
		else
			outputFile << "\"" << currentPrimaryNode->category << "\":{}\n";

		primaryNodeQueue.pop();
		if (currentPrimaryNode->left)
			primaryNodeQueue.push(currentPrimaryNode->left);
		if (currentPrimaryNode->right)
			primaryNodeQueue.push(currentPrimaryNode->right);
	}
	outputFile << "}\n";
}

// This function prints information that belongs to all item objects in the tree with the given category parameter.
void MainTree::printAllItemsInCategory(std::string category, std::ofstream& outputFile) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}

	if (currentPrimaryNode->rootSecondaryNode) {
		outputFile << "command:printAllItemsInCategory\t" << category << "\n{\n\"" << category << "\":\n\t";

		std::queue<SecondaryNode*> secondaryNodeQueue;
		secondaryNodeQueue.push(currentPrimaryNode->rootSecondaryNode);
		while (secondaryNodeQueue.size() > 0) {
			size_t secondaryNodeCount = secondaryNodeQueue.size();
			while (secondaryNodeCount > 0) {
				SecondaryNode* currentSecondaryNode = secondaryNodeQueue.front();
				outputFile << "\"" << currentSecondaryNode->name << "\":\"" << currentSecondaryNode->price << "\"";
				secondaryNodeQueue.pop();

				if (currentSecondaryNode->left)
					secondaryNodeQueue.push(currentSecondaryNode->left);
				if (currentSecondaryNode->right)
					secondaryNodeQueue.push(currentSecondaryNode->right);

				secondaryNodeCount--;

				if (secondaryNodeCount > 0)
					outputFile << ",";
				else if (secondaryNodeQueue.empty())
					outputFile << "\n";
				else
					outputFile << "\n\t";

			}
		}
	}
	else
		outputFile << "command:printAllItemsInCategory\t" << category << "\n{\n\"" << category << "\":{}\n";
	outputFile << "}\n";
}

// This function prints information of a single item object that is specified by the given category and name parameters.
void MainTree::printItem(std::string category, std::string name, std::ofstream& outputFile) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}
	if (!currentPrimaryNode) {
		outputFile << "command:printItem\t" << category << "\t" << name << "\n";
		outputFile << "{}\n";
		return;
	}
	SecondaryNode* currentSecondaryNode = currentPrimaryNode->rootSecondaryNode;
	while (currentSecondaryNode) {
		if (name == currentSecondaryNode->name)
			break;
		else if (name < currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->left;
		else if (name > currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->right;
	}
	if (!currentSecondaryNode) {
		outputFile << "command:printItem\t" << category << "\t" << name << "\n";
		outputFile << "{}\n";
		return;
	}
	outputFile << "command:printItem\t" << category << "\t" << name << "\n{\n";
	outputFile << "\"" << category << "\":\n\t";
	outputFile << "\"" << name << "\":\"" << currentSecondaryNode->price << "\"\n}\n";
}

// This function prints information of a single item object that is specified by the given category and name parameters. Similar with printItem function.
void MainTree::find(std::string category, std::string name, std::ofstream& outputFile) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}
	if (!currentPrimaryNode) {
		outputFile << "command:find\t" << category << "\t" << name << "\n";
		outputFile << "{}\n";
		return;
	}
	SecondaryNode* currentSecondaryNode = currentPrimaryNode->rootSecondaryNode;
	while (currentSecondaryNode) {
		if (name == currentSecondaryNode->name)
			break;
		else if (name < currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->left;
		else if (name > currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->right;
	}
	if (!currentSecondaryNode) {
		outputFile << "command:find\t" << category << "\t" << name << "\n";
		outputFile << "{}\n";
		return;
	}
	outputFile << "command:find\t" << category << "\t" << name << "\n{\n";
	outputFile << "\"" << category << "\":\n\t";
	outputFile << "\"" << name << "\":\"" << currentSecondaryNode->price << "\"\n}\n";
}

// This function updates the price of target secondary node.
void MainTree::updateData(std::string category, std::string name, int newPrice) {
	PrimaryNode* currentPrimaryNode = root;
	while (currentPrimaryNode) {
		if (category == currentPrimaryNode->category)
			break;
		else if (category < currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->left;
		else if (category > currentPrimaryNode->category)
			currentPrimaryNode = currentPrimaryNode->right;
	}
	SecondaryNode* currentSecondaryNode = currentPrimaryNode->rootSecondaryNode;
	while (currentSecondaryNode) {
		if (name == currentSecondaryNode->name)
			break;
		else if (name < currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->left;
		else if (name > currentSecondaryNode->name)
			currentSecondaryNode = currentSecondaryNode->right;
	}
	currentSecondaryNode->price = newPrice;
}
