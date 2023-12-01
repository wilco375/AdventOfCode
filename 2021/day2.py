file1 = open('input2.txt', 'r')
lines = file1.readlines()

horizontal = 0
vertical = 0
for line in lines:
    command = line.split(' ')[0]
    value = int(line.split(' ')[1])
    if command == 'forward':
        horizontal += value
    elif command == 'up':
        vertical -= value
    elif command == 'down':
        vertical += value
print(horizontal * vertical)


horizontal = 0
vertical = 0
aim = 0
for line in lines:
    command = line.split(' ')[0]
    value = int(line.split(' ')[1])
    if command == 'forward':
        horizontal += value
        vertical += aim * value
    elif command == 'up':
        aim -= value
    elif command == 'down':
        aim += value
print(horizontal * vertical)
