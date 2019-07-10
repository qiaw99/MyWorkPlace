"""
Aufgabe 4 c)
Eingabe(Beispiel):
1 2     #Zwischen 1 und 2 soll es ein Leerzeichen geben.
        #Nach der Eingabe eines Punktes: 1 Mal [Enter] weiter einzugeben.
2 Mal [Enter] zu beenden
"""
import math
def triangle_area(p1,p2,p3):       #Aufgabe 3 a) und b)
    area=0
    p1p2=GetLineLength(p1,p2)       
    p2p3=GetLineLength(p2,p3) 
    p3p1=GetLineLength(p3,p1)
    s=(p1p2+p2p3+p3p1)/2
    area=s*(s-p1p2)*(s-p2p3)*(s-p3p1)
    area=math.sqrt(area)
    return area
    
def GetLineLength(p1,p2):       #Die Seitenlängen berechen
    length=math.pow((p1.x-p2.x),2)+math.pow((p1.y-p2.y),2)
    length=math.sqrt(length)
    return length
    
class Point():      #Klasse von Punkten
    def __init__(self,x,y):
        self.x=x
        self.y=y
        
def GetAreaOfPolygon(points):
    area=0
    if(len(points)<3):          #Fehlerbehandlung
        raise Exception("Too less arguments! It can't even build a triangle! ")
    p1=points[0]            #wählen wir einen bestimmten Punkt
    for i in range (1,len(points)-1):       #alle Flächen von Dreiecken addieren 
        p2=points[i]
        p3=points[i+1]
        triArea=triangle_area(p1,p2,p3)
        area+=triArea       
    return area

def main():
    final_list=[]
    line=input("Enter the list of points:\n")
    while (line!=''):       #Eine Liste, in der mehrere Tupeln enthält,kriegen.
        final_list.append(tuple(line.split()))
        line=input()
    x=0
    points=[]
    while(x<len(final_list)):
        a=int(final_list[x][0])     #x-Koordinate
        b=int(final_list[x][1])     #y-Koordinate
        p=Point(a,b)                #Punkte entstehen
        points.append(p)            #legen alle Punkte in eine Liste
        x+=1
    area=GetAreaOfPolygon(points)
    print("The area of Polygon before rounding is: "+str(area))
    print("The area of Polygon after rounding is "+str(math.ceil(area)))       #aufruden
    
if __name__=='__main__':
    main()
