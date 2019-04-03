package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;


/**
 * @see IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers;
    private int size;
    private int overallSize;
    private IDictionary<T, Integer> map;
    T[] myGenericArray;

    // You will probably need to add one or two more fields in order to
    // successfully implement this class.

    public ArrayDisjointSet() {

        overallSize=10;
        myGenericArray = (T[]) new Object[overallSize];
        pointers= new int[overallSize];
        map=new ChainedHashDictionary<>();


    }

    @Override
    public void makeSet(T item) {
        // already a part of this disjoint set somewhere
        if (map.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        if (size == overallSize) {
            extendsCapacity();
        }

        map.put(item, size);
        myGenericArray[size]=item;
        pointers[size]=-1;
        size++;
    }

    public void extendsCapacity(){
        int[]temp=new int[overallSize*2];
        T[] tempT = (T[]) new Object[overallSize * 2];
        for (int i = 0; i < size; i++) {
            temp[i]=pointers[i];
            tempT[i]=myGenericArray[i];
        }
        pointers=temp;
        myGenericArray=tempT;
        overallSize*=2;
    }

    @Override
    public int findSet(T item) {
        if (!map.containsKey(item)) {
            throw new IllegalArgumentException();
        }

        int modify = map.get(item);
        int result = findValue(item, modify);

        resetRank(modify, result);
        return result;

    }

    private int findValue(T item, int root) {
        if (pointers[root] < 0) {
                return root;
        } else {
                root=pointers[root];
            return findValue(myGenericArray[root], root);
            }
        }





    @Override
    public void union(T item1, T item2) {
        if (!map.containsKey(item1) || !map.containsKey(item2) || findSet(item1) == findSet(item2)) {
            throw new IllegalArgumentException();
        }
        int index1=findSet(item1);
        int index2=findSet(item2);
        int rankV1=pointers[index1];
        int rankV2=pointers[index2];
        // if (rankV1 <= rankV2) {
        //     pointers[index2]=index1;
        //     pointers[index1]-=1;
        // } else {
        //
        //     pointers[index1]=index2;
        //     pointers[index2]-=1;
        // }

        if (rankV1 == rankV2) {
            pointers[index2]=index1;
            pointers[index1]-=1;

        }
        if (rankV1 > rankV2) {
            pointers[index1] = index2;
        } else if (rankV2 > rankV1) {
            pointers[index2] = index1;
        }

    }
    private void resetRank(int modify, int result) {
        if (pointers[modify] < 0) {
            return;
        }
        pointers[modify] = result;
        resetRank(pointers[modify], result);
    }
    //for testing correct rank
    // public int findRank(T item){
    //     int index1=findSet(item);
    //     return -pointers[index1]-1;
    // }




}

