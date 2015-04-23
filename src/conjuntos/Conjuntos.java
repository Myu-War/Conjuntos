package conjuntos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class Conjuntos<T extends Comparable> implements SetADT<T>, Iterable<T> {
//atributos

    private T[] contenido;
    private final int DEFAULT_CAPACITY = 2;
    private int cont;
//constructor

    public Conjuntos() {
        cont = 0;
        contenido = (T[]) (new Comparable[DEFAULT_CAPACITY]);
    }

    @Override
    public void add(T elem) {
        if (elem != null) {
            if (!contains(elem)) {
                if (cont == contenido.length) {
                    expandCapacity();
                }
                contenido[cont] = (T) elem;
                cont++;
            }
        }
    }

    @Override
    public boolean contains(T elem) {
        int corredor = 0;
        while (corredor < cont) {
            if (contenido[corredor].equals(elem)) {
                corredor = cont + 1;
            }
            corredor++;
        }
        if (corredor >= cont + 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(SetADT<T> set) {
        boolean res = false;
        int corredor = 0;

        if (cont == set.size()) {
            while (corredor < cont) {
                if (!set.contains(contenido[corredor])) {
                    corredor = cont + 1;
                }
                corredor++;
            }
            if (corredor == cont) {
                res = true;
            }
        }

        return res;
    }

    private void expandCapacity() {
        T[] aux = (T[]) new Comparable[DEFAULT_CAPACITY * 2];

        for (int i = 0; i < contenido.length; i++) {
            aux[i] = contenido[i];
        }
        contenido = aux;
    }

    @Override
    public T remove(T elem) {
        T aux = null;
        int corredor = 0;

        if (contains(elem)) {
            while (corredor < cont && !contenido[corredor].equals(elem)) {
                corredor++;
            }
            aux = contenido[corredor];
            contenido[corredor] = contenido[cont - 1];
            contenido[cont - 1] = null;
            cont--;
        }

        return aux;
    }

    @Override
    public SetADT union(SetADT set) {
        SetADT un = new Conjuntos();
        Iterator<T> it = set.iterator();

        for (int i = 0; i < cont; i++) {
            un.add(contenido[i]);
        }
        for (int j = 0; j < set.size(); j++) {
            un.add(it.next());
        }

        return un;
    }

    public SetADT diferencia(SetADT<T> set) {
        SetADT dif = new Conjuntos();
        Iterator<T> it = set.iterator();
        T aux = null;

        if(!equals(set)){
            while(it.hasNext()){
                dif.add(it.next());
            }
            for(int i=0; i<cont; i++){
                if(dif.contains(contenido[i])){
                    dif.remove(contenido[i]);
                }
                else{
                    dif.add(contenido[i]);
                }
            }
        }
        
        return dif;
    }

    @Override
    public boolean isEmpty() {
        boolean res = true;

        if (cont > 0) {
            res = false;
        }

        return res;
    }

    @Override
    public int size() {
        return cont;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorArray(contenido, cont);
    }

    @Override
    public SetADT intersect(SetADT set) {
        SetADT in = new Conjuntos();

        for (int i = 0; i < cont; i++) {
            if (set.contains(contenido[i])) {
                in.add(contenido[i]);
            }
        }

        return in;
    }

    @Override
    public T removeRandom() {
        int borrado;
        T aux = null;
        Random rnd = new Random();

        if (cont > 0) {
            borrado = rnd.nextInt(cont);
            aux = contenido[borrado];
            contenido[borrado] = contenido[cont - 1];
            contenido[cont - 1] = null;
            cont--;
        } else {
            aux = (T) "El conjunto esta vacio";
        }

        return aux;
    }

    public void imprimeConjunto() {
        for (int i = 0; i < cont; i++) {
            System.out.print(contenido[i]);
        }
    }

    public class IteratorArray<T> implements Iterator<T> {

        private T[] cosas;
        private int pos;

        public IteratorArray(T[] contenido, int cont) {
            cosas = (T[]) (new Object[cont]);
            for (int i = 0; i < cont; i++) {
                cosas[i] = contenido[i];
            }
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < cosas.length;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return cosas[pos++];
            }
            throw new ElementNotFound();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public static void main(String[] args) {
        Conjuntos c = new Conjuntos();
        Conjuntos q = new Conjuntos();
        Conjuntos aux;

        c.add(3);
        c.add(6);
        c.imprimeConjunto();
        System.out.println(c.contains(3));
        q.add(6);
        q.add(2);
        q.imprimeConjunto();
        System.out.println(c.equals(q));
        aux = (Conjuntos) c.diferencia(q);
        aux.imprimeConjunto();
    }
}
