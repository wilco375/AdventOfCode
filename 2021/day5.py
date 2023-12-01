import re

file1 = open('input5.txt', 'r')
filelines = [line for line in file1.readlines() if line.strip() != '']


class Line:
    def __init__(self, fileline):
        values = re.findall(r'\d+', fileline)
        self.x1 = int(values[0])
        self.y1 = int(values[1])
        self.x2 = int(values[2])
        self.y2 = int(values[3])

    def is_horizontal_or_vertical(self):
        return self.x1 == self.x2 or self.y1 == self.y2

    def is_diagonal(self):
        return not self.is_horizontal_or_vertical()

    def draw(self, coords):
        if self.is_horizontal_or_vertical():
            for x in range(min(self.x1, self.x2), max(self.x1, self.x2) + 1):
                for y in range(min(self.y1, self.y2), max(self.y1, self.y2) + 1):
                    if (x, y) in coords:
                        coords[(x, y)] += 1
                    else:
                        coords[(x, y)] = 1
        else:
            for i in range(max(self.x1, self.x2) - min(self.x1, self.x2) + 1):
                if self.x1 > self.x2 and self.y1 > self.y2 or self.x1 < self.x2 and self.y1 < self.y2:
                    # \
                    key = (min(self.x1, self.x2) + i, min(self.y1, self.y2) + i)
                else:
                    # /
                    key = (min(self.x1, self.x2) + i, max(self.y1, self.y2) - i)
                if key in coords:
                    coords[key] += 1
                else:
                    coords[key] = 1

lines = []
for fileline in filelines:
    lines.append(Line(fileline))

# Horizontal and vertical lines
coords = {}
for line in lines:
    if line.is_horizontal_or_vertical():
        line.draw(coords)

print(len([coord for coord in coords.values() if coord >= 2]))

# All lines
coords = {}
for line in lines:
    line.draw(coords)

print(len([coord for coord in coords.values() if coord >= 2]))
