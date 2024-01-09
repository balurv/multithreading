package org.example.practice;

import com.sun.jdi.Value;

public class Main {


}
class Vault{
    private int password;
    public Vault(int password){
        this.password = password;
    }
    public boolean isCorrectPassword(int password){
        try {
            Thread.sleep(5);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return this.password == password;
    }
}

class HackerThread extends Thread{
    protected Vault vault;
    public HackerThread(Vault vault){
        this.vault = vault;
        this.setName(this.getClass().getSimpleName());
        this.setPriority(Thread.MAX_PRIORITY);
    }
    @Override
    public void start(){
        System.out.println("current thread:"+this.getName());
        super.start();
    }
}

class AscendingThread extends HackerThread{
    public static final int MAX_PASSWORD = 9999;
    public AscendingThread(Vault vault) {
        super(vault);
    }
    @Override
    public void run(){
        for(int i = 0; i < MAX_PASSWORD; i++){
            if(vault.isCorrectPassword(i)){
                System.out.println(this.getName()+" guees the password:"+i);
                System.exit(0);
            }
        }
    }
}