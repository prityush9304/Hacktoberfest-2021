#Hello every one!! I am Pratyush and below is a code to find all permutations of a word(string) using recursion.

'''For finding permutations of the string through the method of recursion. We will continuously break the string separating a character from and finding the permutation of rest of the string again through the same method of recursion '''


#Recursive algorithm:

permutations_of_string_with_duplicates=[]       #This variable will store all our permutations without caring about duplicates that may have occurred. This is declared global so that it does not update itself with an empty loop while traversing through the below recursive function.

permutations_of_string_without_duplicates=[]    #This variable will stores all distinct permutations of the string. It is declared outside the recursive function(globally) due the same reasons as mentioned above.

def permutation_of_string( s , ans):
	global permutations_of_string_with_duplicates   
	
	global permutations_of_string_without_duplicates
	
	if len(s)==0:
		permutations_of_string_with_duplicates.append(ans)
		
	for i in range(0, len(s)):
		ch=s[i]                #saving the i th character of the string
		
		next_s=s[0:i]+s[i+1:]  #saving remaining string excluding above character in string variable next_s 
		
		permutation_of_string( next_s ,ans + ch)   #recursive call
		
		
	permutations_of_string_without_duplicates = list(set(permutations_of_string_with_duplicates))    #removing duplicate permutations
	
	return(permutations_of_string_without_duplicates)
	

	
#examples:		
if __name__ == '__main__':

	s = 'AABC'	                                  #YOU CAN CHANGE THE STRING WITH THE STRING OF YOUR CHOICE
	final_permutations=permutation_of_string(s, '')
	
	for i in range(0, len(final_permutations)):    #printing permutaions
		print(final_permutations[i])
