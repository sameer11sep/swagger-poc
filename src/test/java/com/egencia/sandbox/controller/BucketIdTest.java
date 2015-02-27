package com.egencia.sandbox.controller;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by samarora on 2/2/15.
 */
public class BucketIdTest {

    public static final int NUMBER_OF_RECORDS = 1000000;
    
    public HashSet<Integer> integers=new HashSet<Integer>();

    @Test
    public void shouldBuildUniformDistributionForABCTesting() {
        Random r = new Random();
        int a = 0;
        int b = 0;
        int[] distribution = new int[101];
        for (int j = 0; j < NUMBER_OF_RECORDS; j++) {
            int hash = consistentHash(r.nextLong(), 100);
            distribution[hash] = distribution[hash] + 1;
        }
        for (int k = 0; k < distribution.length / 3; k++) {
            a += distribution[k];
        }

        for (int k = distribution.length / 3; k < (2 * distribution.length) / 3; k++) {
            b += distribution[k];
        }

        int c = NUMBER_OF_RECORDS - (a + b);
        System.out.println("Share of Bucket A "+(((double) a * 100 / NUMBER_OF_RECORDS)));
        System.out.println("Share of Bucket B "+(((double) b * 100 / NUMBER_OF_RECORDS)));
        System.out.println("Share of Bucket C "+(((double) c * 100 / NUMBER_OF_RECORDS)));
        ArrayList<Comparable> comparables = new ArrayList<Comparable>(integers);
        Collections.sort(comparables);
        System.out.println(comparables);
        Assert.assertEquals((((double) a * 100 / NUMBER_OF_RECORDS)), 33.33, 1.0);
        Assert.assertEquals((((double) b * 100 / NUMBER_OF_RECORDS)), 33.33, 1.0);
        Assert.assertEquals((((double) c * 100 / NUMBER_OF_RECORDS)), 33.33, 1.0);

    }

    @Test
    public void shouldBuildUniformDistributionForABTesting() {
        Random r = new Random();
        int a = 0;
        int[] distribution = new int[101];
        for (int j = 0; j < NUMBER_OF_RECORDS; j++) {
            int hash = consistentHash(r.nextLong(), 100);
            distribution[hash] = distribution[hash] + 1;
        }
        for (int k = 0; k < distribution.length / 2; k++) {
            a += distribution[k];
        }


        int b = NUMBER_OF_RECORDS - (a);
        System.out.println("Share for Bucket A "+(((double) a * 100 / NUMBER_OF_RECORDS)));
        System.out.println("Share for Bucket B "+(((double) b * 100 / NUMBER_OF_RECORDS)));
        Assert.assertEquals((((double) a * 100 / NUMBER_OF_RECORDS)), 50.0, 1.0);
        Assert.assertEquals((((double) b * 100 / NUMBER_OF_RECORDS)), 50.0, 1.0);

    }

    public int consistentHash(long input, int buckets) {
        long h = input;
        int candidate = 1;
        int next;
        while (true) {
            h = 2862933555777941757L * h + 1;
            double inv = 0x1.0p31 / ((int) (h >>> 33) + 1);
            next = (int) ((candidate + 1) * inv);
            if (next >= 0 && next <= buckets) {
                candidate = next;
            } else {
                integers.add(candidate);
                return candidate;
            }
        }
    }

}
