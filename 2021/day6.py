file1 = open('input6.txt', 'r')
initial_state = [int(x) for x in file1.readline().split(',')]

for days in [80, 256]:
    state = {x: initial_state.count(x) for x in set(initial_state)}
    for day in range(days):
        new_state = {}
        for x in state.keys():
            if x == 0:
                if 6 in new_state:
                    new_state[6] += state[x]
                else:
                    new_state[6] = state[x]
                if 8 in new_state:
                    new_state[8] += state[x]
                else:
                    new_state[8] = state[x]
            else:
                if x-1 in new_state:
                    new_state[x-1] += state[x]
                else:
                    new_state[x-1] = state[x]
        state = new_state
    print(sum(state.values()))

