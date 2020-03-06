def binaryrespresentationofintegers(number):
    result=[]
    result.append(0)
    for i in range(1,number+1):
        if i%2==0:
            result.append(result[i>>1])# if even ,appending half of number present in result 
        else:
            result.append(result[i>>1]+1)# if odd ,appending half of number present in result and plus 1 
    return result

def printing(result):
    for i in range(len(result)):
        print("{} number contains {} number of 1's".format(i,result[i]))

def binaryrespresentationofintegerstest(result,expectedoutput):
    if result==expectedoutput:
        return True
    else:
        return False

number=int(input("Enter the number:"))
result=binaryrespresentationofintegers(number)
printing(result)
expectedoutput=[0,1,1]
boolean=binaryrespresentationofintegerstest(result,expectedoutput)
print(boolean)