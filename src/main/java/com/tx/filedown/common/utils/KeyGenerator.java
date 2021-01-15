package com.tx.filedown.common.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class KeyGenerator {
    public KeyGenerator() {
    }

    public static String getUniqueID() {
        Calendar calendar_z = Calendar.getInstance();
        Date now_z = calendar_z.getTime();
        SimpleDateFormat formatDate_z = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SecureRandom seed_z = new SecureRandom();
        Random random_z = new Random();
        random_z.setSeed((long)seed_z.nextInt(100000000));
        return formatDate_z.format(now_z) + (100000 + random_z.nextInt(899999));
    }

    public static void main(String[] args) {
        System.out.println(getUniqueID());
    }
}
