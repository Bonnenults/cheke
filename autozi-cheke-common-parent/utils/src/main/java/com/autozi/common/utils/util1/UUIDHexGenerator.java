package com.autozi.common.utils.util1;

/**
 * <b>uuid.hex</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32,
 * This string will consist of only hex digits. Optionally,
 * the string may be generated with seperators between each
 * component of the UUID.
 */
public class UUIDHexGenerator extends UUIDGenerator
{
    public final static String PROP_USE_DOUBLE_HEX = "bc.uuid.hex.generator.double";
    public static boolean USE_DOUBLE_HEX = Boolean.getBoolean(PROP_USE_DOUBLE_HEX);

    /**
     * Reset to this default only if system property PROP_USE_DOUBLE_HEX is not defined
     *
     * @param useDoubleHex - reset to this default
     */
    public static void resetDefault(boolean useDoubleHex) {
        USE_DOUBLE_HEX = Boolean.parseBoolean(System.getProperty(PROP_USE_DOUBLE_HEX, Boolean.toString(useDoubleHex)));
    }

    /**
     * Generate a Hex UUID.
     *
     * @return the UUID.
     */
    public static String newUUID()
    {
        return USE_DOUBLE_HEX ?
                new UUIDRadixGenerator().generate() :
                (String) new UUIDHexGenerator().generate();
    }

    /**
     *
     * @param intval
     * @return
     */
    protected String format(int intval)
    {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    /**
     *
     * @param shortval
     * @return
     */
    protected String format(short shortval)
    {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    /**
     *
     * @return
     */
    public java.io.Serializable generate()
    {
        return new StringBuffer(36)
                .append(format(getIP()))
                .append(format(getJVM()))
                .append(format(getHiTime()))
                .append(format(getLoTime()))
                .append(format(getCount()))
                .toString();
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        for (int i = 0; i < 10; ++i)
        {
            System.out.println("ID: " + newUUID());
        }
    }
}






