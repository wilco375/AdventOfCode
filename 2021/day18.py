import json
import copy
import math

file1 = open('input18.txt', 'r')


class Fish:
    def __init__(self, left, right):
        self.left = left
        self.right = right
        self.parent = None
        self.is_left = False
        self.is_right = False
        if isinstance(left, Fish):
            left.parent = self
            left.is_left = True
        if isinstance(right, Fish):
            right.parent = self
            right.is_right = True

    @classmethod
    def from_list(cls, fish):
        if isinstance(fish[0], list):
            left = Fish.from_list(fish[0])
        else:
            left = int(fish[0])

        if isinstance(fish[1], list):
            right = Fish.from_list(fish[1])
        else:
            right = int(fish[1])

        return Fish(left, right)

    def __str__(self):
        return '[{},{}]'.format(self.left, self.right)

    def magnitude(self):
        if isinstance(self.left, Fish):
            magnitude = 3 * self.left.magnitude()
        else:
            magnitude = 3 * self.left
        if isinstance(self.right, Fish):
            magnitude += 2 * self.right.magnitude()
        else:
            magnitude += 2 * self.right
        return magnitude

    def depth(self):
        depth = 0
        if isinstance(self.left, Fish):
            depth = max(depth, self.left.depth() + 1)
        if isinstance(self.right, Fish):
            depth = max(depth, self.right.depth() + 1)
        return depth

    def deepest(self):
        left_depth = 0
        right_depth = 0
        if isinstance(self.left, Fish):
            left_depth = self.left.depth() + 1
        if isinstance(self.right, Fish):
            right_depth = self.right.depth() + 1

        if left_depth == 0 and right_depth == 0:
            return self
        elif left_depth == 0:
            return self.right.deepest()
        elif right_depth == 0:
            return self.left.deepest()
        else:
            if right_depth > left_depth:
                return self.right.deepest()
            else:
                return self.left.deepest()

    def max(self):
        if isinstance(self.left, Fish):
            max_fish = self.left.max()
        else:
            max_fish = self.left
        if isinstance(self.right, Fish):
            max_fish = max(max_fish, self.right.max())
        else:
            max_fish = max(max_fish, self.right)
        return max_fish

    def highest(self):
        if isinstance(self.left, Fish):
            left_max = self.left.max()
        else:
            left_max = self.left

        if left_max >= 10:
            if isinstance(self.left, Fish):
                return self.left.highest()
            else:
                return self
        else:
            if isinstance(self.right, Fish):
                return self.right.highest()
            else:
                return self

    def add(self, fish):
        new_fish = Fish(self, fish)
        new_fish.simplify()
        return new_fish

    def add_left(self, number):
        if isinstance(self.left, Fish):
            self.left.add_left(number)
        else:
            self.left += number

    def add_right(self, number):
        if isinstance(self.right, Fish):
            self.right.add_right(number)
        else:
            self.right += number

    def explode(self):
        if self.is_left:
            if isinstance(self.parent.right, Fish):
                self.parent.right.add_left(self.right)
            else:
                self.parent.right += self.right

            node = self
            while node.parent is not None and node.is_left:
                node = node.parent
            if node.parent is not None:
                if isinstance(node.parent.left, Fish):
                    node.parent.left.add_right(self.left)
                else:
                    node.parent.left += self.left

            self.parent.left = 0
        elif self.is_right:
            if isinstance(self.parent.left, Fish):
                self.parent.left.add_right(self.left)
            else:
                self.parent.left += self.left

            node = self
            while node.parent is not None and node.is_right:
                node = node.parent
            if node.parent is not None:
                if isinstance(node.parent.right, Fish):
                    node.parent.right.add_left(self.right)
                else:
                    node.parent.right += self.right

            self.parent.right = 0

    def split(self):
        if not isinstance(self.left, Fish) and self.left >= 10:
            self.left = Fish(self.left // 2, math.ceil(self.left / 2))
            self.left.parent = self
            self.left.is_left = True
        elif not isinstance(self.right, Fish) and self.right >= 10:
            self.right = Fish(self.right // 2, math.ceil(self.right / 2))
            self.right.parent = self
            self.right.is_right = True

    def simplify(self):
        if self.depth() >= 4:
            # Explode
            self.deepest().explode()
        elif self.max() >= 10:
            # Split
            self.highest().split()
        else:
            # Stop simplification
            return

        # Try to simplify again
        self.simplify()


lines = file1.readlines()
initial_fishes = [Fish.from_list(json.loads(fish)) for fish in lines]
fishes = copy.deepcopy(initial_fishes)
fish = fishes[0]
for other_fish in fishes[1:]:
    fish = fish.add(other_fish)
print(fish.magnitude())

max_magnitude = 0
for fish1 in lines:
    for fish2 in lines:
        if fish1 == fish2:
            continue
        magnitude = Fish.from_list(json.loads(fish1)).add(Fish.from_list(json.loads(fish2))).magnitude()
        if magnitude > max_magnitude:
            max_magnitude = magnitude
print(max_magnitude)

