package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.io.*;

public class BitmapHeader {
    int nsize;
    int nbisize;
    int nwidth;
    int nheight;
    int nplanes;
    int nbitcount;
    int ncompression;
    int nsizeimage;
    int nxpm;
    int nypm;
    int nclrused;
    int nclrimp;

    public void read_the_bitmap_header(byte[] inputdata, int bflen, int bilen) throws IOException {
        byte bf[] = new byte[bflen];
        for (int p = 0; p <= bflen - 1; p++) {
            bf[p] = inputdata[p];
        }
        byte bi[] = new byte[bilen];
        for (int p = 0; p <= bilen - 1; p++) {
            bi[p] = inputdata[p + bflen];
        }
        nsize = build_an_int_from_a_byte_array_convert_little_to_big_endian(bf, 2);
        nbisize = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 2);
        nwidth = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 4);
        nheight = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 8);
        nplanes = build_an_short_from_a_byte_array_convert_little_to_big_endian(bi, 12);
        nbitcount = build_an_short_from_a_byte_array_convert_little_to_big_endian(bi, 14);
        ncompression = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 16);
        nsizeimage = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 20);
        nxpm = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 24);
        nypm = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 28);
        nclrused = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 32);
        nclrimp = build_an_int_from_a_byte_array_convert_little_to_big_endian(bi, 36);
    }

    public static int build_an_int_from_a_byte_array_convert_little_to_big_endian(byte[] in, int offset) {
        int ret = (in[offset + 3] & 0xff);
        ret = (ret << 8) | (in[offset + 2] & 0xff);
        ret = (ret << 8) | (in[offset + 1] & 0xff);
        ret = (ret << 8) | (in[offset + 0] & 0xff);
        return (ret);
    }

    public int build_an_int_from_a_byte_array_convert_little_to_big_endian_and_set_high_order_bytes_to_0xfff(byte[] in,
                                                                                                             int offset) {
        int ret = 0xff;
        ret = (ret << 8) | (in[offset + 2] & 0xff);
        ret = (ret << 8) | (in[offset + 1] & 0xff);
        ret = (ret << 8) | (in[offset + 0] & 0xff);
        return (ret);
    }

    public static long build_an_int_from_a_byte_array_convert_little_to_big_endian2(byte[] in, int offset) {
        long ret = ((long) in[offset + 7] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 6] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 5] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 4] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 3] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 2] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 1] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 0] & 0xff);
        return (ret);
    }

    public static double constructDouble(byte[] in, int offset) {
        long ret = build_an_int_from_a_byte_array_convert_little_to_big_endian2(in, offset);
        return (Double.longBitsToDouble(ret));
    }

    public static short build_an_short_from_a_byte_array_convert_little_to_big_endian(byte[] in, int offset) {
        short ret = (short) (in[offset + 1] & 0xff);
        ret = (short) ((ret << 8) | (short) (in[offset + 0] & 0xff));
        return (ret);
    }
}
