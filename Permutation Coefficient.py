def permutationCoeff(n, k):
 
    P = [[0 for i in range(k + 1)]
            for j in range(n + 1)]
            
    for i in range(n + 1):
        for j in range(min(i, k) + 1):
 
         
            if (j == 0):
                P[i][j] = 1
            else:
                P[i][j] = P[i - 1][j] + (
                        j * P[i - 1][j - 1])
            if (j < k):
                P[i][j + 1] = 0
    return P[n][k]

n = 10
k = 2
print("Value fo P(", n, ", ", k, ") is ",
    permutationCoeff(n, k), sep = "")
