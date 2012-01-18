
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;
public class DoubleStack<E>{


	private static class Element<E> {
		public E data;
		public Element() {
		}

		public Element(E data) {
			this.data = data;
		}
	}
  
    private E[] stackar;
    private int stackBorder;
    
 
    @SuppressWarnings({"unchecked"})
    public DoubleStack(){//Class<E> typ){
        stackar = (E[]) new Object[2]; //Array.newInstance(typ,2);
        stackBorder = 1; 
    }
    
    public void pushToFirst(E elem){
        boolean added = false;
        while(!added){
        for(int i = 0; i<stackBorder ; i++){
            if(stackar[i]==null){
                stackar[i]=elem;
                return;
            }
        }
        expand();
        added = false; 
        }
    }

    public void pushToSecond(E elem){
        boolean added = false;
        while(!added){
        for(int i = stackBorder; i<stackar.length; i++){
            if(stackar[i]==null){
                stackar[i]=elem;
                return;
            }
        }
        expand();
        added = false; 
        }
    }

    @SuppressWarnings({"unchecked"})
    private void expand(){
    E[] temp = (E[]) new Object[stackar.length*2];//(E[]) Array.newInstance(typ,stackar.length*2);//
    for(int i = 0; i<stackBorder ; i++){
        temp[i]=stackar[i];   
        temp[(stackBorder*2)+i] = stackar[stackBorder+i];
    }
    stackBorder = stackBorder*2;
    stackar = temp;
    }
    
    public E peekFirst(){
        for(int i=stackBorder-1; i>=0;i--){
            if(stackar[i]!=null)
                return stackar[i];
        }
        throw new EmptyStackException();  
    }

    public E peekSecond(){
        for(int i=stackar.length; i>=stackBorder-1; i--){
            if(stackar[i]!=null)
                return stackar[i];
        }
        throw new EmptyStackException();  
    }

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

    public boolean firstIsEmpty(){
        return stackar[0]==null;
    }

    public boolean secondIsEmpty(){
        return stackar[stackBorder]==null;
    } 
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

    public static void main(String []args){
        DoubleStack<Integer> test = new DoubleStack<Integer>();
//ett
        test.pushToFirst(new Integer(110));

        test.pushToSecond(new Integer(210));
//två - exp
        test.pushToFirst(new Integer(110));

        test.pushToSecond(new Integer(220));
//tre - expand
        test.pushToFirst(new Integer(111));

        test.pushToSecond(new Integer(222));

        test.pushToFirst(new Integer(111));

        test.pushToSecond(new Integer(222));
//4 -exp
        test.pushToFirst(new Integer(111));

        test.pushToSecond(new Integer(222));

        test.pushToFirst(new Integer(111));

        test.pushToSecond(new Integer(222));

        System.out.println(test.searchSecond(new Integer(200)));
        System.out.println(test.searchFirst(new Integer(100)));

        System.out.println(test.searchSecond(new Integer(210)));
        System.out.println(test.searchFirst(new Integer(110)));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        test.pushToSecond(new Integer(222));
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.peekFirst());
        System.out.println(test.popFromFirst());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        System.out.println(test.popFromSecond());
        //System.out.println(test.popFromSecond());
        
        System.out.println(test.firstIsEmpty());
        System.out.println(test.secondIsEmpty());
    }
}
