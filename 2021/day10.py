import re

file1 = open('input10.txt', 'r')
lines = [line.strip() for line in file1.readlines() if line.strip() != '\n']

syntax_points = {
    ')': 3,
    ']': 57,
    '}': 1197,
    '>': 25137
}
completion_points = {
    ')': 1,
    ']': 2,
    '}': 3,
    '>': 4
}

closing_char = {
    '(': ')',
    '[': ']',
    '{': '}',
    '<': '>'
}

syntax_error_score = 0
completion_scores = []
for line in lines:
    last_line = None
    while last_line != line:
        last_line = line
        line = line.replace('()', '').replace('[]', '').replace('{}', '').replace('<>', '')

    if line == '':
        # No syntax errors
        continue
    syntax_error = re.search(r"(\([]}>]|\[[)}>]|{[)\]>]|<[)\]}])", line)
    if syntax_error is not None:
        # Invalid line
        syntax_error_score += syntax_points[syntax_error.group(0)[1]]
    else:
        # Incomplete line
        completion_score = 0
        while line != '':
            char_to_add = closing_char[line[-1]]
            completion_score = completion_score * 5 + completion_points[char_to_add]
            line = line + char_to_add
            line = line.replace('()', '').replace('[]', '').replace('{}', '').replace('<>', '')
        completion_scores.append(completion_score)

print(syntax_error_score)
completion_scores.sort()
print(completion_scores[len(completion_scores) // 2])

