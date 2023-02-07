import sys

smn = sys.argv[1]
commands = sys.argv[2]

with open(smn, encoding="UTF-8") as network:
    network_list = []
    for line in network.readlines():
        line = line.split(":") 
        line[1] = line[1].strip("\n ")
        line[0] = line[0].strip(" ")
        network_list.append(line)

network_dict = {k: v.split(" ") for (k, v) in network_list}

relations = network_dict.values()

def add_new_user(new_user):
    if new_user in network_dict:
        output.write("ERROR: Wrong input type! for 'ANU'!--This user already exists!!\n")

    else:
        network_dict[new_user] = []
        output.write("User '%s' has been added to the social network successfully\n" % new_user)

def delete_existing_user(username):
    if username in network_dict:
        del network_dict[username]
        for relation in relations:
            if username in relation:
                relation.remove(username)
        output.write("User '%s' and his/her all relations have been deleted successfully\n" % username)

    else:
        output.write("ERROR: Wrong input type! for 'DEU'!--There is no user named '%s'!!\n" % username)

def add_new_friend(source_user, target_user):
    if source_user not in network_dict and target_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'ANF'!--No user named '%s' and '%s' found!\n" % (source_user, target_user))

    elif source_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'ANF'!--No user named '%s' found!!\n" % source_user)

    elif target_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'ANF'!--No user named '%s' found!!\n" % target_user)

    elif target_user in network_dict[source_user] and source_user in network_dict[target_user]:
        output.write("ERROR: A relation between '%s' and '%s' already exists!!\n" % (source_user, target_user))

    elif source_user and target_user in network_dict:
        network_dict[source_user].append(target_user)
        network_dict[target_user].append(source_user)
        output.write("Relation between '%s' and '%s' has been added successfully\n" % (source_user, target_user))

def delete_existing_friend(source_user, target_user):
    if source_user not in network_dict and target_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'DEF'!--No user named '%s' and '%s' found!\n" % (source_user, target_user))

    elif source_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'DEF'!--No user named '%s' found!!\n" % source_user)

    elif target_user not in network_dict:
        output.write("ERROR: Wrong input type! for 'DEF'!--No user named '%s' found!\n" % target_user)

    elif target_user not in network_dict[source_user] and source_user not in network_dict[target_user]:
        output.write("ERROR: No relation between '%s' and '%s' found!!\n" % (source_user, target_user))

    elif target_user in network_dict[source_user] and source_user in network_dict[target_user]:
        network_dict[source_user].remove(target_user)
        network_dict[target_user].remove(source_user)
        output.write("Relation between '%s' and '%s' has been deleted successfully\n" % (source_user, target_user))

def count_friend(username):
    if username not in network_dict:
        output.write("ERROR: Wrong input type! for 'CF'!--No user named '%s' found!\n" % username)
    elif username in network_dict:
        output.write("User '%s' has %d friends\n" % (username, len(network_dict[username])))

def find_possible_friends(username, maximum_distance):
    if username not in network_dict:
        output.write("ERROR: Wrong input type! for 'FPF'!--No user named '%s' found!\n" % username)
    
    if not 1 <= maximum_distance <= 3:
        output.write("ERROR: Maximum distance cannot be less than 1 or greater than 3\n")

    elif username in network_dict and 1 <= maximum_distance <= 3:
        possible_friends = []

        if maximum_distance == 1:
            for relation1 in network_dict[username]:
                possible_friends.append(relation1)

        elif maximum_distance == 2:
            for relation1 in network_dict[username]:
                possible_friends.append(relation1)
                for relation2 in network_dict[relation1]:
                    possible_friends.append(relation2)

        elif maximum_distance == 3:
            for relation1 in network_dict[username]:
                possible_friends.append(relation1)
                for relation2 in network_dict[relation1]:
                    possible_friends.append(relation2)
                    for relation3 in network_dict[relation2]:
                        possible_friends.append(relation3)


        if username in possible_friends:
            possible_friends = sorted(set(possible_friends))
            possible_friends.remove(username)

        if len(possible_friends) == 0:
            output.write("User '%s' does not have any possible friends.\n" % username)
        else:
            output.write("User '%s' have %d possible friends when maximum distance is %d\n" % (username, len(possible_friends), maximum_distance))
            output.write("These possible friends: {'%s'}\n" % "','".join(possible_friends))

