#include "SecondaryNode.h"

SecondaryNode::SecondaryNode(std::string name, int price) : name(name), price(price), height(1), isRed(true), left(nullptr), right(nullptr) {}