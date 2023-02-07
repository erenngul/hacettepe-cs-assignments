OBJS	= Apartment.o ApartmentLinkedList.o Flat.o FlatLinkedList.o Main.o
SOURCE	= Apartment.cpp ApartmentLinkedList.cpp Flat.cpp FlatLinkedList.cpp Main.cpp
HEADER	= Apartment.h ApartmentLinkedList.h Flat.h FlatLinkedList.h
OUT	= networkmap
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

Apartment.o: Apartment.cpp
	$(CC) $(FLAGS) Apartment.cpp -std=c++11

ApartmentLinkedList.o: ApartmentLinkedList.cpp
	$(CC) $(FLAGS) ApartmentLinkedList.cpp -std=c++11

Flat.o: Flat.cpp
	$(CC) $(FLAGS) Flat.cpp -std=c++11

FlatLinkedList.o: FlatLinkedList.cpp
	$(CC) $(FLAGS) FlatLinkedList.cpp -std=c++11

Main.o: Main.cpp
	$(CC) $(FLAGS) Main.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)