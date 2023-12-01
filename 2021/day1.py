file1 = open('input1.txt', 'r')
items = [int(x) for x in file1.readlines()]

last = None
count = 0
for value in items:
    if last is not None and last < value:
        count += 1
    last = value
        
print(count)

last = None
count = 0
for i in range(0, len(items)):
    minimum = max(0, i)
    maximum = min(len(items), i + 3)
    result = sum(items[minimum:maximum])
    if last is not None and last < result and len(items[minimum:maximum]) == 3:
        count += 1
    last = result
print(count)
