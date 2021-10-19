s = "XYYZXZYZXXYZ" #4
x = 'XYZ'

# cpdct = dct
i=0
j=0
flag = 0
count = 0
cp = x
while(j<len(s)):

    if flag == 1 and j-i<len(x):
        if s[j] in cp:
            cp = cp.replace(s[j], "", 1)
        else:
            flag = 0
            cp = x
        
    if flag == 1 and j-i == 3:
        # print(i, j-1)
        count += 1
        cp = x
        flag = 2

    if flag == 2:
        i+=1
        if s[j] == s[i-1]:
            print(i, j+1)
            count += 1
            
        else:
            flag = 0
            cp = x

    if s[j] in cp and flag == 0:
        cp = cp.replace(s[j], "", 1)
        i = j
        print(j)
        flag = 1
    j+=1

print(count)
