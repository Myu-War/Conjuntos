
package conjuntos;

import java.util.Iterator;

public interface SetADT <T extends Comparable>{
    public void add(T elem);
    public T removeRandom();
    public T remove(T elem);
    public SetADT <T> union (SetADT<T> set);
    public boolean contains(T elem);
    public boolean equals(SetADT<T> set);
    public boolean isEmpty();
    public int size();
    public Iterator<T> iterator();
    public String toString();
    public SetADT <T> intersect(SetADT<T> set);
          
}
