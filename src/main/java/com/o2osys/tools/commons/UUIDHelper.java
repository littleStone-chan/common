package com.o2osys.tools.commons;

/**
 * uuid 助手类
 * @author victim
 * @date 2018-09-01 13:52:00 *
 */
public class UUIDHelper {


    //定义32位UUID的长度
    static private final int UUID32 = 32;

    /**
     * @param uuid
     * @return
     * @deprecated 将32位的UUID添加连接符, 变为36位UUID
     */
    static public String boundSymbol(String uuid) {
        if (uuid.length() == UUIDHelper.UUID32) {
            uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, uuid.length());
//            System.out.println(uuid);
        }
        return uuid;
    }

    public static void main(String[] args) throws Exception {
        String uuid = "f6e0e040be3d4573964c88724a8fa7d3";
        System.out.println(uuid);
        UUIDHelper.boundSymbol(uuid);
        System.out.println("f6e0e040-be3d-4573-964c-88724a8fa7d3");
    }
}
