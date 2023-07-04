package cn.chendd.core.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <pre>
 * 描述：图片处理工具类，底层实现类：Thumbnails，如有更多实现需求，可直接采用底层类实现；
 * </pre>
 *
 * @author chendd
 * @date 2020/8/15 22:15
 */
public class ImageUtil {

    /**
     * 图片最小宽度，超过此宽度将修改水印文字大小
     */
    private static final Integer WATERMARK_MIN_WIDTH = 200;
    /**
     * 图片最小高度，超过此高度将实现多
     */
    private static final Integer WATERMARK_MIN_HEIGHT = 800;

    /**
     * 等比缩放图片至指定大小
     *
     * @param srcImageFile  原图
     * @param destImageFile 新图
     * @param width         宽度
     * @param height        高度
     * @param keepRatio     是否等比
     */
    public static void zoom(File srcImageFile, File destImageFile, int width, int height, boolean keepRatio) {

        try {
            Thumbnails.of(srcImageFile).size(width, height).keepAspectRatio(keepRatio).toFile(destImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 缩放图片，非等比缩放
     *
     * @param srcImageFile  原图
     * @param destImageFile 新图
     * @param width         宽度
     * @param height        高度
     */
    public static void zoom(File srcImageFile, File destImageFile, int width, int height) {

        zoom(srcImageFile, destImageFile, width, height, false);
    }

    /**
     * 等比缩放图片至指定大小
     *
     * @param srcImageFile 原图
     * @param width        宽度
     * @param height       高度
     * @param keepRatio    是否等比
     * @param out          输出流
     */
    public static void zoom(File srcImageFile, int width, int height, boolean keepRatio, OutputStream out) {
        try {
            Thumbnails.of(srcImageFile).size(width, height).keepAspectRatio(keepRatio).toOutputStream(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将图片进行等比例缩放，0~~1之间的为缩小，大于1的为放大
     *
     * @param srcImageFile 原图
     * @param outImageFile 新图
     * @param scale        比例
     */
    public static void zoom(File srcImageFile, File outImageFile, double scale) {
        try {
            Thumbnails.of(srcImageFile).scale(scale).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将图片进行等比例缩放，0~~1之间的为缩小，大于1的为放大
     *
     * @param srcImageFile 原图
     * @param out          新图
     * @param scale        比例
     */
    public static void zoom(File srcImageFile, OutputStream out, double scale) {
        try {
            Thumbnails.of(srcImageFile).scale(scale).toOutputStream(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按角度旋转
     *
     * @param srcImageFile 原图
     * @param outImageFile 新图
     * @param rotate       比例
     */
    public static void rotate(File srcImageFile, File outImageFile, double rotate) {

        try {
            Thumbnails.of(srcImageFile).scale(1).rotate(rotate).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按坐标位置裁切图片，先按位置获取指定的图片范围，再按指定宽高存储
     *
     * @param srcImageFile 原图
     * @param outImageFile 新图
     * @param postition    坐标
     * @param width        宽度
     * @param height       高度
     * @param x            新的宽度
     * @param y            新的高度
     */
    public static void corp(File srcImageFile, File outImageFile, Positions postition, int width, int height, int x, int y) {
        try {
            Thumbnails.of(srcImageFile).sourceRegion(postition, x, y).size(width, height).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按坐标位置裁切图片，先按位置获取指定的图片范围，再按指定宽高存储
     * x1，x2为左上角的坐标点
     * y1，y2为以左上角为0,0坐标位置的相对坐标（或者x1+要裁切图片宽度=y1,y1+要裁切图片高度=y2的坐标）
     *
     * @param srcImageFile 原图
     * @param outImageFile 新图
     * @param width        宽度
     * @param height       高度
     * @param x1           左上角的横坐标
     * @param y1           左上角的纵坐标
     * @param x2           右下角的横坐标
     * @param y2           右下角的纵坐标
     */
    public static void corpRegion(File srcImageFile, File outImageFile, int width, int height, int x1, int y1, int x2, int y2) {
        try {
            Thumbnails.of(srcImageFile).sourceRegion(x1, y1, x2, y2).size(width, height).keepAspectRatio(false).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图片水印
     *
     * @param srcImageFile         原图
     * @param srcImageWatemarkFile 水印图片
     * @param outImageFile         新图
     * @param position             坐标
     * @param opacity              透明度
     */
    public static void watermark(File srcImageFile, File srcImageWatemarkFile, File outImageFile, Position position, float opacity) {
        try {
            Thumbnails.of(srcImageFile).scale(1).watermark(position, ImageIO.read(srcImageWatemarkFile), opacity).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加文本水印
     *
     * @param srcImageFile 原图
     * @param outImageFile 新图
     * @param text         文本
     * @param opacity      透明度
     */
    public static void watermark(File srcImageFile, File outImageFile, Font font, Color color, String text , TextPositions position , float opacity) {

        try {
            BufferedImage image = ImageIO.read(srcImageFile);
            int imageWidth = image.getWidth();
            if (imageWidth < WATERMARK_MIN_WIDTH) {
                font = new Font(font.getFamily() , font.getStyle() , 8);
            }
            int imageHeight = image.getHeight();
            if (imageHeight > WATERMARK_MIN_HEIGHT) {
                watermarkLongImage(srcImageFile , outImageFile , text , font , color);
                return;
            }
            BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            //设置绘图区域透明
            bi = g.getDeviceConfiguration().createCompatibleImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
            g.dispose();
            //字体、字体大小，透明度，旋转角度
            g = bi.createGraphics();
            g.setFont(font);
            g.setColor(color);
            //设置文本显示坐标，以上述中的300x300位置为0,0点
            //根据字体和文本获取宽度
            //x = 0 表示为左上下角
            FontMetrics fm = g.getFontMetrics(font);
            int textWidth = fm.stringWidth(text);
            Point point = position.getPosition(imageWidth , imageHeight , textWidth , font.getSize());
            g.drawString(text, point.x, point.y);
            Thumbnails.of(srcImageFile).scale(1).watermark(bi, opacity).toFile(outImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void watermarkLongImage(File srcImageFile, File destImageFile, String text, Font font, Color color) {

        try {
            //从原图中找出300x300的大小来显示水印文本
            BufferedImage image = ImageIO.read(srcImageFile);
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            //设置绘图区域透明
            bi = g.getDeviceConfiguration().createCompatibleImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
            g.dispose();
            //字体、字体大小，透明度，旋转角度
            g = bi.createGraphics();
            g.setFont(font);
            char[] data = text.toCharArray();
            g.setColor(color);
            //设置文本显示坐标，以上述中的300x300位置为0,0点
            g.drawChars(data, 0, data.length, 10, 20);
            //假设此图在手机端访问，手机的高度为400，则每超过400部分显示一个水印
            Thumbnails.Builder<File> builder = Thumbnails.of(srcImageFile).scale(1);
            int mod = (int) Math.ceil(imageHeight / 400.0);
            FontMetrics fm = g.getFontMetrics(font);
            int width = fm.stringWidth(text);
            int x = 5;
            if(imageWidth > width) {
                x = (imageWidth - width) / 2;
            }
            for (int i = 1; i < mod; i++) {
                int y = (i) * 400;
                builder.watermark(new Coordinate(x, y), bi, 1);
            }
            builder.toFile(destImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static void outputFormatType(File imageFile) {
        String fileName = imageFile.getName();
        //默认的格式类型图片
        String suffix = "jpg";
        if(! StringUtils.endsWithIgnoreCase(fileName , suffix)) {
            try {
                String name = FilenameUtils.getBaseName(fileName);
                String tempFileName = name + "_temp." + suffix;
                File tempFile = new File(imageFile.getParent() , tempFileName);
                BufferedImage image = ImageIO.read(imageFile);
                Thumbnails.of(imageFile).size(image.getWidth() , image.getHeight()).outputFormat(suffix).toFile(tempFile);
                //删除本图片文件
                imageFile.delete();
                //重命名新图片文件
                File resultFile = new File(tempFile.getParent() , name + "." + suffix);
                if(resultFile.exists()) {
                    resultFile.delete();
                }
                tempFile.renameTo(resultFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public enum TextPositions {

        /**
         * 左上角
         */
        TOP_LEFT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                //左上角为0,0，预留左侧 5 个像素间距
                return new Point(5 , fontHeight);
            }

        },
        TOP_CENTER {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                //左上角为0,0，预留左侧 5 个像素间距
                if (imageWidth < fontWidth) {
                    return TOP_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                }
                int center = (imageWidth - fontWidth) / 2;
                return new Point(center , fontHeight);
            }

        },
        TOP_RIGHT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                if (imageWidth < fontWidth) {
                    return TOP_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                }
                //距离右上角右侧 5 个像素间距
                int right = imageWidth - fontWidth - 5;
                return new Point(right , fontHeight);
            }

        },

        CENTER_LEFT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                //高度的中线位置处还需要加上字体高度的一半，才是最终文本显示的水平顶部位置
                int center = (imageHeight - fontHeight) / 2 + fontHeight / 2;
                Point left = TOP_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                //距离左侧 5 个像素间距
                return new Point(left.x , center);
            }

        },
        CENTER {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                Point topCenter = TOP_CENTER.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                Point middleCenter = CENTER_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                return new Point(topCenter.x , middleCenter.y);
            }

        },
        CENTER_RIGHT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                Point rightCenter = TOP_RIGHT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                Point middleCenter = CENTER_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                return new Point(rightCenter.x , middleCenter.y);
            }

        },

        BOTTOM_LEFT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                Point left = TOP_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                int bottom = imageHeight - 5;
                return new Point(left.x , bottom);
            }

        },
        BOTTOM_CENTER {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                Point topCenter = TOP_CENTER.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                Point bottomCenter = BOTTOM_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                return new Point(topCenter.x , bottomCenter.y);
            }

        },
        BOTTOM_RIGHT {

            @Override
            public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
                Point topRight = TOP_RIGHT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                Point bottomRight = BOTTOM_LEFT.getPosition(imageWidth , imageHeight , fontWidth , fontHeight);
                return new Point(topRight.x , bottomRight.y);
            }

        };

        public Point getPosition(int imageWidth , int imageHeight , int fontWidth , int fontHeight) {
            return null;
        }
    }

}
