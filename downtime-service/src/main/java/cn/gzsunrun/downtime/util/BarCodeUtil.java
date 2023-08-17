package cn.gzsunrun.downtime.util;

/**
 * @author john saunders
 * @description:
 * @date: 2023/5/24
 * @time: 10:31
 */
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 一维码生成
 * @author zlxls
 * @date 2020-03-26
 **/
public class BarCodeUtil {

    private static final String CHARSET = "utf-8";
    /** 条形码宽度 */
    private static final int MARGIN_LEFT = 10;

    /** 条形码高度 */
    private static final int HEIGHT = 100;

    /** 加文字 条形码 */
    private static final int WORDHEIGHT = 240;

    private static final String FORMAT_NAME = "PNG";
    /**
     * 生成图片
     * @param content 内容
     * @return
     * @throws Exception
     */
    public static String encode(String content) throws Exception {
        String imgName = UUID.randomUUID()+".png";
        // 生成的二维码的路径
        String imgPath = "barcode/";
        if(BarCodeUtil.encode(content,imgPath, imgName)){
            return imgPath+imgName;
        }
        return "";
    }

    public static boolean setImage(BufferedImage img,String path) throws Exception{
        //BufferedImage img = removeBackgroud(file);//去除重影

//bufferedimage 转换成 inputstream
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(img, FORMAT_NAME, imOut);
        InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());

