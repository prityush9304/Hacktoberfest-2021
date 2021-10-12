#dfs concept
n=6
e=[[5,0],[5,2],[2,3],[4,0],[4,1],[3,1]]
am=[[] for x in range(n+1)]
for i in range(len(e)):
    am[e[i][0]].append(e[i][1])
print(am)
v=[0 for x in range(n+1)]
l=[]
def dfs(a):
    v[a] = 1 
    for x in am[a]:
        if v[x] == 0:
            dfs(x)
    l.append(a)
    
    return l
        
for i in range(0,n+1):
    if v[i] == 0:
        print(dfs(i))
