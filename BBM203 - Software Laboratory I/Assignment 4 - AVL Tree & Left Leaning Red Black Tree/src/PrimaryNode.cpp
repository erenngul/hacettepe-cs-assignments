#include "PrimaryNode.h"

PrimaryNode::PrimaryNode(std::string category) : category(category), left(nullptr), right(nullptr), rootSecondaryNode(nullptr) {}

// This function returns the height of the secondary node.
int PrimaryNode::height(SecondaryNode* secondaryNode) {
	if (!secondaryNode)
		return 0;
	return secondaryNode->height;
}

// This function compares the subtrees and returns the subtree with higher height.
int PrimaryNode::maxHeight(int heightOfLeftSubtree, int heightOfRightSubtree) {
	return heightOfLeftSubtree > heightOfRightSubtree ? heightOfLeftSubtree : heightOfRightSubtree;
}

// This function returns the balance factor of the secondary node.
int PrimaryNode::getBalanceFactor(SecondaryNode* secondaryNode) {
	return height(secondaryNode->left) - height(secondaryNode->right);
}

// This function rotates the secondary node to right and updates the heights of rotated nodes in AVL model.
SecondaryNode* PrimaryNode::rotateRightAVL(SecondaryNode* secondaryNode) {
	SecondaryNode* leftOfSecondaryNode = secondaryNode->left;
	SecondaryNode* rightOfLeftOfSecondaryNode = leftOfSecondaryNode->right;
	leftOfSecondaryNode->right = secondaryNode;
	secondaryNode->left = rightOfLeftOfSecondaryNode;
	secondaryNode->height = 1 + maxHeight(height(secondaryNode->left), height(secondaryNode->right));
	leftOfSecondaryNode->height = 1 + maxHeight(height(leftOfSecondaryNode->left), height(leftOfSecondaryNode->right));
	return leftOfSecondaryNode;
}

// This function rotates the secondary node to left and updates the heights of rotated nodes in AVL model.
SecondaryNode* PrimaryNode::rotateLeftAVL(SecondaryNode* secondaryNode) {
	SecondaryNode* rightOfSecondaryNode = secondaryNode->right;
	SecondaryNode* leftOfRightOfSecondaryNode = rightOfSecondaryNode->left;
	rightOfSecondaryNode->left = secondaryNode;
	secondaryNode->right = leftOfRightOfSecondaryNode;
	secondaryNode->height = 1 + maxHeight(height(secondaryNode->left), height(secondaryNode->right));
	rightOfSecondaryNode->height = 1 + maxHeight(height(rightOfSecondaryNode->left), height(rightOfSecondaryNode->right));
	return rightOfSecondaryNode;
}

// This function inserts a new secondary node at appropriate location and then if needed, balances the tree in AVL model.
SecondaryNode* PrimaryNode::insertSecondaryNodeAVL(SecondaryNode* currentSecondaryNode, std::string name, int price) {
	if (!currentSecondaryNode)
		return new SecondaryNode(name, price);
	else if (name < currentSecondaryNode->name)
		currentSecondaryNode->left = insertSecondaryNodeAVL(currentSecondaryNode->left, name, price);
	else if (name > currentSecondaryNode->name)
		currentSecondaryNode->right = insertSecondaryNodeAVL(currentSecondaryNode->right, name, price);

	currentSecondaryNode->height = 1 + maxHeight(height(currentSecondaryNode->left), height(currentSecondaryNode->right));
	int balanceFactor = getBalanceFactor(currentSecondaryNode);
	if (balanceFactor > 1) {
		if (name < currentSecondaryNode->left->name)
			return rotateRightAVL(currentSecondaryNode);
		else if (name > currentSecondaryNode->left->name) {
			currentSecondaryNode->left = rotateLeftAVL(currentSecondaryNode->left);
			return rotateRightAVL(currentSecondaryNode);
		}
	}
	else if (balanceFactor < -1) {
		if (name > currentSecondaryNode->right->name)
			return rotateLeftAVL(currentSecondaryNode);
		else if (name < currentSecondaryNode->right->name) {
			currentSecondaryNode->right = rotateRightAVL(currentSecondaryNode->right);
			return rotateLeftAVL(currentSecondaryNode);
		}
	}
	return currentSecondaryNode;
}

// This function returns the inorder successor of the secondary node.
SecondaryNode* PrimaryNode::inorderSuccessor(SecondaryNode* secondaryNode) {
	SecondaryNode* currentSecondaryNode = secondaryNode;
	while (currentSecondaryNode->left)
		currentSecondaryNode = currentSecondaryNode->left;
	return currentSecondaryNode;
}

