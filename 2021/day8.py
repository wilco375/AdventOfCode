file1 = open('input8.txt', 'r')

digits = [
    'abcefg',
    'cf',
    'acdeg',
    'acdfg',
    'bcdf',
    'abdfg',
    'abdefg',
    'acf',
    'abcdefg',
    'abcdfg'
]


class Display:
    def __init__(self, display_text):
        self.signals = [signal.strip() for signal in display_text.replace('| ', '').split(' ') if signal.strip() != '']
        self.result_signals = display_text.split('|')[1].strip().split(' ')
        self.solution = []

        self.solve()
        print(self.solution)

    def solve(self):
        one = [signal for signal in self.signals if len(signal) == 2][0]
        four = [signal for signal in self.signals if len(signal) == 4][0]
        seven = [signal for signal in self.signals if len(signal) == 3][0]
        eight = [signal for signal in self.signals if len(signal) == 7][0]
        cf = one
        a = seven.replace(cf[0], '').replace(cf[1], '')
        bd = four.replace(cf[0], '').replace(cf[1], '')
        eg = eight.replace(four[0], '').replace(four[1], '').replace(four[2], '').replace(four[3], '').replace(a, '')
        for signal in self.result_signals:
            if len(signal) == 2:
                self.solution.append(1)
            elif len(signal) == 4:
                self.solution.append(4)
            elif len(signal) == 3:
                self.solution.append(7)
            elif len(signal) == 7:
                self.solution.append(8)
            elif len(signal) == 5:
                if cf[0] in signal and cf[1] in signal:
                    self.solution.append(3)
                else:
                    if eg[0] in signal and eg[1] in signal:
                        self.solution.append(2)
                    else:
                        self.solution.append(5)
            elif len(signal) == 6:
                if eg[0] in signal and eg[1] in signal:
                    if cf[0] in signal and cf[1] in signal:
                        self.solution.append(0)
                    else:
                        self.solution.append(6)
                else:
                    self.solution.append(9)


displays = [Display(display) for display in file1.readlines() if display.strip() != '']

print(sum([len([solution for solution in display.solution if solution in [1, 4, 7, 8]]) for display in displays]))
print(sum([int(''.join(map(str, display.solution))) for display in displays]))
