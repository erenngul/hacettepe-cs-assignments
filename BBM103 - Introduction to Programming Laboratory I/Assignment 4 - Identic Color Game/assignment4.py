import sys

input_file = sys.argv[1]


with open(input_file) as input_file:
	input_list = input_file.readlines()
	input_list = [line.strip("\n ").split() for line in input_list]


def balls_disappear(row, column):
	global score_list

	def bomb_explosion(row, column):
		input_list[row][column] = " "

		for row_list in input_list:
			try:
				if input_list[input_list.index(row_list)][column] == "X":
					bomb_explosion(input_list.index(row_list), column)
			except ValueError:
				pass
			score_list.append(row_list[column])
			row_list[column] = " "
            
		if "X" in input_list[row]:
			bomb_explosion(row, input_list[row].index("X"))

		for column_ in input_list[row]:
			score_list.append(column_)
		input_list[row] = [" " for column_ in input_list[row]]

	
	if input_list[row][column] == "X":
		bomb_explosion(row, column)
	else:
		try:
			if input_list[row][column + 1] == current_cell and column < len(input_list[row]): # checks right
				score_list.append(input_list[row][column + 1])
				input_list[row][column + 1] = " "
				balls_disappear(row, column + 1)
		except IndexError:
			pass
			
		try:
			if input_list[row][column - 1] == current_cell and column > 0: # checks left
				score_list.append(input_list[row][column - 1])
				input_list[row][column - 1] = " "
				balls_disappear(row, column - 1)
		except IndexError:
			pass

		try:
			if input_list[row - 1][column] == current_cell and row > 0: # checks up
				score_list.append(input_list[row - 1][column])
				input_list[row - 1][column] = " "
				balls_disappear(row - 1, column)
		except IndexError:
			pass

		try:
			if input_list[row + 1][column] == current_cell and row < len(input_list): # checks down
				score_list.append(input_list[row + 1][column])
				input_list[row + 1][column] = " "
				balls_disappear(row + 1, column)
		except IndexError:
			pass


def calculate_score():
	global score

	for color in score_list:
		if color in score_dict:
			score_dict[color] += 1
		else:
			score_dict[color] = 1
	
	for color in score_dict:
		if color == "B":
			score += score_dict[color] * 9
		elif color == "G":
			score += score_dict[color] * 8
		elif color == "W":
			score += score_dict[color] * 7
		elif color == "Y":
			score += score_dict[color] * 6
		elif color == "R":
			score += score_dict[color] * 5
		elif color == "P":
			score += score_dict[color] * 4
		elif color == "O":
			score += score_dict[color] * 3
		elif color == "D":
			score += score_dict[color] * 2
		elif color == "F":
			score += score_dict[color] * 1


def slide_cells_down(row, column):
	empty_list = []
	while True:
		try:
			if input_list[row][column] == " " and -len(input_list) - 1 < row:
				try:
					input_list[row][column] = input_list[row - 1][column]
				except IndexError:
					if input_list[-1][column] == " ":
						row = -1
					else:
						row = -1
						column += 1
						empty_list = []
						if column > len(input_list[row]) - 1:
							break
					continue
				input_list[row - 1][column] = " "
				while True:
					if input_list[row + 1][column] == " " and input_list[row][column] != " ":
						input_list[row + 1][column] = input_list[row][column]
						input_list[row][column] = " "
						row += 1
						if row == -1:
							break
					else:
						break
			if input_list[row][column] == " ":
				empty_list.append(input_list[row][column])
				if len(empty_list) == len(input_list):
					row = 0
					column += 1
					empty_list = []
			row -= 1
		except IndexError:
			row = -1
			column += 1
			empty_list = []
			if column > len(input_list[row]):
				break


def remove_empty_column():
	try:
		for column in reversed(input_list[-1]):
			if column == " ":
				column_index = input_list[-1].index(column)
				for row in input_list:
					del row[column_index]
	except IndexError:
		pass


def remove_empty_row():
	for row in reversed(input_list):
		if all(empty == " " for empty in row):
			input_list.remove(row)


def game_over():
	global gameover_check

	row_index = 0
	index_position_list = []
	for row in input_list:
		column_index = 0
		for column in row:
			index_position_list.append((row_index, column_index))
			column_index += 1
		row_index += 1

	gameover_list = []
	for position in index_position_list:
		if input_list[position[0]][position[1]] != " ":
			try:
				if input_list[position[0]][position[1]] == input_list[position[0]][position[1] + 1]:
					gameover_list.append(False)
				else:
					gameover_list.append(True)
			except IndexError:
				pass
			
			try:
				if input_list[position[0]][position[1]] == input_list[position[0] + 1][position[1]]:
					gameover_list.append(False)
				else:
					gameover_list.append(True)
			except IndexError:
				pass
		
		if input_list[position[0]][position[1]] == "X":
			gameover_list.append(False)
	
	if any(gameover == False for gameover in gameover_list):
		gameover_check = False
	else:
		gameover_check = True


def gameplay():
	global current_cell, score, score_list, score_dict, gameover_check

	score = 0
	gameover_check = False
	while gameover_check == False:
		for line in input_list:
			line = " ".join(line)
			print(line)
		print("\nYour score is:", score)

		game_over()

		if gameover_check == True:
			print("\nGame over!")
		else:
			score_list = []
			score_dict = {}
			row, column = 0, 0

			while True:
				try:
					user_input = input("\nPlease enter a row and column number: ")
					print()
					user_input = [int(number) for number in user_input.split(" ")]
					row, column = user_input[0], user_input[1]
					current_cell = input_list[row][column]

					if current_cell == " ": # checks for valid size
						error = True
						print("Please enter a valid size!\n")
						while error == True:
							user_input = input("Please enter a row and column number: ")
							print()
							user_input = [int(number) for number in user_input.split(" ")]
							row, column = user_input[0], user_input[1]
							current_cell = input_list[row][column]
							if current_cell != " ":
								error = False
							else:
								print("Please enter a valid size!\n")
					else:
						break
				except IndexError:
					print("Please enter a valid size!")
			
			if current_cell == "X":
				balls_disappear(*user_input)
				calculate_score()
				remove_empty_row()
				remove_empty_column()
			else:
				balls_disappear(*user_input)
				slide_cells_down(-1, 0)
				calculate_score()
				remove_empty_row()
				remove_empty_column()

gameplay()