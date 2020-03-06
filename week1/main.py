"""n=int(input())
c=0
while(n):
    val=n&1
    c=c+val
    n=n>>1
"""
res=[0]
num=int(input())
for i in range(1,num+1):
    print(i,i>>1)
    res.append(res[i>>1]+(i&1))
print(res)