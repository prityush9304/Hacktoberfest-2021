def bubbleSort( arr ):
    n = len( arr )

    for i in range( n - 1 ) :
        flag = 0

        for j in range(n - 1) :
            
            if arr[j] > arr[j + 1] : 
                tmp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = tmp
                flag = 1

        if flag == 0:
            break

    return arr

ar = [21,6,9,33,3] 

result = bubbleSort(ar)

print (result)