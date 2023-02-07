#include <string>
#pragma once

class SecondaryNode {
public:
	std::string name;
	int price;
	int height;
	bool isRed;
	SecondaryNode* left;
	SecondaryNode* right;

	SecondaryNode(std::string name, int price);
};