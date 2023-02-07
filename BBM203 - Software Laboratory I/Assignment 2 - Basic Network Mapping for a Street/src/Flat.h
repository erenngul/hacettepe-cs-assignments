#pragma once

class Flat {
public:
	int flat_id;
	int initial_bandwidth;
	int is_empty;
	Flat* next;
	Flat* prev;

	Flat(int flat_id, int initial_bandwidth, int is_empty);
};