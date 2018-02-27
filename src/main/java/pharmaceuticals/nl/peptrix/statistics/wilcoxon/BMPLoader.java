package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.awt.image.*;
import java.awt.*;
import java.io.*;

public class BMPLoader {
    public static Image read(byte[] inputdata) {
        try {
            int byte_14_BITMAPFILEHEADER = 14;
            int byte_40_BITMAPINFOHEADER = 40;
            BitmapHeader bh = new BitmapHeader();
            bh.read_the_bitmap_header(inputdata, byte_14_BITMAPFILEHEADER, byte_40_BITMAPINFOHEADER);
            if (bh.nbitcount == 8) {
                return (readMap8_internal_routine_to_read_the_bytes_in_a_8_bit_bitmap(bh, byte_14_BITMAPFILEHEADER,
                        byte_40_BITMAPINFOHEADER, inputdata));
            }
        } catch (IOException e) {
        }
        return (null);
    }

    protected static Image readMap8_internal_routine_to_read_the_bytes_in_a_8_bit_bitmap(BitmapHeader bh, int bflen,
                                                                                         int bilen, byte[] inputdata) throws IOException {
        Image image;
        int nNumColors = 0;
        if (bh.nclrused > 0) {
            nNumColors = bh.nclrused;
        } else {
            nNumColors = (1 & 0xff) << bh.nbitcount;
        }
        if (bh.nsizeimage == 0) {
            bh.nsizeimage = ((((bh.nwidth * bh.nbitcount) + 31) & ~31) >> 3);
            bh.nsizeimage *= bh.nheight;
        }
        int npalette[] = new int[nNumColors];
        byte bpalette[] = new byte[nNumColors * 4];
        int total_length = bflen + bilen;
        int bpalette_length = nNumColors * 4;
        for (int p = 0; p <= bpalette_length - 1; p++) {
            bpalette[p] = inputdata[total_length + p];
        }
        total_length = total_length + bpalette_length;
        int nindex8 = 0;
        for (int n = 0; n < nNumColors; n++) {
            npalette[n] = bh
                    .build_an_int_from_a_byte_array_convert_little_to_big_endian_and_set_high_order_bytes_to_0xfff(
                            bpalette, nindex8);
            nindex8 += 4;
        }
        int npad8 = (bh.nsizeimage / bh.nheight) - bh.nwidth;
        int ndata8[] = new int[bh.nwidth * bh.nheight];
        byte bdata[] = new byte[(bh.nwidth + npad8) * bh.nheight];
        int bh_nwidth_npad8_x_bh_nheight = (bh.nwidth + npad8) * bh.nheight;
        for (int p = 0; p <= bh_nwidth_npad8_x_bh_nheight - 1; p++) {
            bdata[p] = inputdata[total_length + p];
        }
        nindex8 = 0;
        for (int j8 = 0; j8 < bh.nheight; j8++) {
            for (int i8 = 0; i8 < bh.nwidth; i8++) {
                ndata8[bh.nwidth * (bh.nheight - j8 - 1) + i8] = npalette[(bdata[nindex8] & 0xff)];
                nindex8++;
            }
            nindex8 += npad8;
        }
        image = Toolkit.getDefaultToolkit()
                .createImage(new MemoryImageSource(bh.nwidth, bh.nheight, ndata8, 0, bh.nwidth));
        return (image);
    }
}
