import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class A {
	public static void main1(String[] args) throws InterruptedException, IOException {
		File fieldFile = new File("D:\\log\\A.txt");
		File typesFile = new File("D:\\log\\B.txt");
		List<String> lines1 = FileUtils.readLines(fieldFile, "UTF-8");
		List<String> lines2 = FileUtils.readLines(typesFile, "UTF-8");
		for (int i = 0; i < lines1.size(); i++) {
			System.out.print("\"" + lines1.get(i) + ":" + (lines2.get(i).equalsIgnoreCase("string") ? "s"
					: lines2.get(i).equalsIgnoreCase("decimal") ? "n" : "d") + "\",");
		}
	}

	public static void main2(String[] args) throws IOException {
		List<String> lines = FileUtils.readLines(new File("D:\\log\\D.txt"), "UTF-8");
		lines.forEach(l -> {
			String[] arr = l.split("\t");
			System.out.print("private static final String INTERFACE_METHOD_" + arr[0].toUpperCase() + "=\"" + arr[0]
					+ "\";\t//����ֵ����");
			System.out.print(arr[1] + ",˵��:");
			System.out.println(arr[2]);
		});
	}

	public static void main3(String[] args) {
		String str = "readertype=~:s";
		String[] split = str.split(":");
		System.out.println(split[0]);
		String[] split2 = split[0].split("=");
		for (int i = 0; i < split2.length; i++) {
			System.out.println(i + "..." + split2[i]);
		}
		System.out.println(split[1]);
	}

	public static void main4(String[] args) throws IOException {
		File file = new File("F:\\zhongkang\\yyxm.txt");
		List<String> lines = FileUtils.readLines(file, "GBK");
		String str = "yyxmbm(ҽԺ��Ŀ����),yyxmmc(ҽԺ��Ŀ����),zfbl(�Ը�����),sm(˵��),ylxmbm,gg(���),dw(��λ),ckj(�ο���),jxm(������),scqy(������ҵ),cfybz(�Ƿ񴦷�ҩ),gmpbz(�Ƿ�GMP),zxgg(���װ���),bzsl(���װ��С��װ����),gxsj,xj,spbz";
		String[] splitTitle = str.split(",");
		for (int i = 0; i < lines.size(); i++) {
			String l = lines.get(i);
			String[] split = l.split("\t");
			if (split.length == 17) {
				for (int j = 0; j < split.length; j++) {
					System.out.print(splitTitle[j] + "=" + split[j] + ",");
				}
				System.out.println();
			}
		}
	}

	public static void main5(String[] args) {
		String[] split = System.getProperty("java.library.path").split(";");
		for (String string : split) {
			System.out.println(string);
		}
	}

	public static void main6(String[] args) {
		String str = "yyxmbm(ҽԺ��Ŀ����),yyxmmc(ҽԺ��Ŀ����),zfbl(�Ը�����),sm(˵��),ylxmbm(ҽ����Ŀ����),gg(���),dw(��λ),ckj(�ο���),jxm(������),scqy(������ҵ),cfybz(�Ƿ񴦷�ҩ),gmpbz(�Ƿ�GMP),zxgg(���װ���),bzsl(���װ��С��װ����),gxsj(����ʱ��),xj,spbz";
		String[] split = str.split(",");
		String content = "1553-41-01	������	0	-qx-	2383			0	59	���ݻ���ҩ��	1	1		1.00000000	20130206 15:01:07	0	1";
		String[] split2 = content.split("\t");
		for (int i = 0; i < split.length; i++) {
			System.out.print(split[i] + "=" + split2[i]);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		String str = "yyxmbm(ҽԺ��Ŀ����),yyxmmc(ҽԺ��Ŀ����),zfbl(�Ը�����),sm(˵��),ylxmbm(ҽ����Ŀ����),gg(���),dw(��λ),ckj(�ο���),jxm(������),scqy(������ҵ),cfybz(�Ƿ񴦷�ҩ),gmpbz(GMP��־),zxgg(��С���),bzsl(���װ��С��װ����),gxsj(����ʱ��),xj(),spbz(��Ʒ��־)";
		String[] split = str.split(",");
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
	}

}
