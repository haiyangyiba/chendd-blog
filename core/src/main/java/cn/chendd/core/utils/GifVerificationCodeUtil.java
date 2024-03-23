package cn.chendd.core.utils;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

/**
 * Gif图片处理工具类
 *
 * @author chendd
 * @date 2021/12/27 15:37
 */
public class GifVerificationCodeUtil {

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
        int width = 400, height = 200;
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
        /// 画边框
        /*g.setColor(new Color());
        g.drawRect(0,0,width-1,height-1);*/
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(250);
            int yl = random.nextInt(250);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 设定字体
        g.setColor(Color.RED);
        g.setFont(new Font("Consolas", Font.BOLD, 20));

        g.drawString("https://www.chendd.cn" , 80, 180);
        // 设定字体
        g.setFont(new Font("Consolas", Font.BOLD, 100));
        // 取随机产生的认证码(4位数字)
        for (int i = 0; i < randCodes.length; i++) {
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            int a = new Random().nextInt(6);
            g.rotate(a * Math.PI / 180);
            g.translate(0, -a);
            ///当前帧的图片所在位置的字符显示为“*”
            /*if (i == index) {
                g.drawString("？", 100 * i + 20, 100);
                continue;
            }*/
            g.drawString(String.valueOf(randCodes[i]), 100 * i + 20, 100);
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
