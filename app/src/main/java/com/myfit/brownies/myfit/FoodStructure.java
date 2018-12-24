package com.myfit.brownies.myfit;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodStructure<E>  {
    int size;
    int capacity;
    ArrayList<E> bucket[];
    Calendar calendar;

    FoodStructure() {
        this(1000);
    }

    public FoodStructure(int cap) {
        capacity = cap;
        bucket = (ArrayList<E>[]) new ArrayList[cap];
        size = 0;
        for (int i = 0; i < cap; i++)
            bucket[i] = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E get(int i) {
        calendar= Calendar.getInstance();
        int h = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (bucket[h] != null) {
            E p = bucket[h].get(i);
            return p;
        }
        return null;
    }

    public void add(E k, int h) {
        if (bucket[h] == null)
            bucket[h] = new ArrayList<>();

        bucket[h].add(k);
        size++;
    }

    public void remove(E k) {
        calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (bucket[h] != null) {
            for (int i = 0; i < bucket[h].size(); i++) {
                E p = bucket[h].get(i);
                if (p.equals(k)) {
                    bucket[h].remove(i);
                    size--;
                    return;
                }
            }
        }
    }

    public ArrayList<E> dayList(int x){
        ArrayList<E> v = new ArrayList<>();

        if (bucket[x] != null){
            for (int i = 0; i < bucket[x].size(); i++){
                E p = bucket[x].get(i);
                v.add(p);
            }
        }
        return v;
    }

    public E dumpData(int x) {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        E p = bucket[day].get(x);

        return p;
    }
}