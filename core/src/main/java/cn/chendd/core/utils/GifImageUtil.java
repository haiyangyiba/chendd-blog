package cn.chendd.core.utils;

import com.gif4j.*;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Gif图片处理工具类
 *
 * @author chendd
 * @date 2021/12/27 15:37
 */
public class GifImageUtil {

    /**
     * 动图中加文字水印
     *
     * @param src      原图片
     * @param dest     输出图片
     * @param font     字体
     * @param color    颜色
     * @param text     水印文本
     * @param position 水印位置
     * @param opacity  水印透明度
     */
    public static void watermarkGif(File src, File dest, Font font, Color color, String text, ImageUtil.TextPositions position, float opacity) {
        try {
            //水印初始化、设置（字体、样式、大小、颜色）
            TextPainter textPainter = new TextPainter(font);
            textPainter.setOutlinePaint(Color.WHITE);
            BufferedImage renderedWatermarkText = textPainter.renderString(text, true);
            //图片对象
            GifImage gf = GifDecoder.decode(src);
            //获取图片大小
            int imageWidth = gf.getScreenWidth();
            int imageHeight = gf.getScreenHeight();
            //获取水印大小
            int fontWidth = renderedWatermarkText.getWidth();
            int fontHeight = renderedWatermarkText.getHeight();
            //加水印
            Watermark watermark = new Watermark(renderedWatermarkText, position.getPosition(imageWidth, imageHeight, fontWidth, fontHeight));
            gf = watermark.apply(GifDecoder.decode(src), true);
            //输出
            GifEncoder.encode(gf, dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图片水印
     *
     * @param src      原图
     * @param watemark 水印图片
     * @param dest     新图
     * @param position 坐标
     * @param opacity  透明度
     */
    public static void watermarkGif(File src, File dest, File watemark, ImageUtil.TextPositions position, float opacity) {
        try {
            BufferedImage renderedWatermarkText = ImageIO.read(watemark);
            //图片对象
            GifImage gf = GifDecoder.decode(src);
            //获取图片大小
            int imageWidth = gf.getScreenWidth();
            int imageHeight = gf.getScreenHeight();
            //获取水印大小
            int watemarkImageWidth = renderedWatermarkText.getWidth();
            int watemarkImageHeight = renderedWatermarkText.getHeight();
            Watermark watermark = new Watermark(renderedWatermarkText, position.getPosition(imageWidth, imageHeight, watemarkImageWidth, watemarkImageHeight));
            //水印透明度
            watermark.setTransparency(opacity);
            gf = watermark.apply(GifDecoder.decode(src), false);
            //输出
            GifEncoder.encode(gf, dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建生成函数
     *
     * @param bos       参数类型为OutputStream 或 String filePath
     * @param randCodes 随机文本
     * @return gif图片
     */
    public static BufferedImage createVaildateCodeImage(OutputStream bos, char[] randCodes) {
        //按位数计算出N位长度的验证码图片所占用的宽和高，这里如果生成固定位数的长度时可以将宽和高写成常量值
        int codeLens = randCodes.length;
        int width = codeLens * 20 - codeLens * 20 / 10 - 5, height = 30;
        // 在内存中创建图象
        AnimatedGifEncoder gifFrame = new AnimatedGifEncoder();
        BufferedImage frame = null;
        //开始写入文件，需根据不同的参数类型来
        gifFrame.start(bos);
        //0重复，1不重复
        gifFrame.setRepeat(0);
        //设置图片直接的延时毫秒
        gifFrame.setDelay(1000);
        //生成GIF验证码的闪烁张数，帧
        for (int i = codeLens - 1; i >= 0; i--) {
            frame = graphicsImage(i, randCodes, width, height);
            gifFrame.addFrame(frame);
        }
        gifFrame.finish();
        // 如果是在servlet中使用则输出图象到页面，同时将code存储值session范围内，省略
        return frame;
    }

    /**
     * 在内存中生成一个图片
     */
    private static BufferedImage graphicsImage(int index, char[] randCodes, int width, int height) {
        Random random = new Random();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Consolas", Font.BOLD, 24));
        /// 画边框
        /*g.setColor(new Color());
        g.drawRect(0,0,width-1,height-1);*/
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 40; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        for (int i = 0; i < randCodes.length; i++) {
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            int a = new Random().nextInt(6);
            g.rotate(a * Math.PI / 180);
            g.translate(0, -a);
            if (i == index) {
                g.drawString(String.valueOf("  "), 13 * i + 6, 24);
                continue;
            }
            g.drawString(String.valueOf(randCodes[i]), 13 * i + 6, 24);
        }
        //画线
        g.rotate(new Random().nextInt(180) * Math.PI / 180);
        g.drawLine(0, 0, width, height);
        // 图象生效
        g.dispose();
        return image;
    }

    /**
     * 给定范围获得随机颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
