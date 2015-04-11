package com.andframe.util;

import java.io.InputStream;

import com.andframe.helper.android.AfGifHelper;
import com.andframe.helper.android.AfGifHelper.GifFrame;

public class GifUtil
{
    /**
     * ����GIFͼƬ
     * 
     * @param is
     * @return
     */
    public static GifFrame[] getGif(InputStream is) {
        AfGifHelper gifHelper = new AfGifHelper();
        if (AfGifHelper.STATUS_OK == gifHelper.read(is)) {
            return gifHelper.getFrames();
        }
        return null;
    }
    /**
     * �ж�ͼƬ�Ƿ�ΪGIF��ʽ
     * @param is
     * @return
     */
    public static boolean isGif(InputStream is) {
        AfGifHelper gifHelper = new AfGifHelper();
        return gifHelper.isGif(is);
    }

}
