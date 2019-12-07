package com.moci.simhappy;

import org.junit.Test;

import java.util.Random;

public class JavaTest {

    @Test
    public void random_test(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(10);
            System.out.print(num+" ");
        }
    }
}
