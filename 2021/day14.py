file1 = open('input14.txt', 'r')

initial_polymer = file1.readline().strip()

insertions = {}
for line in file1.readlines():
    if line.strip() == '':
        continue
    line = line.strip().split(' -> ')
    insertions[line[0]] = line[1]


def step(polymer):
    new_polymer = {}
    for group, amount in polymer.items():
        if group in insertions:
            if group[0] + insertions[group] in new_polymer:
                new_polymer[group[0] + insertions[group]] += amount
            else:
                new_polymer[group[0] + insertions[group]] = amount
            if insertions[group] + group[1] in new_polymer:
                new_polymer[insertions[group] + group[1]] += amount
            else:
                new_polymer[insertions[group] + group[1]] = amount
        else:
            if group in new_polymer:
                new_polymer[group] += amount
            else:
                new_polymer[group] = amount

    return new_polymer


def print_count(polymer):
    count = {}
    for group, amount in polymer.items():
        if group[0] in count:
            count[group[0]] += amount
        else:
            count[group[0]] = amount
    count[initial_polymer[-1]] += 1
    print(count[max(count, key=count.get)] - count[min(count, key=count.get)])


polymer = {}
for i in range(0, len(initial_polymer) - 1):
    key = initial_polymer[i] + initial_polymer[i+1]
    if key in polymer:
        polymer[key] += 1
    else:
        polymer[key] = 1

for i in range(0, 40):
    polymer = step(polymer)
    if i == 9:
        print_count(polymer)
print_count(polymer)