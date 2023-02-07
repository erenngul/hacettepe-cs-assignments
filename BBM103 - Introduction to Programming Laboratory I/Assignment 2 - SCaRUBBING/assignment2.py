commands = input("""<-----RULES----->
1. BRUSH DOWN
2. BRUSH UP
3. VEHICLE ROTATES RIGHT
4. VEHICLE ROTATES LEFT
5. MOVE UP TO X
6. JUMP
7. REVERSE DIRECTION
8. VIEW THE MATRIX
0. EXIT
Please enter the commands with a plus sign (+) between them.
""")

commands = commands.split("+")
x = 1
y = 1
direction = "right"
brush = "up"
error = False

def check_for_error(): # 7+5_2+1+6+4+5_5+3+5_2+6+2+7+4+1+5_4+8+0
    global error, commands # decrease n 1 by 1 and check

    if error == False:
        for i in commands[1:]:
            if i == "9" or i == "5" or i == "5_" or i == " ":
                error = True
                break
            elif len(i) > 1:
                if i[1] == "_":
                    if i[0] != "5":
                        error = True
                        break
                elif i[1] == " " or i[0] == " " or len(i) > 1:
                    error = True
                    break

check_for_error()

N = int(commands[0])
matrix = [["+" for x in range(1, N+3)] for y in range(1, N+3)]
for row in matrix[1:-1]:
    row[1:-1] = " "*(len(row[1:-1]))

def brush_down():
    global brush, x, y
    brush = "down"
    matrix[y][x] = "*"

def brush_up():
    global brush
    brush = "up"

def vehicle_rotates_right():
    global direction
    
    if direction == "right":
        direction = "down"
    elif direction == "down":
        direction = "left"
    elif direction == "left":
        direction = "up"
    elif direction == "up":
        direction = "right"

def vehicle_rotates_left():
    global direction

    if direction == "right":
        direction = "up"
    elif direction == "up":
        direction = "left"
    elif direction == "left":
        direction = "down"
    elif direction == "down":
        direction = "right"

def move_up_to_x(i):
    global x, y, direction, brush, N
    a = int(i[2:])

    while a > 0:
        if brush == "down":
            if direction == "right":
                x += 1
                if x > N:
                    x = 1
                matrix[y][x] = "*"
            elif direction == "left":
                x -= 1
                if x < 1:
                    x = N
                matrix[y][x] = "*"
            elif direction == "down":
                y += 1
                if y > N:
                    y = 1
                matrix[y][x] = "*"
            elif direction == "up":
                y -= 1
                if y < 1:
                    y = N
                matrix[y][x] = "*"
        elif brush == "up":
            if direction == "right":
                x += 1
                if x > N:
                    x = 1
            elif direction == "left":
                x -= 1
                if x < 1:
                    x = N
            elif direction == "down":
                y += 1
                if y > N:
                    y = 1
            elif direction == "up":
                y -= 1
                if y < 1:
                    y = N
        a -= 1

def jump():
    global x, y, brush
    brush = "up"

    if direction == "right":
        x = x + 3
        if x > N:
            x = x - N
    elif direction == "left":
        x = x - 3
        if x < 1:
            x = x + N 
    elif direction == "down":
        y = y + 3
        if y > N:
            y = y - N
    elif direction == "up":
        y = y - 3
        if y < 1:
            y = y + N

def reverse_direction():
    global direction

    if direction == "right":
        direction = "left"
    elif direction == "up":
        direction = "down"
    elif direction == "left":
        direction = "right"
    elif direction == "down":
        direction = "up"

def view_the_matrix():
    for row in matrix:
        print("".join(row))

def program():
    global commands, error, N, matrix

    if error == False:
        for i in commands[1:]:
            if i == "1":
                brush_down()
            elif i == "2":
                brush_up()
            elif i == "3":
                vehicle_rotates_right()
            elif i == "4":
                vehicle_rotates_left()
            elif i[0] == "5":
                move_up_to_x(i)
            elif i == "6":
                jump()
            elif i == "7":
                reverse_direction()
            elif i == "8":
                view_the_matrix()
            elif i == "0":
                end = True
                break
    if error == True:
        commands = input("You entered an incorrect command. Please try again!\n")
        commands = commands.split("+")
        error = False

        N = int(commands[0])
        matrix = [["+" for x in range(1, N+3)] for y in range(1, N+3)]
        for row in matrix[1:-1]:
            row[1:-1] = " "*(len(row[1:-1]))

        check_for_error()
        program()

program()