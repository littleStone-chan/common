package com.o2osys.tools.commons;

import java.util.HashSet;
import java.util.UUID;

public class UuidBase64ShortMap {


    /**
     * 把UUID 转为 22位长字符串
     */
    public static String shorter(String s) {
        char[] res = Base64.encode(asBytes(s));
        return new String(res, 0, res.length - 2);
    }

    /**
     * 把22位长字符串转为 UUID
     */
    public static String recover(String s) {
        int len = s.length();
        char[] chars = new char[len + 2];
        chars[len] = chars[len + 1] = '_';
        for (int i = 0; i < len; i++) {
            chars[i] = s.charAt(i);
        }
        return toUUID(Base64.decode(chars)).toString();
    }

    public static byte[] asBytes(String id) {
        UUID uuid = UUID.fromString(UUIDHelper.boundSymbol(id));
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return buffer;

    }

    public static UUID toUUID(byte[] byteArray) {
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (byteArray[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (byteArray[i] & 0xff);
        }
        UUID result = new UUID(msb, lsb);

        return result;
    }

    /**
     * 生成一个压缩过的22位 uuid
     *
     * @return
     */
    public static String generate() {
        return UuidBase64ShortMap.shorter(UUID.randomUUID().toString());
    }

    public static void main(String[] args) {
//        StringShortMap shortm = new UuidBase64ShortMap();
//        String uuid = "3b01174f-3139-4a5b-a949-bb7e80b55f91";
//        System.out.println(Base64.encode(uuid));
//        String shorter= shortm.shorter(uuid);
//        System.out.println(shorter);
//        System.out.println(shortm.recover(shorter) +"\t" + uuid);

//        for(int i=0;i<100000;i++){
//            String Uuid = UUID.randomUUID().toString();
//            System.out.println(Uuid +"\t" + shortm.shorter(Uuid));
//        }

//        String[] uuids0 = {"f6e0e040-be3d-4573-964c-88724a8fa7d3", "c19b9de1-f33a-494b-afbe-f06817218d63", "f08f0b2c-66fb-41a3-99c3-ac206089c3ad"};
//        String[] uuids0 = {"f6e0e040-be3d-4573-964c-88724a8fa7d3"};
//        for (String s : uuids0) {
//            System.out.println(UuidBase64ShortMap.shorter(s));
//        }
//        String uuid = "f6e0e040be3d4573964c88724a8fa7d3";
//        System.out.println(UuidBase64ShortMap.shorter(uuid));

        //测试是否会生成相同的uuid
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();
        new Thread(new TtestThread()).start();

    }

    //用于测试多线程时是否会生成相同的uuid
    static class TtestThread implements Runnable {
        //用于测试是否会生成相同uuid的窗口
        static HashSet<String> testHashSet = new HashSet<>();

        @Override
        public void run() {
//            for (int i = 0; i < 100; i++) {
            while (true) {
                String shortUUID = UuidBase64ShortMap.generate();
                if (!testHashSet.contains(shortUUID)) {
                    testHashSet.add(shortUUID);
//                    System.out.println(shortUUID + "  " + new Date().getTime() + "  " + Thread.currentThread().getId());
                } else
                    System.out.println("uuid:" + shortUUID + "已存在");
            }
        }
    }

}

