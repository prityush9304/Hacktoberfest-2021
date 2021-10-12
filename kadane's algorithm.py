# to find maximum subarray sum


def kadane(a):
    m=a[0]
    s=0
    
    for i in range(len(a)):
        m = m + arr[i]
        if m < 0:
            m = 0
        
        
        elif (s < m):
            s = m
            
    return s

arr = [-2, -3, 4, -1, -2, 5, -3]
print(kadane(arr))
