package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 26.03.15.
 */

/*
    Klasa służąca za przechowywanie pary dwóch róznych lub takich samych typów.
 */
public class Pair<T, S> {
    private T first;
    private S second;

    public Pair(T __first, S __second) {
        first = __first;
        second = __second;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(T __first) {
        first = __first;
    }

    public void setObjectS(S __second) {
        second = __second;
    }

    @Override
    public boolean equals(Object __o) {
        if(!(__o instanceof Pair))
            return false;

        Pair _pair = (Pair)__o;
        return _pair.first.equals(this.first) && _pair.second.equals(this.second);
    }
}