// This function removes the target secondary node from tree and then if needed, balances the tree in AVL model.
SecondaryNode* PrimaryNode::removeSecondaryNodeAVL(SecondaryNode* currentSecondaryNode, std::string name) {
	if (!currentSecondaryNode)
		return currentSecondaryNode;
	else if (name < currentSecondaryNode->name)
		currentSecondaryNode->left = removeSecondaryNodeAVL(currentSecondaryNode->left, name);
	else if (name > currentSecondaryNode->name)
		currentSecondaryNode->right = removeSecondaryNodeAVL(currentSecondaryNode->right, name);
	else {
		if (!currentSecondaryNode->left || currentSecondaryNode->right) {
			SecondaryNode* temporarySecondaryNode = currentSecondaryNode->left ? currentSecondaryNode->left : currentSecondaryNode->right;
			if (!temporarySecondaryNode) {
				temporarySecondaryNode = currentSecondaryNode;
				currentSecondaryNode = nullptr;
			}
			else
				*currentSecondaryNode = *temporarySecondaryNode;
			delete temporarySecondaryNode;
		}
		else {
			SecondaryNode* temporarySecondaryNode = inorderSuccessor(currentSecondaryNode->right);
			currentSecondaryNode->name = temporarySecondaryNode->name;
			currentSecondaryNode->price = temporarySecondaryNode->price;
			currentSecondaryNode->right = removeSecondaryNodeAVL(currentSecondaryNode->right, temporarySecondaryNode->name);
		}
	}
	if (!currentSecondaryNode)
		return currentSecondaryNode;

	currentSecondaryNode->height = 1 + maxHeight(height(currentSecondaryNode->left), height(currentSecondaryNode->right));
	int balanceFactor = getBalanceFactor(currentSecondaryNode);
	if (balanceFactor > 1) {
		if (getBalanceFactor(currentSecondaryNode->left) >= 0)
			return rotateRightAVL(currentSecondaryNode);
		else {
			currentSecondaryNode->left = rotateLeftAVL(currentSecondaryNode->left);
			return rotateRightAVL(currentSecondaryNode);
		}
	}
	else if (balanceFactor < -1) {
		if (getBalanceFactor(currentSecondaryNode->right) <= 0)
			return rotateLeftAVL(currentSecondaryNode);
		else {
			currentSecondaryNode->right = rotateRightAVL(currentSecondaryNode->right);
			return rotateLeftAVL(currentSecondaryNode);
		}
	}
	return currentSecondaryNode;
}

// This function returns true if secondary node is red, otherwise false.
bool PrimaryNode::isRed(SecondaryNode* secondaryNode) {
	if (!secondaryNode)
		return false;
	return secondaryNode->isRed;
}

// This function rotates the secondary node to right and updates the colors of rotated nodes in LLRB model.
SecondaryNode* PrimaryNode::rotateRightLLRB(SecondaryNode* secondaryNode) {
	SecondaryNode* leftOfSecondaryNode = secondaryNode->left;
	secondaryNode->left = leftOfSecondaryNode->right;
	leftOfSecondaryNode->right = secondaryNode;
	leftOfSecondaryNode->isRed = secondaryNode->isRed;
	secondaryNode->isRed = true;
	return leftOfSecondaryNode;
}

// This function rotates the secondary node to left and updates the colors of rotated nodes in LLRB model.
SecondaryNode* PrimaryNode::rotateLeftLLRB(SecondaryNode* secondaryNode) {
	SecondaryNode* rightOfSecondaryNode = secondaryNode->right;
	secondaryNode->right = rightOfSecondaryNode->left;
	rightOfSecondaryNode->left = secondaryNode;
	rightOfSecondaryNode->isRed = secondaryNode->isRed;
	secondaryNode->isRed = true;
	return rightOfSecondaryNode;
}

// This function flips the colors of secondary node and its children.
void PrimaryNode::colorFlip(SecondaryNode* secondaryNode) {
	secondaryNode->isRed = !secondaryNode->isRed;
	if (secondaryNode->left)
		secondaryNode->left->isRed = !secondaryNode->left->isRed;
	if (secondaryNode->right)
		secondaryNode->right->isRed = !secondaryNode->right->isRed;
}

