def xoringbyslicing(queriesize,queriearray,array):
    resultarray=[]
    for i in range(queriesize):
        answer=0
        for j in range(queriearray[i][0],queriearray[i][1]+1):
            answer=answer ^ (array[j])
        resultarray.append(answer)
    return resultarray
def xoringbyslicingTest(resultarray,Expectedoutput):
    flag=0
    pos=0
    for i in range(len(resultarray)):
        if resultarray[i]!=Expectedoutput[i]:
            flag=1
            pos=i
            break
    if(flag==1):
        print("Your answer is wrong here {}".format(resultarray[pos]))
    else:
        print("True")
array=[int(i) for i in input("Enter The Array elements:").split()]
resultarray=[]
queriesize=int(input("Enter the number of Queries required:"))
queriearray=[[int(i) for i in input("Enter the Li and Ri values to enter into Queriearray:").split()] for i in range(queriesize)]
Expectedoutput=[2,7,14,8]
print("Given Array {}".format(array))
print("Querie Array {}".format(queriearray))
resulltarray=xoringbyslicing(queriesize,queriearray,array)
print("Resulltarray Array {}".format(resulltarray))
xoringbyslicingTest(resulltarray,Expectedoutput)


        