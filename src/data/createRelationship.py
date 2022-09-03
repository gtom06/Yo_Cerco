import random
list = []
for i in range (0,100):
    s = "(" + str(random.randint(1,11)) + "," + str(random.randint(1,13)) + "),"
    if s not in list:
        list.append(s)
for i in list:
    print(i)