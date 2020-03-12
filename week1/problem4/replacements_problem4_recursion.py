number=int(input("Enter the number to be reduced to one"))
def replacements(n):
    if number==1:
        return 0
    elif number%2==0:
        return 1+replacements(number/2)#here adding 1 is like a count
    else:
        return 1+replacements(min(number+1,number-1))#1 is like count
result=replacements(n)
print(result)