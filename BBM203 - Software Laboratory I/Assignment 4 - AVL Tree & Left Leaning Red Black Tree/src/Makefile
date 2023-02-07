OBJS	= Main.o MainTree.o PrimaryNode.o SecondaryNode.o
SOURCE	= Main.cpp MainTree.cpp PrimaryNode.cpp SecondaryNode.cpp
HEADER	= MainTree.h PrimaryNode.h SecondaryNode.h
OUT	= webstore
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

Main.o: Main.cpp
	$(CC) $(FLAGS) Main.cpp -std=c++11

MainTree.o: MainTree.cpp
	$(CC) $(FLAGS) MainTree.cpp -std=c++11

PrimaryNode.o: PrimaryNode.cpp
	$(CC) $(FLAGS) PrimaryNode.cpp -std=c++11

SecondaryNode.o: SecondaryNode.cpp
	$(CC) $(FLAGS) SecondaryNode.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)