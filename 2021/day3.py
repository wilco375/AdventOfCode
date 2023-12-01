file1 = open('input3.txt', 'r')
lines = file1.readlines()

gamma = ""
epsilon = ""
for i in range(0, len(lines[0].strip())):
    zeros = 0
    ones = 0
    for j in range(0, len(lines)):
        if lines[j][i] == '0':
            zeros += 1
        elif lines[j][i] == '1':
            ones += 1
    if zeros < ones:
        gamma += '1'
        epsilon += '0'
    else:
        gamma += '0'
        epsilon += '1'
print(int(gamma, 2) * int(epsilon, 2))

oxygen = lines
co2 = lines
for i in range(0, len(lines[0].strip())):
    if len(oxygen) > 1:
        # Oxygen
        zeros = 0
        ones = 0
        for j in range(0, len(oxygen)):
            if oxygen[j][i] == '0':
                zeros += 1
            elif oxygen[j][i] == '1':
                ones += 1
        keep = '1'
        if zeros > ones:
            keep = '0'
        oxygen = [x for x in oxygen if x[i] == keep]

    if len(co2) > 1:
        # CO2
        zeros = 0
        ones = 0
        for j in range(0, len(co2)):
            if co2[j][i] == '0':
                zeros += 1
            elif co2[j][i] == '1':
                ones += 1
        keep = '0'
        if zeros > ones:
            keep = '1'
        co2 = [x for x in co2 if x.strip()[i] == keep]

    if len(oxygen) == 1 and len(co2) == 1:
        break
print(int(oxygen[0], 2) * int(co2[0], 2))


