OBJS	= Barista.o BaristaQueue.o Cashier.o CashierQueue.o Customer.o FirstModel.o Main.o SecondModel.o
SOURCE	= Barista.cpp BaristaQueue.cpp Cashier.cpp CashierQueue.cpp Customer.cpp FirstModel.cpp Main.cpp SecondModel.cpp
HEADER	= Barista.h BaristaQueue.h Cashier.h CashierQueue.h Customer.h FirstModel.h SecondModel.h
OUT	= Assignment3
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

Barista.o: Barista.cpp
	$(CC) $(FLAGS) Barista.cpp -std=c++11

BaristaQueue.o: BaristaQueue.cpp
	$(CC) $(FLAGS) BaristaQueue.cpp -std=c++11

Cashier.o: Cashier.cpp
	$(CC) $(FLAGS) Cashier.cpp -std=c++11

CashierQueue.o: CashierQueue.cpp
	$(CC) $(FLAGS) CashierQueue.cpp -std=c++11

Customer.o: Customer.cpp
	$(CC) $(FLAGS) Customer.cpp -std=c++11

FirstModel.o: FirstModel.cpp
	$(CC) $(FLAGS) FirstModel.cpp -std=c++11

Main.o: Main.cpp
	$(CC) $(FLAGS) Main.cpp -std=c++11

SecondModel.o: SecondModel.cpp
	$(CC) $(FLAGS) SecondModel.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)