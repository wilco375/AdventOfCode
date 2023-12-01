file1 = open('input12.txt', 'r')

paths = {}

for line in file1.readlines():
    if line.strip() == '':
        continue
    line = line.strip().split('-')

    if line[0] in paths:
        paths[line[0]].append(line[1])
    else:
        paths[line[0]] = [line[1]]

    if line[1] in paths:
        paths[line[1]].append(line[0])
    else:
        paths[line[1]] = [line[0]]


def generate_paths(taken=None, visit_small_twice=False):
    if taken is None:
        taken = ['start']

    last_node = taken[-1]
    if last_node == 'end':
        return [taken]

    generated_paths = []
    for node in paths[last_node]:
        if node == 'start':
            continue
        if node.islower() and node in taken:
            if not visit_small_twice or visited_small_twice(taken):
                continue

        generated_paths.extend(generate_paths(taken + [node], visit_small_twice))

    return generated_paths


def visited_small_twice(path):
    seen = []
    for node in path:
        if node.islower() and node in seen:
            return True
        seen.append(node)
    return False


print(len(generate_paths()))
print(len(generate_paths(visit_small_twice=True)))
