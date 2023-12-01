from functools import reduce

file1 = open('input16.txt', 'r')
input = file1.readline().strip().strip('0')

input = str(bin(int(input, 16)))[2:]
while len(input) % 4 != 0:
    # Add leading zeros lost in conversion
    input = '0' + input


class Packet:
    def __init__(self):
        self.version = None
        self.type = None
        self.val = None
        self.size = 0
        self.packets = []

    def process_packet(self, data):
        self.version = int(data[0:3], 2)
        self.type = int(data[3:6], 2)
        self.size += 6
        data = data[6:]

        if self.type == 4:
            # Number
            number = ''
            while data[0] == '1':
                # Not last group
                number += data[1:5]
                data = data[5:]
                self.size += 5

            # Last group
            number += data[1:5]
            data = data[5:]
            self.size += 5

            number = int(number, 2)

            self.val = number
        else:
            # Operator
            length_type_id = int(data[0])
            data = data[1:]
            self.size += 1
            if length_type_id == 0:
                # Bit length
                bit_length = int(data[0:15], 2)
                data = data[15:]
                self.size += 15

                bits_processed = 0
                while bits_processed < bit_length:
                    packet = Packet()
                    data = packet.process_packet(data)
                    self.packets.append(packet)
                    self.size += packet.size
                    bits_processed += packet.size
            else:
                # Number of sub-packets
                sub_packets = int(data[0:11], 2)
                data = data[11:]
                self.size += 11

                for i in range(sub_packets):
                    packet = Packet()
                    data = packet.process_packet(data)
                    self.packets.append(packet)
                    self.size += packet.size

        # Return unprocessed data
        return data

    def version_sum(self):
        return self.version + sum(packet.version_sum() for packet in self.packets)

    def value(self):
        if self.type == 0:
            # Sum
            return sum(packet.value() for packet in self.packets)
        elif self.type == 1:
            # Product
            return reduce(lambda x, y: x * y, (packet.value() for packet in self.packets))
        elif self.type == 2:
            # Minimum
            return min(packet.value() for packet in self.packets)
        elif self.type == 3:
            # Maximum
            return max(packet.value() for packet in self.packets)
        elif self.type == 4:
            # Literal value
            return self.val
        elif self.type == 5:
            # Greater than
            return int(self.packets[0].value() > self.packets[1].value())
        elif self.type == 6:
            # Less than
            return int(self.packets[0].value() < self.packets[1].value())
        elif self.type == 7:
            # Equal
            return int(self.packets[0].value() == self.packets[1].value())


packet = Packet()
packet.process_packet(input)
print(packet.version_sum())
print(packet.value())
