#include <string>
#include "SecondaryNode.h"
#pragma once

class PrimaryNode {
public:
	std::string category;
	PrimaryNode* left;
	PrimaryNode* right;
	SecondaryNode* rootSecondaryNode; // This pointer holds the root of secondary node tree.

	PrimaryNode(std::string category);

	// This function returns the height of the secondary node.
	int height(SecondaryNode* secondaryNode);

	// This function compares the subtrees and returns the subtree with higher height.
	int maxHeight(int heightOfLeftSubtree, int heightOfRightSubtree);

	// This function returns the balance factor of the secondary node.
	int getBalanceFactor(SecondaryNode* secondaryNode);

	// This function rotates the secondary node to right and updates the heights of rotated nodes in AVL model.
	SecondaryNode* rotateRightAVL(SecondaryNode* secondaryNode);

	// This function rotates the secondary node to left and updates the heights of rotated nodes in AVL model.
	SecondaryNode* rotateLeftAVL(SecondaryNode* secondaryNode);

	// This function inserts a new secondary node at appropriate location and then if needed, balances the tree in AVL model.
	SecondaryNode* insertSecondaryNodeAVL(SecondaryNode* currentSecondaryNode, std::string name, int price);

	// This function returns the inorder successor of the secondary node.
	SecondaryNode* inorderSuccessor(SecondaryNode* secondaryNode);

	// This function removes the target secondary node from tree and then if needed, balances the tree in AVL model.
	SecondaryNode* removeSecondaryNodeAVL(SecondaryNode* currentSecondaryNode, std::string name);

	// This function returns true if secondary node is red, otherwise false.
	bool isRed(SecondaryNode* secondaryNode);

	// This function rotates the secondary node to right and updates the colors of rotated nodes in LLRB model.
	SecondaryNode* rotateRightLLRB(SecondaryNode* secondaryNode);

	// This function rotates the secondary node to left and updates the colors of rotated nodes in LLRB model.
	SecondaryNode* rotateLeftLLRB(SecondaryNode* secondaryNode);

	// This function flips the colors of secondary node and its children.
	void colorFlip(SecondaryNode* secondaryNode);

	// This function inserts a new secondary node at appropriate location and then if needed, balances the tree in LLRB model.
	SecondaryNode* insertSecondaryNodeLLRB(SecondaryNode* currentSecondaryNode, std::string name, int price);

	// This function changes the 3-node (the node with the single red child) from being the right child to being the left child in LLRB model.
	SecondaryNode* moveRedLeft(SecondaryNode* secondaryNode);

	// This function re-arranges the subtree so that the root is a red node and left-child is still a 3-node in LLRB model.
	SecondaryNode* moveRedRight(SecondaryNode* secondaryNode);

	// This function balances the tree in LLRB model.
	SecondaryNode* fixUp(SecondaryNode* secondaryNode);

	// This function deletes the inorder successor of secondary node.
	SecondaryNode* deleteInorderSuccessor(SecondaryNode* secondaryNode);

	// This function removes the target secondary node from tree and then if needed, balances the tree in LLRB model.
	SecondaryNode* removeSecondaryNodeLLRB(SecondaryNode* currentSecondaryNode, std::string name);
};