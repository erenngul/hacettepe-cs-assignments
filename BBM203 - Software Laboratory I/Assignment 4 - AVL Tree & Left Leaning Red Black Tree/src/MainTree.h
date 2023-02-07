#include "PrimaryNode.h"
#pragma once

class MainTree {
public:
	PrimaryNode* root;

	MainTree();

	// This function inserts a new primary node at appropriate location.
	PrimaryNode* insertPrimaryNode(PrimaryNode* currentPrimaryNode, std::string category);

	// This function finds the target category and inserts a new secondary node at appropriate location in AVL model.
	void insertAVL(std::string category, std::string name, int price);

	// This function finds the target category and removes the target secondary node in AVL model.
	void removeAVL(std::string category, std::string name);

	// This function finds the target category and inserts a new secondary node at appropriate location in LLRB model. 
	void insertLLRB(std::string category, std::string name, int price);

	// This function finds the target category and removes the target secondary node in LLRB model.
	void removeLLRB(std::string category, std::string name);

	// This function prints nodes of both primary tree and secondary tree level by level.
	void printAllItems(std::ofstream& outputFile);

	// This function prints information that belongs to all item objects in the tree with the given category parameter.
	void printAllItemsInCategory(std::string category, std::ofstream& outputFile);

	// This function prints information of a single item object that is specified by the given category and name parameters.
	void printItem(std::string category, std::string name, std::ofstream& outputFile);

	// This function prints information of a single item object that is specified by the given category and name parameters. Similar with printItem function.
	void find(std::string category, std::string name, std::ofstream& outputFile);

	// This function updates the price of target secondary node.
	void updateData(std::string category, std::string name, int newPrice);
};