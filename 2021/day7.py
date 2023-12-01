file1 = open('input7.txt', 'r')
initial_positions = [int(x) for x in file1.readline().split(',')]

min_cost = None
min_cost_position = None
for i in range(min(initial_positions), max(initial_positions) + 1):
    cost = sum([abs(position - i) for position in initial_positions])
    if min_cost is None or cost < min_cost:
        min_cost = cost
        min_cost_position = i

print(min_cost)

min_cost = None
min_cost_position = None
for i in range(min(initial_positions), max(initial_positions) + 1):
    cost = sum([(steps * (steps + 1)) // 2 for steps in [abs(position - i) for position in initial_positions]])
    if min_cost is None or cost < min_cost:
        min_cost = cost
        min_cost_position = i

print(min_cost)