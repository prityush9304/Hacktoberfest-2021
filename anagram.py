#str1 = Plane
#str2 = Nepal
# converting both the strings to lowercase
str1 = str1.lower()
str2 = str2.lower()

# checking if the length is same
if(len(str1) == len(str2)):

    # sorting the strings
    sorted_str1 = sorted(str1)
    sorted_str2 = sorted(str2)

    # if the sorted char arrays are same
    if(sorted_str1 == sorted_str2):
        print(str1 + " and " + str2 + " are anagram.")
    else:
        print(str1 + " and " + str2 + " are not anagram.")

else:
    print(str1 + " and " + str2 + " are not anagram.")
