#Hello every one!! I am Pratyush and below is a code to find the maximum sub-array sum of a given array using Kadane's algorithm.

''' Sub-array is a continuous part of an array. And the optimal approach(with time complexity of O[n]) to find maximum sub-array sum in an array is Kadane's algo.

In this we traverse in the array with storing the continuous sum in a variable current sum(curr_sum) which is allotted value of zero if it becomes negative. And maximum value of this current sum would be stored in a variable maximum sum(max_sum) which will be our answer'''



#algorithm :
def maximum_subarray_Sum(arr,n):

	curr_sum , max_sum = 0, 0
	
	for i in range(0, n):
		curr_sum += arr[i]
		if curr_sum < 0:
			curr_sum = 0
			
		max_sum = max(curr_sum, max_sum)
		
	return max_sum

    

# test examples:

arr=[]
n = int(input("Enter number of elements : "))
print("\n Now enter the numbers one by one(press enter after inserting each element) : ")

for i in range(0, n):
    x = int(input())
 
    arr.append(x)
    
print("\n Maximum Sub Array Sum Is" , maximum_subarray_Sum(arr,n))

#example: if n=6 and arr=[-1, 4 , 4 , -6 , 7 , -4] then maximum sub-array sum is 9 and the sub array for this sum is [4 , 4 , -6 , 7 ].
