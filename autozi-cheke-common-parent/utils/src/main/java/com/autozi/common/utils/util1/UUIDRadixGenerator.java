package com.autozi.common.utils.util1;

/**
 * Created by IntelliJ IDEA.
 * User: bbngai
 * Date: Jul 24, 2009
 * Time: 11:50:18 AM
 * <p/>
 * Copyright TIBCO Software Inc. 2004-2006. All rights reserved.
 * TIBCO Software Inc. proprietary information
 * TIBCO Software Inc. confidential
 *
 * A <tt>UUIDGenerator</tt> that returns a string of length 28:
 *
 * Format: XXXXXX-XXXXXX-XXX-XXXXXX-XXX
 * Digits: [0-9][A-Z] minus [D]avid [I]van [O]prah [V]incent
 *
 * Examples:
 *  TJETX5-JB8FFJ-43J-T3LMNJ-000
 *  TJETX5-JB8FFJ-49Z-T3LMUZ-001
 *  TJETX5-JB8FFJ-4H8-T3LN28-002
 *
 */
public class UUIDRadixGenerator extends UUIDGenerator
{
    private StringBuilder uuid = new StringBuilder(28);

    public static String newUUID() {
        return new UUIDRadixGenerator().generate();
    }

    protected UUIDRadixGenerator() {}

    protected UUIDRadixGenerator append(int intval) {
        uuid.append(shift(intval), 1, 6);
        return this;
    }

    protected UUIDRadixGenerator append(short shortval) {
        uuid.append(shift(shortval), 4, 3);
        return this;
    }

    protected UUIDRadixGenerator append(char c) {
        uuid.append(c);
        return this;
    }

    // Removed [D]avid [I]van [O]prah [V]incent - with radix of 32 digits
    final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' ,
        '8' , '9' , 'A' , 'B' , 'C' , 'E' , 'F' , 'G' ,
        'H' , 'J' , 'K' , 'L' , 'M' , 'N' , 'P' , 'Q' ,
        'R' , 'S' , 'T' , 'U' , 'W' , 'X' , 'Y' , 'Z'
    };

    private static char[] shift(int i) {
        char[] buf = new char[] { '0', '0', '0', '0', '0', '0', '0' };
        int charPos = 7;
        int radix = 1 << 5;
        int mask = radix - 1;
        do {
            buf[--charPos] = digits[i & mask];
            i >>>= 5;
        } while (i != 0);

        return buf;
    }

    protected short getHiTime() {
        return (short) (System.currentTimeMillis() % 10000);
    }

    public String generate() {
        return this
                .append(getIP()).append('-')
                .append(getJVM()).append('-')
                .append(getHiTime()).append('-')
                .append(getLoTime()).append('-')
                .append(getCount())
                .toString();
    }

    public String toString() {
        return uuid.toString();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; ++i) {
            Thread.sleep(200);
            System.out.println("ID: " + newUUID());
        }
    }
}
