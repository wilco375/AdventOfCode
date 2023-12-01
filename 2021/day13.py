file1 = open('input13.txt', 'r')

dots = []
folds = []
for line in file1.readlines():
    if line.startswith('fold along '):
        fold = line.strip()[11:].split('=')
        folds.append((fold[0], int(fold[1])))
    elif line.strip() != '':
        dot = line.strip().split(',')
        dots.append((int(dot[0]), int(dot[1])))

coords = []
for y in range(max(y for (x, y) in dots) + 1):
    coords.append([])
    for x in range(max(x for (x, y) in dots) + 1):
        coords[y].append(False)

for (x, y) in dots:
    coords[y][x] = True


def print_coords(coords):
    for row in coords:
        for dot in row:
            print('#' if dot else ' ', end='')
        print()


def fold_coords(coords, fold):
    direction = fold[0]
    position = fold[1]

    if direction == 'x':
        # Vertical fold, fold left
        for y in range(len(coords)):
            for x in range(position+1, len(coords[0])):
                coords[y][2*position - x] = coords[y][2*position - x] or coords[y][x]
        return [row[:position] for row in coords]
    if direction == 'y':
        # Horizontal fold, fold up
        for x in range(len(coords[0])):
            for y in range(position+1, len(coords)):
                coords[2*position - y][x] = coords[2*position - y][x] or coords[y][x]
        return coords[:position]


first_fold = True
for fold in folds:
    coords = fold_coords(coords, fold)
    if first_fold:
        print(sum([sum(row) for row in coords]))
    first_fold = False
print_coords(coords)
