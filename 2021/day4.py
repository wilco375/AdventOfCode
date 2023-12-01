import re

file1 = open('input4.txt', 'r')
lines = file1.readlines()

numbers = [int(number) for number in lines[0].split(',')]
lines = lines[2:]


class Card:
    def __init__(self, card_text):
        self.numbers = []
        for line in card_text.split('\n'):
            if line.strip() != '':
                self.numbers.append([int(number) for number in re.split(r"\s+", line) if number.strip() != ''])

    def has_bingo(self, numbers):
        # Horizontal
        for row in self.numbers:
            if all([number in numbers for number in row]):
                return True

        # Vertical
        for i in range(len(self.numbers[0])):
            if all([row[i] in numbers for row in self.numbers]):
                return True

        return False

    def unmarked_numbers(self, numbers):
        unmarked_numbers = []
        for row in self.numbers:
            for number in row:
                if number not in numbers:
                    unmarked_numbers.append(number)
        return unmarked_numbers


cards = []
card_text = ''
for line in lines:
    if line.strip() == '':
        cards.append(Card(card_text))
        card_text = ''
    else:
        card_text += line
if card_text.strip() != '':
    cards.append(Card(card_text))

# First to win
called_numbers = []
uncalled_numbers = numbers.copy()
while len(uncalled_numbers) > 0:
    called_number = uncalled_numbers.pop(0)
    called_numbers.append(called_number)
    bingo = False
    for card in cards:
        if card.has_bingo(called_numbers):
            bingo = True
            print(called_number * sum(card.unmarked_numbers(called_numbers)))
            break

    if bingo:
        break

# Last to win
called_numbers = []
uncalled_numbers = numbers.copy()
last_card = None
while len(uncalled_numbers) > 0:
    called_number = uncalled_numbers.pop(0)
    called_numbers.append(called_number)
    bingo = False
    if len([True for card in cards if card.has_bingo(called_numbers)]) == len(cards) - 1:
        last_card = [card for card in cards if not card.has_bingo(called_numbers)][0]

    if len([True for card in cards if card.has_bingo(called_numbers)]) == len(cards):
        print(called_number * sum(last_card.unmarked_numbers(called_numbers)))
        break
