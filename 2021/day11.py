import copy

file1 = open('input11.txt', 'r')
original_grid = [[int(i) for i in list(line.strip())] for line in file1.readlines() if line.strip() != '\n']
flash_count = 0


def simulate_step(grid, stop_at_all_flash=False):
    # Increase energy levels by 1
    grid = [[i + 1 for i in row] for row in grid]

    # Simulate flashes
    grid = simulate_flashes(grid, stop_at_all_flash)
    if grid is None and stop_at_all_flash:
        return None

    # Set flashed energy levels to 0
    grid = [[0 if i == -1 else i for i in row] for row in grid]

    return grid


def simulate_flashes(grid, stop_at_all_flash=False):
    step_flash_count = 0

    last_grid = None
    while grid != last_grid:
        last_grid = copy.deepcopy(grid)
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] > 9:
                    # Flash
                    grid[i][j] = -1
                    step_flash_count += 1

                    # Increase energy of surrounding cells
                    if i > 0 and grid[i - 1][j] != -1:
                        grid[i - 1][j] += 1
                    if i < len(grid) - 1 and grid[i + 1][j] != -1:
                        grid[i + 1][j] += 1
                    if j > 0 and grid[i][j - 1] != -1:
                        grid[i][j - 1] += 1
                    if j < len(grid[0]) - 1 and grid[i][j + 1] != -1:
                        grid[i][j + 1] += 1
                    if i > 0 and j > 0 and grid[i - 1][j - 1] != -1:
                        grid[i - 1][j - 1] += 1
                    if i > 0 and j < len(grid[0]) - 1 and grid[i - 1][j + 1] != -1:
                        grid[i - 1][j + 1] += 1
                    if i < len(grid) - 1 and j > 0 and grid[i + 1][j - 1] != -1:
                        grid[i + 1][j - 1] += 1
                    if i < len(grid) - 1 and j < len(grid[0]) - 1 and grid[i + 1][j + 1] != -1:
                        grid[i + 1][j + 1] += 1

    global flash_count
    flash_count += step_flash_count

    if stop_at_all_flash and step_flash_count == len(grid) * len(grid[0]):
        return None

    return grid


grid = copy.deepcopy(original_grid)
for i in range(0, 100):
    grid = simulate_step(grid)
print(flash_count)

grid = copy.deepcopy(original_grid)
i = 0
while True:
    i += 1
    grid = simulate_step(grid, True)
    if grid is None:
        break
print(i)
