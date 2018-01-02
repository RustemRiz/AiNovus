package ru.rustem.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class VisitsCounterBean {
    private int count;


    public void incrementCount(){
        count++;
    }

    public int getCount() {
        return count;
    }

}
