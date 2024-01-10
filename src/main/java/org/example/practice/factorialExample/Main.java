package org.example.practice.factorialExample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> list = Arrays.asList(919909L,3450L, 2539L, 8782L, 2389L, 9970L, 2301L, 5899L );
        List<FactorailThread> threads = new ArrayList<>();
        for (long input : list){
            threads.add(new FactorailThread(input));
        }
        for(FactorailThread thread : threads){
            thread.start();
        }
        for(FactorailThread thread : threads){
            try {
                thread.join(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        for(int i = 0; i < list.size(); i++){
            FactorailThread factorailThread = threads.get(i);
            if(factorailThread.isFinished()){
                System.out.println("factorial of "+list.get(i)+" is "+factorailThread.getResult());
            }
            else {
                System.out.println("The calculation for "+list.get(i)+" is still in process");
            }
        }

        System.exit(0);
    }
}

class FactorailThread extends Thread{
    private Long inputNumber;
    private BigInteger result = BigInteger.ZERO;
    private boolean isFinished = false;
    public FactorailThread(Long inputNumber){
        this.inputNumber = inputNumber;
    }
    @Override
    public void run(){
        this.result = calculateFactorial(inputNumber);
        this.isFinished = true;
    }

    private BigInteger calculateFactorial(long inputNumber){
        BigInteger res = BigInteger.ONE;
        for(long i = inputNumber; i> 0; i--){
            res = res.multiply(new BigInteger(Long.toString(i)));
        }
        return res;
    }

    public boolean isFinished(){
        return isFinished;
    }
    public BigInteger getResult(){
        return result;
    }
}