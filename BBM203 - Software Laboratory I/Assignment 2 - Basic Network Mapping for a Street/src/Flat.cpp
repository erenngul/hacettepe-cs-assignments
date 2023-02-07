#include "Flat.h"

Flat::Flat(int flat_id, int initial_bandwidth, int is_empty) : flat_id(flat_id), initial_bandwidth(initial_bandwidth), is_empty(is_empty), next(nullptr), prev(nullptr) {}