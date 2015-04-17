__author__ = 'lapofrati'

def main():
    counter = 0
    with open("TestInput", 'r') as f:
        with open("TestInputProcessed.txt", 'w') as out:
            for line in f:
                if(counter == 0):
                    out.write(line)
                    counter += 1
                else:
                    out.write(translate(line))
                    counter -= 1

def translate(letter):
    # There are 20 valid letters and one simbol representig the special letters b j o u x z
    # Each letter is then converted into a series of 21 digits (twenty 0 and one 1)
    translation = {"A": "1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "B": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1",
                   "C": "0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "D": "0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "E": "0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "F": "0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "G": "0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "H": "0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "I": "0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0",
                   "J": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1",
                   "K": "0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0",
                   "L": "0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0",
                   "M": "0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0",
                   "N": "0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0",
                   "O": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1",
                   "P": "0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0",
                   "Q": "0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0",
                   "R": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0",
                   "S": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0",
                   "T": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0",
                   "U": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1",
                   "V": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0",
                   "W": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0",
                   "X": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1",
                   "Y": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0",
                   "Z": "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1"}

    return translation[letter]

if __name__ == "__main__":
    main()