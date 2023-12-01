import re

file1 = open('input17.txt', 'r')
boundaries = re.search(r'target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)', file1.read()).groups()
boundaries_x = [int(boundaries[0]), int(boundaries[1])]
boundaries_y = [int(boundaries[2]), int(boundaries[3])]


def in_boundary(start_vel_x, start_vel_y):
    pos = [0, 0]
    vel = [start_vel_x, start_vel_y]
    highest_y = 0
    while True:
        # Calculate new position
        new_pos = [pos[0] + vel[0], pos[1] + vel[1]]

        # Update highest y
        if new_pos[1] > highest_y:
            highest_y = new_pos[1]

        # Move x velocity to 0
        if vel[0] > 0:
            vel[0] -= 1
        if vel[0] < 0:
            vel[0] += 1

        # Decrease y velocity by 1
        vel[1] -= 1

        # Check if new position is past boundary
        if new_pos[1] < boundaries_y[0] and vel[1] < 0:
            # Y too low and cannot get higher
            return None
        if new_pos[0] > boundaries_x[1] and vel[0] > 0:
            # X too high and cannot get lower
            return None

        if boundaries_x[0] <= new_pos[0] <= boundaries_x[1] and boundaries_y[0] <= new_pos[1] <= boundaries_y[1]:
            # In boundary
            return highest_y

        pos = new_pos


max_y = 0
in_target = 0
for vel_x in range(0, boundaries_x[1] + 1):
    for vel_y in range(boundaries_y[0], 100):
        highest_y = in_boundary(vel_x, vel_y)
        if highest_y is not None:
            max_y = max(max_y, highest_y)
            in_target += 1
print(max_y)
print(in_target)
