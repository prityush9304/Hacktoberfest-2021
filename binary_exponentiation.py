'''Hello everyone!! I am Pratyush and below is python code to find the exponents using binary exponentiation.

Normal exponentiation of a^b is good for small b, time and space limits could get exceeded by it. So for large b we use binary exponentiation which has time complexity of O{log(base 2)n} which is a drastic change as compared to normal exponentiation method having time complexity of 0{n}''' 

import sys

sys.setrecursionlimit(10**7)

def binary_exp(base , index):       #binary exponentiation using recursion
	
	if(index==0):
		return 1
	res= binary_exp(base, index//2)
	
	if(index%2==1):
		return res*res*base
		
	else:
		return res*res
		
		
if __name__=="__main__":
	
	base=int(input("enter the base number : "))
	
	index=int(input("enter the power number : "))
	
	answer=int(binary_exp(base , index))
	
	print(str(base) + "^" + str(index) + "=" + str(answer))
		
	
