def minimumflips(number1_bin,number2_bin,bitwiseorresult_bin):
    count=0
    for i in range(len(number1_bin)):
        if number1_bin[i] | number2_bin[i] != bitwiseorresult_bin[i]:
            if bitwiseorresult_bin[i]==0:
                if number1_bin[i]==1 and number2_bin[i]==1:
                    count=count+2
                elif number1_bin[i] or number2_bin[i]:
                    count=count+1
        else:
            count=count+1
    return count
    
def checking(resultcount,expectedcount):
    if resultcount==expectedcount:
        return True
    else:
        return False
    
number1=int(input("Enter the input of first numer:"))
number2=int(input("Enter the input of second numer:"))
bitwiseorresult=int(input("Enter the wanted result we want when we do or operation of number1 and number2:"))
number1_bin=[int(i) for i in bin(number1)[2:]]
number2_bin=[int(i) for i in bin(number2)[2:]]
bitwiseorresult_bin=[int(i) for i in bin(bitwiseorresult)[2:]]#here bin method to convert into binary format.The ans will be in 0b101.So to remove 0b I sliced from 2 index.
max_len = max(len(number1_bin), len(number2_bin), len(bitwiseorresult_bin))#taking max_len of its binary form

if len(number1_bin)<max_len:
    number1_bin=[0]*(max_len-len(number1_bin))+number1_bin#appending zeroes if it is less than max_len
    
if len(number2_bin)<max_len:
    number2_bin=[0]*(max_len-len(number2_bin))+number2_bin#appending zeros if it is less than max_len
    
if len(bitwiseorresult_bin)<max_len:
    bitwiseorresult_bin=[0]*(max_len-len(bitwiseorresult_bin))+bitwiseorresult_bin#appending zeroes if it is less than max_len
    
expectedcount=3
resultcount=minimumflips(number1_bin,number2_bin,bitwiseorresult_bin)#calling the function which will return number of flips
print(resultcount)
checkingresult=checking(resultcount,expectedcount)
print(checkingresult)
