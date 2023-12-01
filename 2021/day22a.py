import re

file1 = open('input22.txt', 'r')
procedure = [re.search(r'(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)', line).groups() for line in file1 if line.strip() != '']

cubes = [[[0 for _ in range(101)] for _ in range(101)] for _ in range(101)]

for line in procedure:
    if -50 <= int(line[1]) <= 50 and \
            -50 <= int(line[2]) <= 50 and \
            -50 <= int(line[3]) <= 50 and \
            -50 <= int(line[4]) <= 50 and \
            -50 <= int(line[5]) <= 50 and \
            -50 <= int(line[6]) <= 50:
        for x in range(int(line[1]), int(line[2]) + 1):
            for y in range(int(line[3]), int(line[4]) + 1):
                for z in range(int(line[5]), int(line[6]) + 1):
                    if line[0] == 'on':
                        cubes[x + 50][y + 50][z + 50] = 1
                    else:
                        cubes[x + 50][y + 50][z + 50] = 0

print(sum(sum(sum(row) for row in cube) for cube in cubes))
