# importing the system specific parameters
import sys


numb = float(input("ENTER A NUMBER"))

# redirect print output to the file called result .txt the w stands for write
sys.stdout = open("result.txt", "w")

if  0 < numb < 100:
    print("POSITIVE")
    numb = 100 - numb
    print(numb)
elif numb > 100:
    print("POSITIVE")
    numb = numb - 100
    print(numb)

elif numb < 0:
    print("NEGATIVE")
    numb = numb + 100
    print(numb)

elif numb == 0:
    print("ZERO")
    numb = numb + 100
    print(numb)

# close after finishing writing to a text file
sys.stdout.close()





