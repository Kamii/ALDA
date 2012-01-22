import java.util.EmptyStackException;
public class DoubleStack<E>{
    /*
     *En array som ska innehålla de två stackarna
     *En int som ska ska hålla index för första platsen i den andra stacken
     */ 
    private E[] stackar;
    private int stackBorder;
    /*
     *Konstruktor som initierar stackar till en generisk array mha cast. 
     *@SuppressWarnings för att ignorera varningen castet genererar.
     *Arrayens storlek sätts till 2.
     *stackBorder sätts till 1 för att indikera index motsvarande andra stackens första plats.
     */ 
    @SuppressWarnings({"unchecked"})
        public DoubleStack(){
            stackar = (E[]) new Object[2]; 
            stackBorder = 1; 
        }
    /*
     *pushToFirst tar ett element och lägger in det överst i den första stacken.
     *Första halvan av arrayen itereras över från index 0 tills en plats hittas
     *som är null varpå elementet placeras där.
     *Ifall ingen tom plats hittas kallas expand för att skapa mer utrymme, 
     *Därefter itererar man igen över stacken och lägger elementet i den första
     *av de tomma platserna.
     */ 
    public void pushToFirst(E elem){
        boolean bool = true;
        while(bool){
            for(int i = 0; i<stackBorder ; i++){
                if(stackar[i]==null){
                    stackar[i]=elem;
                    bool = false;
                }
            }
            if(bool)
                expand();
        }
    }
    /*
     *Fungerar på samma sätt som pushToFirst med skillnaden att den börjar
     *iterera över arrayen från stackBorder till slutet av den.
     */ 
    public void pushToSecond(E elem){
        boolean bool = true;
        while(bool){
            for(int i = stackBorder; i<stackar.length; i++){
                if(stackar[i]==null){
                    stackar[i]=elem;
                    bool = false;
                }
            }
            if(bool)
                expand();
        }
    }

    /*
     *expand kallas från push metoderna för att göra plats för ytterligare
     *element när någon av stackarna är fulla.
     *En temporär array, dubbelt så stor som stackar[] skapas och värdena kopieras till den.
     *Detta görs i en for loop som gör hälften så många iterationer som stackars längd.
     *För varje iteration läggs elementen på n:te platsen i stackarna ett och två till i temp.
     *@SuppressWarnings används även här för att ignorera castet av temp arrayen.
     *Slutligen dubblas stackBorder och stackar sätts till temp.
     */ 
    @SuppressWarnings({"unchecked"})
        private void expand(){
            E[] temp = (E[]) new Object[stackar.length*2];
            for(int i = 0; i<stackBorder ; i++){
                temp[i]=stackar[i];   
                temp[(stackBorder*2)+i] = stackar[stackBorder+i];
            }
            stackBorder = stackBorder*2;
            stackar = temp;
        }

    /*
     *peekFirst returnerar det senaste pushade värdet i första stacken.
     *Den itererar bakifrån från slutet på stack ett tills den 
     *stöter på en plats som inte är tom.
     *Om inget element påträffas kastas EmptyStackException.
     */ 
    public E peekFirst(){
        for(int i=stackBorder-1; i>=0;i--){
            if(stackar[i]!=null)
                return stackar[i];
        }
        throw new EmptyStackException();  
    }

    /*
     *Samma som peekFirst fast på andra stacken.
     *Börjar iterera från slutet av arrayen istället.
     */ 
    public E peekSecond(){
        for(int i=stackar.length; i>=stackBorder-1; i--){
            if(stackar[i]!=null)
                return stackar[i];
        }
        throw new EmptyStackException();  
    }

    /*
     *popFromFirst itererar över första stacken på samma sätt som peekFirst.
     *Enda skillnaden är att elementet tas bort innan det returneras,
     *därav temp elementet.
     */ 
    public E popFromFirst(){
        E temp=null;
        for(int i=stackBorder-1; i>=0;i--){
            if(stackar[i]!=null){
                temp=stackar[i];
                stackar[i] = null;
                return temp;
            }
        }

        throw new EmptyStackException();  
    }

    /*
     *popFromSecond fungerar som popFromFirst fast självfallet med elementen
     *från andra stacken.
     */
    public E popFromSecond(){
        E temp=null;
        for(int i=stackar.length-1; i>=stackBorder-1; i--){
            if(stackar[i]!=null){
                temp=stackar[i];
                stackar[i] = null;
                return temp;
            }
        }
        throw new EmptyStackException();  
    }

    /*
     *firstIsEmpty kollar om första stacken är tom på element.
     *Ifall elementet i botten av stacken är null innebär det
     *att hela stacken är tom varpå false returneras.
     */ 
    public boolean firstIsEmpty(){
        return stackar[0]==null;
    }

    /*
     *secondIsEmpty gör samma sak som firstIsEmpty men använder 
     *stackBorder som index för jämförelsen, för att komma åt
     *första elementet i andra stacken.
     */ 
    public boolean secondIsEmpty(){
        return stackar[stackBorder]==null;
    } 
    /*
     *searchFirst letar igenom stacken efter ett objekt och returnerar
     *en int motsvarande hur långt ner i stacken det ligger, översta
     *elementer får nummer ett, osv.
     *Detta görs genom iteration från toppen av stacken neråt.
     */ 
    public int searchFirst(Object o){
        int distance=0;
        for(int i=stackBorder-1; i>=0;i--){
            if(stackar[i]!=null){
                distance++;
                if(o.equals(stackar[i])) 
                    return distance;
            }
        }
        return -1;
    }

    /*
     *searchSecond fungerar på samma vis som searchFirst fast på
     *andra stacken.
     */ 
    public int searchSecond(Object o){
        int distance=0;
        for(int i=stackar.length-1; i>=stackBorder-1; i--){
            if(stackar[i]!=null){
                distance++;
                if(o.equals(stackar[i])) 
                    return distance;
            }
        }
        return -1;
    }

}
