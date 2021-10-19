## cryptography - Hill cipher encryption algorithm implementation
## input - any plaintext and a key(mostly used of size 9)
## Matrix of 3*3 is formed 
## Output is a ciphertext generated using hill cipher encryption algorithm 
## Characters considered for encryption are A-Z and ".,!" So mod 29 method is used
## eg. Sample inputs - Plaintext - ACT, key - GYBNQKURP
## Output - JTA


plainText = input("Enter the Plain Text: ").upper()
key = input("Enter the key: ").upper()

plainText = "".join(u for u in plainText if u not in ("?", " ", ";", ":", "/", "[", "]"))
x = len(plainText)%3
if(x!=0):
    for i in range(3-x):
        plainText += 'X'

LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!'

#generate key matrix from input key string
#As key is a string of length 9, key matrix will be 3x3
keyMatrix = [[0] * 3 for i in range(3)] 
k = 0

for i in range(3):
    for j in range(3):
        keyMatrix[i][j] = LETTERS.find(key[k])
        k = k+1

# generate column vector for the inputted message
# As key vector is 3x3, the message vectors will be of size 3x1
size_message = int(len(plainText) / 3)
messageMatrix = [[0] * size_message for i in range(3)]

k = 0
j = 0
while(k < size_message):
    for i in range(3):
        messageMatrix[i][k] = LETTERS.find(plainText[j])
        j = j + 1
    k = k + 1

# encrypt the plain text into cipher text using hill cipher 
# C = KP mod 29
cipherMatrix = [[0] * size_message for i in range(3)]

for i in range(3):
    for j in range(size_message):
        cipherMatrix[i][j] = 0
        for x in range(3):
            #Matrix multiplication
            cipherMatrix[i][j] += (keyMatrix[i][x] * messageMatrix[x][j])
        # Taking mod 29 of the generated vector according to the formula
        cipherMatrix[i][j] = cipherMatrix[i][j] % 29

# Generate the encrypted text from above encrypted numbered matrix
CipherText = []

k = 0
while(k < size_message):
    for i in range(3):
        num = cipherMatrix[i][k]
        CipherText.append(LETTERS[num])
    k = k + 1

print("Ciphertext:", "".join(CipherText))

