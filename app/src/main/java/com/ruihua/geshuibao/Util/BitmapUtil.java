package com.ruihua.geshuibao.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.text.TextUtils;
import android.graphics.BitmapFactory.Options;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * Bitmap工具类，
 */
public class BitmapUtil {

    private BitmapUtil(){}

    /**
     * 根据资源id获取指定大小的Bitmap对象
     * @param context   应用程序上下文
     * @param id        资源id
     * @param height    高度
     * @param width     宽度
     * @return
     */
    public static Bitmap getBitmapFromResource(Context context, int id, int height, int width){
        Options options = new Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeResource(context.getResources(), id, options);
        options.inSampleSize = calculateSampleSize(height, width, options);
        options.inJustDecodeBounds = false;//加载到内存中
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
        return bitmap;
    }

    /**
     * 根据文件路径获取指定大小的Bitmap对象
     * @param path      文件路径
     * @param height    高度
     * @param width     宽度
     * @return
     */
    public static Bitmap getBitmapFromFile(String path, int height, int width){
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("参数为空，请检查你选择的路径:" + path);
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateSampleSize(height, width, options);
        options.inJustDecodeBounds = false;//加载到内存中
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
    /**
     * 根据文件路径获取Bitmap对象
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    /**
     * 获取指定大小的Bitmap对象
     * @param bitmap    Bitmap对象
     * @param height    高度
     * @param width     宽度
     * @return
     */
    public static Bitmap getThumbnailsBitmap(Bitmap bitmap, int height, int width){
        if (bitmap == null) {
            throw new IllegalArgumentException("图片为空，请检查你的参数");
        }
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    /**
     * 将Bitmap对象转换成Drawable对象
     * @param context   应用程序上下文
     * @param bitmap    Bitmap对象
     * @return  返回转换后的Drawable对象
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap){
        if (context == null || bitmap == null) {
            throw new IllegalArgumentException("参数不合法，请检查你的参数");
        }
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    /**
     * 将Drawable对象转换成Bitmap对象
     * @param drawable  Drawable对象
     * @return  返回转换后的Bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable为空，请检查你的参数");
        }
        Bitmap bitmap =
                Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Bitmap对象转换为byte[]数组
     * @param bitmap    Bitmap对象
     * @return      返回转换后的数组
     */
    public static byte[] bitmapToByte(Bitmap bitmap){
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap为空，请检查你的参数");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 计算所需图片的缩放比例
     * @param height    高度
     * @param width     宽度
     * @param options   options选项
     * @return
     */
    private static int calculateSampleSize(int height, int width, Options options){
        int realHeight = options.outHeight;
        int realWidth = options.outWidth;
        int heigthScale = realHeight / height;
        int widthScale = realWidth / width;
        if(widthScale > heigthScale){
            return widthScale;
        }else{
            return heigthScale;
        }
    }
}
