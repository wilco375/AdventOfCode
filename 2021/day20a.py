file1 = open('input20.txt', 'r')
enhancement_algorithm = [1 if char == '#' else 0 for char in file1.readline().strip()]

initial_grid = [[1 if char == '#' else 0 for char in line.strip()] for line in file1.readlines() if line.strip() != '']


def expand_grid(grid, expand_by=2):
    # Add an extra rows and columns around the grid
    new_grid = [[0 for i in range(len(grid[0]) + expand_by)] for j in range(len(grid) + expand_by)]
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            new_grid[i + expand_by // 2][j + expand_by // 2] = grid[i][j]
    return new_grid


def enhance(grid):
    # Apply enhancement algorithm to the grid
    grid = expand_grid(grid)
    new_grid = [[0 for i in range(len(grid[0]))] for j in range(len(grid))]
    for i in range(1, len(grid) - 1):
        for j in range(1, len(grid[0]) - 1):
            number = ''
            for y in [-1, 0, 1]:
                for x in [-1, 0, 1]:
                    number += str(grid[i + y][j + x])
            number = int(number, 2)
            new_grid[i][j] = enhancement_algorithm[number]
    return new_grid


def print_grid(grid):
    for row in grid:
        print(''.join(['#' if i == 1 else '.' for i in row]))


grid = expand_grid(initial_grid, 10)
grid = enhance(grid)
grid = enhance(grid)
print(sum(sum(row[5:-5]) for row in grid[5:-5]))
