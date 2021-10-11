#  Function to check if sum is possible
#  with remaining numbers
def possible(x,  S,  N):
 
        #  Stores the minimum sum possible with
        #  x numbers
    minSum = (x * (x + 1)) // 2
 
    # Stores the maximum sum possible with
    # x numbers
    maxSum = (x * ((2 * N) - x + 1)) // 2
 
    # If S lies in the range
    # [minSum, maxSum]
    if (S < minSum or S > maxSum):
        return False
 
    return True
 
#  Function to find the resultant
#  permutation
def findPermutation(N,  L, R,  S):
 
        # Stores the count of numbers
        # in the given segment
    x = R - L + 1
 
    # If the sum is not possible
    # with numbers in the segment
    if (not possible(x, S, N)):
 
                # Output -1
        print("-1")
        return
 
    else:
 
       # Stores the numbers present
       # within the given segment
        v = []
 
        # Iterate over the numbers from
        # 1 to N
        for i in range(N, 0, -1):
 
            # If (S-i) is a positive non-zero
            # sum and if it is possible to
            # obtain (S-i) remaining numbers
            if ((S - i) >= 0 and possible(x - 1, S - i, i - 1)):
 
                # Update sum S
                S = S - i
 
                # Update required numbers
                # in the segement
                x -= 1
 
                # Push i in vector v
                v.append(i)
 
                # If sum has been obtained
            if (S == 0):
 
                                # Break from the loop
                break
 
        # If sum is not obtained
        if (S != 0):
 
            # Output -1
            print(-1)
            return
 
        # Stores the numbers which are
        # not present in given segment
        v1 = []
 
        # Loop to check the numbers not
        # present in the segment
        for i in range(1, N+1):
 
            # Pointer to check if i is
            # present in vector v or not
            it = i in v
 
            # If i is not present in v
            if (not it):
 
                # Push i in vector v1
                v1.append(i)
 
        # Point to the first elements
        # of v1 and v respectively
        j = 0
        f = 0
 
        # Print the required permutation
        for i in range(1, L):
            print(v1[j], end = " ")
            j += 1
 
        for i in range(L, R + 1):
            print(v[f], end = " ")
            f += 1
 
        for i in range(R + 1, N + 1):
            print(v1[j], end = " ")
            j += 1
 
    return
 
# Driver Code
if __name__ == "__main__":
    N = 6
    L = 3
    R = 5
    S = 8
    findPermutation(N, L, R, S)
 