        OutputStream outStream = new FileOutputStream(path);
        IOUtils.copy(inputStream, outStream);
        inputStream.close();
        outStream.close();
        return  true;
    }

    /**
     * 生成图片
     * @param content 内容
     * @param imgPath 图片路径
     * @param imgName 图片名称
     * @return
     * @throws Exception
     */
    public static boolean encode(String content, String imgPath, String imgName) throws Exception {
        BufferedImage image = insertWords(getBarCode(content), content);
        FileUtil.mkdir(imgPath);

        //return setImage(image,imgPath+imgName);
        return ImageIO.write(image, FORMAT_NAME, new File(imgPath+imgName));
    }
    /**
     * 生成图片
     * @param content 内容
     * @param imgPath 图片路径
     * @param imgName 图片名称
     * @param word 底部文字
     * @return
     * @throws Exception
     */
    public static boolean encode(String content, String imgPath, String imgName,String word) throws Exception {
        BufferedImage image = insertWords(getBarCode(content), word);
        FileUtil.mkdir(imgPath);
        return ImageIO.write(image, FORMAT_NAME, FileUtil.file(imgPath+imgName));
    }
    /**
     * 生成code128条形码
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param message       要生成的文本
     * @param withQuietZone 是否两边留白
     * @param hideText      隐藏可读文本
     * @return 图片对应的字节码
     */
    public static byte[] generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
        if (width != null) {
            bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生产条形码
        bean.generateBarcode(canvas, message);
        try {
            canvas.finish();
        } catch (IOException e) {
        }

        return ous.toByteArray();
    }
    /**
     * 设置 条形码参数
     */
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;
        {
            // 设置编码方式
            put(EncodeHintType.CHARACTER_SET,CHARSET);
            put(EncodeHintType.MARGIN,0);
        }
    };

    /**
     * 生成 图片缓冲
     * @author fxbin
     * @param vaNumber  VA 码
     * @return 返回BufferedImage
     */
    public static BufferedImage getBarCode(String vaNumber){
        if (StringUtils.isEmpty(vaNumber)){
            throw new RuntimeException("条形码为空");
        }
        Code128Writer writer = new Code128Writer();
        //为了无边距，需设置宽度为条码自动生成规则的宽度
        int width = writer.encode(vaNumber).length;
        System.out.println(width);
        //条码放大倍数
        int codeMultiples = 2;
        //获取条码内容的宽，不含两边距，当EncodeHintType.MARGIN为0时即为条码宽度
        int codeWidth = width * codeMultiples;
        try {
            // 编码内容, 编码类型, 宽度, 高度, 设置参数
            BitMatrix bitMatrix = writer.encode(vaNumber, BarcodeFormat.CODE_128, codeWidth, HEIGHT, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把带logo的条形码下面加上文字
     * @author fxbin
     * @param image  条形码图片
     * @param words  文字
     * @return 返回BufferedImage
     */
    public static BufferedImage insertWords(BufferedImage image, String words){
        // 新的图片，把带logo的二维码下面加上文字
        if (StringUtils.isNotEmpty(words)) {

            //int width = image.getWidth()+MARGIN_LEFT*2;
            int width = 400;
            BufferedImage outImage = new BufferedImage(400, WORDHEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outImage.createGraphics();
            g2d.setBackground(Color.WHITE);
            // 抗锯齿
            setGraphics2D(g2d);
            // 设置白色
            setColorWhite(g2d);

            // 画条形码到新的面板

            g2d.drawImage(image, (width-image.getWidth())/2, 55, image.getWidth(), image.getHeight(), null);
            // 画文字到新的面板
            Color color=new Color(0, 0, 0);
            g2d.setColor(color);
            // 字体、字型、字号
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 24));
            //文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(words);
            //总长度减去文字长度的一半  （居中显示）
            int wordStartX=(width - strWidth) / 2;
            //height + (outImage.getHeight() - height) / 2 + 12
            int wordStartY=HEIGHT+75;

            // 画文字
            g2d.drawString(words, wordStartX, wordStartY);
            g2d.dispose();
            outImage.flush();
            return outImage;
        }else {
            throw new RuntimeException("请输入文字");
        }
    }

    /**
     * 设置 Graphics2D 属性  （抗锯齿）
     * @param g2d  Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setGraphics2D(Graphics2D g2d){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(s);
    }

    /**
     * 设置背景为白色
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setColorWhite(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        //填充整个屏幕
        g2d.fillRect(0,0,600,600);
        //设置笔刷
        g2d.setColor(Color.BLACK);
    }


    public final static Integer DEFAULT_COUNTRY = 2;
    public final static Integer DEFAULT_COMPANY = 1028;

    public static String genearteBarCode(){
    return generateBarcode(DEFAULT_COUNTRY, DEFAULT_COMPANY, new ArrayList<>());
    }

    /**
     * 生成随机的barcode
     * @param country
     * @param company
     * @return
     */
    public static String generateBarcode(Integer country, Integer company, List<String> usedCode){
        String randomNum = getRandomNum(5);
        String countrys = country.toString();
        if (countrys.length()<3){
            countrys = countrys+getRandomNum(3-countrys.length());
        }
        String comanys = company.toString();
        String res = countrys + comanys + randomNum + getChecksum(countrys + comanys  + randomNum + 0);
        if (CollectionUtil.isNotEmpty(usedCode)){
            while(usedCode.contains(res)){
                String newRan = getRandomNum(5);
                //找到不存在为止
                res = countrys + comanys + newRan + getChecksum(countrys + comanys + newRan + 0);
            }
        }
        return res;
    }

    public static String getRandomNum(int len) {
                 int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
                 return String.valueOf(rs);
    }
    /**
     * 获取条码校验值
     * 13位条码 code 示例：6901234567892
     * 调用方法 int 值 = getChecksum(code);
     * @return checksum
     */
    public static Integer getChecksum(String code){
//                 char a= code.charAt(0);//取字符串中某一个字符
//                 int numa = Integer.parseInt(String.valueOf(a));//char转换为int
        int checksum;
        //校验步骤a
//也可以遍历判断获取偶数位的值的和
        int checkA =  Integer.parseInt(String.valueOf(code.charAt(1)))+Integer.parseInt(String.valueOf(code.charAt(3)))+
                Integer.parseInt(String.valueOf(code.charAt(5)))+Integer.parseInt(String.valueOf(code.charAt(7)))+
                Integer.parseInt(String.valueOf(code.charAt(9)))+Integer.parseInt(String.valueOf(code.charAt(11)));
        //校验步骤b
        int checkB = checkA * 3;
        //校验步骤c
//也可以遍历判断获取奇数位的值的和
        int checkC =  Integer.parseInt(String.valueOf(code.charAt(0)))+Integer.parseInt(String.valueOf(code.charAt(2)))+
                Integer.parseInt(String.valueOf(code.charAt(4)))+Integer.parseInt(String.valueOf(code.charAt(6)))+
                Integer.parseInt(String.valueOf(code.charAt(8)))+Integer.parseInt(String.valueOf(code.charAt(10)));
        //校验步骤d
        int checkD =checkB+checkC;
        //校验步骤e
        if(checkD % 10 == 0){
            checksum = 0;
        }else{
            checksum = 10 - checkD % 10;
        }
        return checksum;
    }

}
