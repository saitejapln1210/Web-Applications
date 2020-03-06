def missingelement(array):
    arraytoset=set(array)
    requiredvalue=(3*sum(arraytoset)-sum(array))/2
    return requiredvalue
array=[int(i) for i in input().split()]
result=missingelement(array)
print(result)
