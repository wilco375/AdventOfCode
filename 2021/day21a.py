import copy

file1 = open('input21.txt', 'r')
initial_positions = [int(line.strip().split(': ')[1]) for line in file1 if line.strip() != '']

positions = copy.deepcopy(initial_positions)
scores = [0 for player in positions]


dice = 1
dice_rolls = 0
while True:
    for i in range(len(positions)):
        dice_sum = dice * 3 + 3
        positions[i] = (positions[i] - 1 + dice_sum) % 10 + 1
        scores[i] += positions[i]
        dice = (dice + 2) % 100 + 1
        dice_rolls += 3

        if max(scores) >= 1000:
            break

    if max(scores) >= 1000:
        break

print(dice_rolls * min(scores))
