import math

file1 = open('input15.txt', 'r')
initial_grid = [[int(x) for x in line.strip()] for line in file1.readlines()]


def dijkstra(grid):
    # Run Dijkstra on grid
    distances = [[math.inf for x in range(len(grid[0]))] for y in range(len(grid))]
    unvisited = [[True for x in range(len(grid[0]))] for y in range(len(grid))]
    unvisited_count = len(unvisited) * len(unvisited[0])
    unvisited_with_non_inf_distance = [(0, 0)]

    distances[0][0] = 0
    max_x = len(grid) - 1
    max_y = len(grid[0]) - 1

    while unvisited_count > 0:
        min_distance = math.inf
        min_distance_node = None
        for x, y in unvisited_with_non_inf_distance:
            if distances[x][y] < min_distance:
                min_distance = distances[x][y]
                min_distance_node = (x, y)

        node = min_distance_node
        unvisited[node[0]][node[1]] = False
        unvisited_count -= 1
        unvisited_with_non_inf_distance.remove(node)

        if node[0] == len(grid) - 1 and node[1] == len(grid[0]) - 1:
            # Return shortest distance to exit node
            return distances[len(grid) - 1][len(grid[0]) - 1]

        neighbours = [(node[0] + 1, node[1]), (node[0] - 1, node[1]), (node[0], node[1] + 1), (node[0], node[1] - 1)]
        for x, y in neighbours:
            if x < 0 or y < 0 or x > max_x or y > max_y:
                continue

            if not unvisited[x][y]:
                continue

            alt = distances[node[0]][node[1]] + grid[x][y]
            if alt < distances[x][y]:
                distances[x][y] = alt
                if (x, y) not in unvisited_with_non_inf_distance:
                    unvisited_with_non_inf_distance.append((x, y))

    return None


print(dijkstra(initial_grid))

# Expand columns
new_grid = []
for row in initial_grid:
    new_row = []
    for i in range(5):
        new_row.extend([(value + i - 1) % 9 + 1 for value in row])
    new_grid.append(new_row)

# Expand rows
for i in range(1, 5):
    for row_index in range(len(initial_grid)):
        new_row = [(value + i - 1) % 9 + 1 for value in new_grid[row_index]]
        new_grid.append(new_row)

print(dijkstra(new_grid))
