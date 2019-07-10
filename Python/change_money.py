"""
Übungsgruppe: Qianli Wang und Nazar Sopiha
"""
def helper(n,liste,coins):  #Aufgabe 3
    i=0
    if n==0:
        return []
    else:
        while(n>=0 and i<8):
            if(n>=coins[i]):
                n-=coins[i]
                liste.append(coins[i])
            else:
                i+=1
                continue                
        return [liste.count(200),liste.count(100),liste.count(50),liste.count(20),liste.count(10),liste.count(5),liste.count(2),liste.count(1)]

def test():
    coins=[200,100,50,20,10,5,2,1]
    n=int(input("What's the amount of money that you wonna change?\n"))
    print(helper(n,[],coins))

test()
