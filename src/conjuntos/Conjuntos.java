
package conjuntos;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class Conjuntos<T extends Comparable> implements SetADT<T>,Iterable<T>{
//atributos
    private T[] contenido;
    private final int DEFAULT_CAPACITY=2;
    private int cont;
//constructor
    public Conjuntos(){
        cont=0;
        contenido=(T[])new Object[DEFAULT_CAPACITY];
    }
    
    @Override
    public void add(T elem) {
        if(!contains(elem)){
            if(cont==contenido.length){
                expandCapacity();
            }
            contenido[cont]=(T) elem;
            cont++;
        }
    }
    
    @Override
    public boolean contains(T elem) {
        int corredor=0;
        while(corredor<cont){
            if(contenido[corredor].equals(elem)){
                corredor=cont+1;
            }
            corredor++;
        }
        if(corredor==cont+1){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(SetADT<T> set){
        boolean res=false;
        int corredor=0;
        
        if(cont==set.size()){
            while(corredor<cont){
                if(!set.contains(contenido[corredor])){
                    corredor=cont+1;
                }
            }
            if(corredor==cont){
                res=true;
            }
        }
        
        return res;
    }
   
    private void expandCapacity() {
        T[] aux=(T[])new Object[DEFAULT_CAPACITY*2];
        
        for(int i = 0; i < contenido.length; i++){
            aux[i] = contenido[i];
        }
        contenido=aux;
    }

    @Override
    public T remove(T elem) {
        T aux=null;
        int corredor=0;
        
        if(contains(elem)){
            while(corredor<cont && !contenido[corredor].equals(elem)){
                cont++;
            }
            aux=contenido[corredor];
            contenido[corredor]=contenido[cont];
            contenido[cont]=null;
            cont--;
        }
        
        
        return aux;
    }

    @Override
    public SetADT union(SetADT set) {
        SetADT un=new Conjuntos();
        ArrayList<T> aux = null;
        int j;
        
        for(int i=0;i<cont;i++){
            un.add(contenido[i]);
        }
        for(j=0;j<set.size();j++){
            aux.add((T) set.removeRandom());
            un.add(aux.get(j));
        }
        for(int h=0;h<j;h++){
            set.add(aux.get(h));
        }
        
        return un;
    }
    
    @Override
    public boolean isEmpty() {
        boolean res=true;
        
        if(cont>0){
            res=false;
        }
        
        return res;
    }

    @Override
    public int size() {
        return cont;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorArray(contenido,cont);
    }

    @Override
    public SetADT intersect(SetADT set) {
        SetADT in=new Conjuntos();
        
        for(int i=0;i<cont;i++){
            if(set.contains(contenido[i])){
                in.add(contenido[i]);
            }
        }
        
        return in;
    }

    @Override
    public T removeRandom() {
        int borrado;
        T aux=null;
        Random  rnd = new Random();
        
        borrado=rnd.nextInt(cont);
        aux=contenido[borrado];
        contenido[borrado]=contenido[cont];
        contenido[cont]=null;
        cont--;
        
        return aux;
    }

    public class IteratorArray implements Iterator<T> {
        private T[] cosas;
        private int pos;
        
        public IteratorArray(T[] contenido, int cont) {
            cosas=(T[])(new Object[cont]);
            for(int i=0;i<cont;i++){
                cosas[i]=contenido[i];
            }
            pos=0;
        }

        @Override
        public boolean hasNext() {
            return pos<cosas.length;
        }

        @Override
        public T next() {
            if(hasNext()){
                return cosas[pos++];
            }
            throw new ElementNotFound();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}