def suggest_friend(username, MD):
    if username not in network_dict:
        output.write("Error: Wrong input type! for 'SF'!--No user named '%s' found!!\n" % username)
    
    if not 2 <= MD <= 3:
        output.write("Error: Mutually Degree cannot be less than 2 or greater than 3\n")
    
    elif username in network_dict and 2 <= MD <= 3:
        suggest_friend_list = []

        for relation1 in network_dict[username]:
            for relation2 in network_dict[relation1]: 
                suggest_friend_list.append(relation2)
        mutual_friends = {relation: suggest_friend_list.count(relation) for relation in suggest_friend_list}
        del mutual_friends[username]
        for relation1 in network_dict[username]:
            if relation1 in mutual_friends:
                del mutual_friends[relation1]

        if MD == 2:
            output.write("Suggestion List for '%s' (when MD is 2):\n" % username)
            mutual_friend_list_2 = sorted([mutual_friend for mutual_friend in mutual_friends if mutual_friends[mutual_friend] == 2])
            mutual_friends_2= "','".join(mutual_friend_list_2)
            mutual_friend_list_3 = sorted([mutual_friend for mutual_friend in mutual_friends if mutual_friends[mutual_friend] == 3])
            mutual_friends_3 = "','".join(mutual_friend_list_3)
            total_mutual_friends_list = sorted(mutual_friend_list_2 + mutual_friend_list_3)
            total_mutual_friends = "','".join(total_mutual_friends_list)
            if 2 in mutual_friends.values():
                output.write("'%s' has 2 mutual friends with '%s'\n" % (username, mutual_friends_2))
            else:
                output.write("'%s' does not have 2 mutual friends with anyone.\n" % username)
            if 3 in mutual_friends.values():
                output.write("'%s' has 3 mutual friends with '%s'\n" % (username, mutual_friends_3))
            else:
                output.write("'%s' does not have 3 mutual friends with anyone.\n" % username)
            if len(total_mutual_friends_list) == 0:
                output.write("There are no suggestible friends for '%s'\n" % username)
            else:
                output.write("The suggested friends for '%s':'%s'\n" % (username, total_mutual_friends))

        elif MD == 3:
            output.write("Suggestion List for '%s' (when MD is 3):\n" % username)
            mutual_friend_list_3 = sorted([mutual_friend for mutual_friend in mutual_friends if mutual_friends[mutual_friend] == 3])
            mutual_friends_3 = "','".join(mutual_friend_list_3)
            if 3 in mutual_friends.values():
                output.write("'%s' has 3 mutual friends with '%s'\n" % (username, mutual_friends_3))
            else:
                output.write("'%s' does not have 3 mutual friends with anyone.\n" % username)
            if len(mutual_friend_list_3):
                output.write("The suggested friends for '%s':'%s'\n" % (username, mutual_friends_3))
            else:
                output.write("There are no suggestible friends for '%s'\n" % username)

def main():
    with open(commands, encoding="UTF-8") as command_input:
        command_list = []
        for line in command_input.readlines():
            line = line.strip("\n ")
            line = line.split(" ")
            command_list.append(line)
    
    output.write("Welcome to Assignment 3\n")
    output.write("-------------------------------\n")

    for command in command_list:
        try:
            if command[0] == "ANU":
                add_new_user(command[1])
            elif command[0] == "DEU":
                delete_existing_user(command[1])
            elif command[0] == "ANF":
                add_new_friend(command[1], command[2])
            elif command[0] == "DEF":
                delete_existing_friend(command[1], command[2])
            elif command[0] == "CF":
                count_friend(command[1])
            elif command[0] == "FPF":
                find_possible_friends(command[1], int(command[2]))
            elif command[0] == "SF":
                suggest_friend(command[1], int(command[2]))
            else:
                output.write("ERROR: '%s' is an invalid command!\n" % command[0])
        except IndexError:
            output.write("There is a missing parameter in '%s'\n" % command[0])

with open("output.txt", "w", encoding="UTF-8") as output:
    main()