// This function inserts a new secondary node at appropriate location and then if needed, balances the tree in LLRB model.
SecondaryNode* PrimaryNode::insertSecondaryNodeLLRB(SecondaryNode* currentSecondaryNode, std::string name, int price) {
	if (!currentSecondaryNode)
		return new SecondaryNode(name, price);
	else if (name < currentSecondaryNode->name)
		currentSecondaryNode->left = insertSecondaryNodeLLRB(currentSecondaryNode->left, name, price);
	else if (name > currentSecondaryNode->name)
		currentSecondaryNode->right = insertSecondaryNodeLLRB(currentSecondaryNode->right, name, price);

	if (isRed(currentSecondaryNode->right) && !isRed(currentSecondaryNode->left))
		currentSecondaryNode = rotateLeftLLRB(currentSecondaryNode);
	if (isRed(currentSecondaryNode->left) && isRed(currentSecondaryNode->left->left))
		currentSecondaryNode = rotateRightLLRB(currentSecondaryNode);
	if (isRed(currentSecondaryNode->right) && isRed(currentSecondaryNode->left))
		colorFlip(currentSecondaryNode);

	return currentSecondaryNode;
}

// This function changes the 3-node (the node with the single red child) from being the right child to being the left child in LLRB model.
SecondaryNode* PrimaryNode::moveRedLeft(SecondaryNode* secondaryNode) {
	colorFlip(secondaryNode);
	if (secondaryNode->right && isRed(secondaryNode->right->left)) {
		secondaryNode->right = rotateRightLLRB(secondaryNode->right);
		secondaryNode = rotateLeftLLRB(secondaryNode);
		colorFlip(secondaryNode);
	}
	return secondaryNode;
}

// This function re-arranges the subtree so that the root is a red node and left-child is still a 3-node in LLRB model.
SecondaryNode* PrimaryNode::moveRedRight(SecondaryNode* secondaryNode) {
	colorFlip(secondaryNode);
	if (secondaryNode->left && isRed(secondaryNode->left->left)) {
		secondaryNode = rotateRightLLRB(secondaryNode);
		colorFlip(secondaryNode);
	}
	return secondaryNode;
}

// This function balances the tree in LLRB model.
SecondaryNode* PrimaryNode::fixUp(SecondaryNode* secondaryNode) {
	if (isRed(secondaryNode->right))
		secondaryNode = rotateLeftLLRB(secondaryNode);
	if (isRed(secondaryNode->left) && isRed(secondaryNode->left->left))
		secondaryNode = rotateRightLLRB(secondaryNode);
	if (isRed(secondaryNode->left) && isRed(secondaryNode->right))
		colorFlip(secondaryNode);
	return secondaryNode;
}

// This function deletes the inorder successor of secondary node.
SecondaryNode* PrimaryNode::deleteInorderSuccessor(SecondaryNode* secondaryNode) {
	if (!secondaryNode->left) {
		delete secondaryNode;
		return nullptr;
	}
	if (!isRed(secondaryNode->left) && isRed(secondaryNode->left->left))
		secondaryNode = moveRedLeft(secondaryNode);
	secondaryNode->left = deleteInorderSuccessor(secondaryNode->left);
	return fixUp(secondaryNode);
}

// This function removes the target secondary node from tree and then if needed, balances the tree in LLRB model.
SecondaryNode* PrimaryNode::removeSecondaryNodeLLRB(SecondaryNode* currentSecondaryNode, std::string name) {
	if (name < currentSecondaryNode->name) {
		if (!isRed(currentSecondaryNode->left) && !isRed(currentSecondaryNode->left->left))
			currentSecondaryNode = moveRedLeft(currentSecondaryNode);
		currentSecondaryNode->left = removeSecondaryNodeLLRB(currentSecondaryNode->left, name);
	}
	else {
		if (isRed(currentSecondaryNode->left))
			currentSecondaryNode = rotateRightLLRB(currentSecondaryNode);
		if (name == currentSecondaryNode->name && !currentSecondaryNode->right) {
			delete currentSecondaryNode;
			return nullptr;
		}
		if (currentSecondaryNode->right) {
			if (!isRed(currentSecondaryNode->right) && !isRed(currentSecondaryNode->right->left))
				currentSecondaryNode = moveRedRight(currentSecondaryNode);
			if (name == currentSecondaryNode->name) {
				currentSecondaryNode = inorderSuccessor(currentSecondaryNode->right);
				currentSecondaryNode->right = deleteInorderSuccessor(currentSecondaryNode->right);
			}
			else
				currentSecondaryNode->right = removeSecondaryNodeLLRB(currentSecondaryNode->right, name);
		}
	}
	return fixUp(currentSecondaryNode);
}
