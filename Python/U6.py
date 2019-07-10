def mersenne(k):
    assert k>=0
    #Vorbedingung: P={mersenne==0,k>=0}
    base=2
    i=0    
    assert i-1<=k
    #INV={i-1<=k,2*mersenne==base-2}
    while(i<k): #B={i>=k}
        mersenne=base-1
        base<<=1
        i+=1       
    assert mersenne==2**k-1 
    assert i==k
    #Nachbedingung: Q={mersenne==2**k-1, i==k}
    return mersenne

k=int(input("Wie ist die Eingabe?\n"))
print("Das Ergebnis ist:",mersenne(k))

https://www.ibm.com/support/knowledgecenter/en/SSB23S_1.1.0.14/gtpc1/unixsock.html
