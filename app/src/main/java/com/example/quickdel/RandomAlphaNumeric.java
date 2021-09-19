package com.example.quickdel;

import java.util.Random;

public class RandomAlphaNumeric {
    final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    final char [] ALPHANUMERIC = (LETTERS + LETTERS.toUpperCase() + "0123456789").toCharArray();
    public String generateOrderNo (int length)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            result.append(ALPHANUMERIC[new Random().nextInt(ALPHANUMERIC.length)]);
        }
        return result.toString();
    }
        }
