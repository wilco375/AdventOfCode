import copy

file1 = open('input9.txt', 'r')
heightmap = [[int(height) for height in list(line.strip())] for line in file1.readlines() if line.strip() != '\n']

risk_level = 0
for i in range(len(heightmap)):
    for j in range(len(heightmap[i])):
        height = heightmap[i][j]
        left = heightmap[i-1][j] if i > 0 else 10
        right = heightmap[i+1][j] if i < len(heightmap)-1 else 10
        top = heightmap[i][j-1] if j > 0 else 10
        bottom = heightmap[i][j+1] if j < len(heightmap[i])-1 else 10
        if height < left and height < right and height < top and height < bottom:
            risk_level += height + 1

print(risk_level)


def is_basin(basins, x, y):
    for basin in basins:
        if (x, y) in basin:
            return True

    return False


def get_basin_index(basins, x, y):
    for i, basin in enumerate(basins):
        if (x, y) in basin:
            return i
    return -1


basins = []
last_basins = None
while basins != last_basins:
    last_basins = copy.deepcopy(basins)
    for i in range(len(heightmap)):
        for j in range(len(heightmap[i])):
            if is_basin(basins, i, j):
                continue

            height = heightmap[i][j]

            if height == 9:
                continue

            basin_indices = set()
            if i > 0:
                if is_basin(basins, i-1, j):
                    top = 10
                    basin_indices.add(get_basin_index(basins, i-1, j))
                else:
                    top = heightmap[i-1][j]
            else:
                top = 10
            if i < len(heightmap)-1:
                if is_basin(basins, i+1, j):
                    bottom = 10
                    basin_indices.add(get_basin_index(basins, i+1, j))
                else:
                    bottom = heightmap[i+1][j]
            else:
                bottom = 10
            if j > 0:
                if is_basin(basins, i, j-1):
                    left = 10
                    basin_indices.add(get_basin_index(basins, i, j-1))
                else:
                    left = heightmap[i][j-1]
            else:
                left = 10
            if j < len(heightmap[i])-1:
                if is_basin(basins, i, j+1):
                    right = 10
                    basin_indices.add(get_basin_index(basins, i, j+1))
                else:
                    right = heightmap[i][j+1]
            else:
                right = 10
            if height <= top and height <= bottom and height <= left and height <= right:
                if len(basin_indices) == 0:
                    basins.append([(i, j)])
                elif len(basin_indices) == 1:
                    basin = []
                    basin_indices = list(basin_indices)
                    basin_indices.sort(reverse=True)
                    for basin_index in basin_indices:
                        basin.extend(basins.pop(basin_index))
                    basin.append((i, j))
                    basins.append(basin)

basin_sizes = [len(basin) for basin in basins]
basin_sizes.sort(reverse=True)
print(basin_sizes[0] * basin_sizes[1] * basin_sizes[2])
