package com.sino.nm.ams.inv.storeman.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.validate.StrValidator;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class StrUtilHave {
	private StrUtilHave() {
	}

	/**
	 * 功能：计算某字符串包含另一字符串的个数。
	 * @param srcStr  原字符串；
	 * @param subStr  查找字符串；
	 * @param ignCase 是否忽略大小写；
	 * @return 返回包含数目，不包含则为0。
	 */
	public static int containNum(String srcStr, String subStr, boolean ignCase) {
		int containNum = 0;
		if (!isEmpty(srcStr) && !isEmpty(subStr)) {
			if (ignCase) {
				srcStr = srcStr.toLowerCase();
				subStr = subStr.toLowerCase();
			}
			int index = -1;
			while ((index = srcStr.indexOf(subStr)) != -1) {
				containNum++;
				srcStr = srcStr.substring(index + subStr.length());
			}
		}
		return containNum;
	}

	/**
	 * 功能：转换字符串为整数。
	 * @param str String
	 * @return int 成功则返回对应整数，否则返回-1。
	 */
	public static int strToInt(String str) {
		int retValue = -1;
		try {
			retValue = Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			Logger.logError(ex);
		}
		return retValue;
	}

	/**
	 * 功能：方法重载。计算某字符串含有另一字符串的个数，原样查找，不忽略大小写。
	 * @param srcStr 原字符串；
	 * @param subStr 查找字符串；
	 * @return 返回包含数目，不包含则为0。
	 */
	public static int containNum(String srcStr, String subStr) {
		return containNum(srcStr, subStr, false);
	}

	/**
	 * 功能：方法重载。判断某字符串是否含有另一字符串。
	 * 注意 不进行空字符串的判断，如查找空字符串，一律返回true。
	 * @param srcStr  原字符串；
	 * @param subStr  查找字符串；
	 * @param ignCase 是否忽略大小写；
	 * @return 返回true或false。
	 */
	public static boolean contains(String srcStr, String subStr, boolean ignCase) {
		boolean contains = false;
		if (isEmpty(subStr)) {
			contains = true;
		} else {
			contains = containNum(srcStr, subStr, ignCase) > 0;
		}
		return contains;
	}

	/**
	 * 功能：方法重载。判断某字符串是否含有另一字符串，原样查找，不忽略大小写。
	 * 注意 不进行空字符串的判断，如查找空字符串，一律返回false。
	 * @param srcStr 原字符串；
	 * @param subStr 查找字符串；
	 * @return 返回true或false。
	 */
	public static boolean contains(String srcStr, String subStr) {
		return contains(srcStr, subStr, false);
	}

	/**
	 * 功能：查找原字符串(subStr)在目标字符串(srcStr)中出现第几次(existTime)的索引，如不存在，则返回-1。
	 * @param srcStr    原字符串
	 * @param subStr    目标字符串
	 * @param existTime subStr在srcStr中出现的次数；
	 * @param ignCase   是否忽略大小写；
	 * @return 返回索引，不满足要求则返回-1。
	 */
	public static int findIndex(String srcStr, String subStr, int existTime,
								boolean ignCase) {
		int retIndex = -1;
		if (existTime > 0 &&
				containNum(srcStr, subStr, ignCase) >= existTime) {
			if (ignCase) {
				srcStr = srcStr.toLowerCase();
				subStr = subStr.toLowerCase();
			}
			int containNum = 0;
			StringBuffer tempStr = new StringBuffer();
			while ((retIndex = srcStr.indexOf(subStr)) != -1) {
				if (containNum == existTime) {
					break;
				}
				tempStr.append(srcStr.substring(0, retIndex + subStr.length()));
				srcStr = srcStr.substring(retIndex + subStr.length());
				containNum++;
			}
			retIndex = tempStr.length() - subStr.length();
		}
		return retIndex;
	}

	/**
	 * 功能：方法重载。查找原字符串(subStr)在目标字符串(srcStr)中某一次(existTime)出现的索引，如不存在，则返回-1。
	 * 注意 原样查找，不忽略大小写。
	 * @param srcStr    原字符串
	 * @param subStr    目标字符串
	 * @param existTime subStr在srcStr中第出现的次数；
	 * @return 返回索引，不满足要求则返回-1。
	 */
	public static int findIndex(String srcStr, String subStr, int existTime) {
		return findIndex(srcStr, subStr, existTime, false);
	}

	/**
	 * 功能：方法重载。查找原字符串(subStr)在目标字符串(srcStr)中第一次出现的索引，如不存在，则返回-1。
	 * @param srcStr  原字符串
	 * @param subStr  目标字符串
	 * @param ignCase 是否忽略大小写；
	 * @return 返回索引，不满足要求则返回-1。
	 */
	public static int findIndex(String srcStr, String subStr,
								boolean ignCase) {
		return findIndex(srcStr, subStr, 1, ignCase);
	}

	/**
	 * 功能：方法重载。查找原字符串(subStr)在目标字符串(srcStr)中第一次(existTime)出现的索引，如不存在，则返回-1。
	 * 注意 原样查找，不忽略大小写。
	 * @param srcStr 原字符串
	 * @param subStr 目标字符串
	 * @return 返回索引，不满足要求则返回-1。
	 */
	public static int findIndex(String srcStr, String subStr) {
		return findIndex(srcStr, subStr, 1, false);
	}

	/**
	 * 功能：将某一字符串分割成数组。
	 * @param srcStr   原字符串
	 * @param splitStr 分割字符串
	 * @return 返回分割后生成的数组或只含有原字符串一个元素的数组。
	 */
	public static String[] splitStr(String srcStr, String splitStr) {
		String[] returnArr = {srcStr};
		if (srcStr != null && splitStr != null && !srcStr.equals("")) {
			if (!splitStr.equals("")) {
				int strLength = srcStr.length();
				int index = -1;
				List strList = new ArrayList();
				String currStr = "";
				boolean isPrevNull = true;
				int splitCount = splitStr.length();
				while ((index = srcStr.indexOf(splitStr)) != -1) {
					currStr = srcStr.substring(0, index);
					if (index == 0) {
						index += splitCount;
						if (index > strLength) {
							index = strLength;
						}
						if (isPrevNull) {
							strList.add(null);
						}
						isPrevNull = true;
					} else {
						strList.add(currStr);
						isPrevNull = false;
					}
					srcStr = srcStr.substring(index);
				}
				if(!srcStr.equals("")){
					strList.add(srcStr);
				} else {
					strList.add(null);
				}
				returnArr = new String[strList.size()];
				strList.toArray(returnArr);
			}
		}
		return returnArr;
	}


	/**
	 * 功能：方法重载。分割字符串为数组，默认分割字符串";"。
	 * @param srcStr String
	 * @return String[]
	 */
	public static String[] splitStr(String srcStr) {
		return splitStr(srcStr, WorldConstant.SPLITOR);
	}

	/**
	 * 功能：方法重载。将某一字符串分割成二维数组。第一维分割字符串为SplitStr1，第二维分割字符串为SplitStr2
	 * 例如 字符串 str = "abd;sfgd;wgfd@@@fsdg;sgfgf;weter;@@@dgfdh;wgfg;@@@gfhgh"，采用strSplit(str, "@@@", ";")将被分割成如下数组
	 * arr[0][0] = "abd"<br>
	 * arr[0][1] = "sfgd"<br>
	 * arr[0][2] = "wgfd"<br>
	 * arr[1][0] = "fsdg"<br>
	 * arr[1][1] = "fsdg"<br>
	 * arr[1][2] = "weter"<br>
	 * arr[3][0] = "dgfdh"<br>
	 * arr[3][1] = "wgfg"<br>
	 * arr[4][0] = "gfhgh"
	 * @param srcStr    原字符串；
	 * @param splitStr1 第一维分割字符串；
	 * @param splitStr2 第二维分割字符串；
	 * @return 成功则返回二维数组，否则返回null。
	 */
	public static String[][] splitStr(String srcStr, String splitStr1,
									  String splitStr2) {
		String[][] returnArr = {{srcStr}
		};
		if (srcStr != null && !srcStr.equals("") && splitStr1 != null &&
				!splitStr1.equals("") && splitStr2 != null && !splitStr2.equals("")) {
			String[] tempArr = splitStr(srcStr, splitStr1);
			if (tempArr != null && tempArr.length > 0) {
				String[] tempArr2 = null;
				int length = 1;
				ArrayList tempList = new ArrayList();
				for (int i = 0; i < tempArr.length; i++) {
					tempArr2 = splitStr(tempArr[i], splitStr2);
					length = (length > tempArr2.length) ? length :
							tempArr2.length;
					tempList.add(tempArr2);
				}
				returnArr = new String[tempArr.length][length];
				for (int i = 0; i < tempList.size(); i++) {
					returnArr[i] = (String[]) tempList.get(i);
				}
			}
		}
		return returnArr;
	}

	/**
	 * 功能：判断某一字符串是否包含另一字符串由分隔符(splitStr)分开的全部子串的全部或其中之一。
	 * @param srcStr     原字符串；
	 * @param subStr     子字符串；
	 * @param splitStr   子字符串分隔符；
	 * @param allOrOneOf 判断全部包含还是包含子字符串由分隔符分隔成的字符串中的任何一个；true则判断全部，false则判断其中之一；
	 * @param ignCase    是否忽略大小写；
	 * @return 布尔值。
	 * @throws StrException 字符串格式化异常
	 */
	private static boolean Contains(String srcStr, String subStr,
									String splitStr, boolean allOrOneOf,
									boolean ignCase) throws StrException {
		StrValidator.validateEmpty(srcStr);
		StrValidator.validateEmpty(subStr);
		StrValidator.validateEmpty(splitStr);
		boolean retVal = allOrOneOf;
		String[] tempArr = splitStr(subStr, splitStr);
		if (tempArr != null && tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if ((allOrOneOf && !contains(srcStr, tempArr[i], ignCase)) ||
						(!allOrOneOf && contains(srcStr, tempArr[i], ignCase))) {
					retVal = !retVal;
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(splitStr)分开的全部子串。
	 * @param srcStr      原字符串；
	 * @param subStr      目标字符串；
	 * @param splitStr    目标字符串分隔符；
	 * @param ignorceCase 是否忽略大小写；
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsAll(String srcStr, String subStr,
									  String splitStr, boolean ignorceCase) throws
			StrException {
		return (Contains(srcStr, subStr, splitStr, true, ignorceCase));
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(splitStr)分开的全部子串，不忽略大小写，原样查找。
	 * @param srcStr   原字符串；
	 * @param subStr   目标字符串；
	 * @param splitStr 目标字符串分隔符；
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsAll(String srcStr, String subStr,
									  String splitStr) throws StrException {
		return Contains(srcStr, subStr, splitStr, true, false);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(“;”)分开的全部子串，不忽略大小写，原样查找。
	 * @param srcStr      原字符串；
	 * @param subStr      目标字符串；
	 * @param ignorceCase 是否忽略大小写
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsAll(String srcStr, String subStr,
									  boolean ignorceCase) throws StrException {
		return Contains(srcStr, subStr, WorldConstant.SPLITOR, true,
				ignorceCase);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(“;”)分开的全部子串。
	 * @param srcStr 原字符串；
	 * @param subStr 目标字符串；
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsAll(String srcStr, String subStr) throws
			StrException {
		return Contains(srcStr, subStr, WorldConstant.SPLITOR, true, false);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(splitStr)分开的全部子串中的任何一个。
	 * @param srcStr      原字符串；
	 * @param subStr      目标字符串；
	 * @param splitStr    目标字符串分隔符；
	 * @param ignorceCase 是否忽略大小写
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsOneOf(String srcStr, String subStr,
										String splitStr, boolean ignorceCase) throws
			StrException {
		return Contains(srcStr, subStr, splitStr, false, ignorceCase);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(splitStr)分开的全部子串中的任何一个，不忽略大小写，原样查找。
	 * @param srcStr   原字符串；
	 * @param subStr   目标字符串；
	 * @param splitStr 目标字符串分隔符；
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsOneOf(String srcStr, String subStr,
										String splitStr) throws StrException {
		return Contains(srcStr, subStr, splitStr, false, false);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(“;”)分开的全部子串中的任何一个。
	 * @param srcStr      原字符串；
	 * @param subStr      目标字符串；
	 * @param ignorceCase 是否忽略大小写
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsOneOf(String srcStr, String subStr,
										boolean ignorceCase) throws
			StrException {
		return Contains(srcStr, subStr, WorldConstant.SPLITOR, false,
				ignorceCase);
	}

	/**
	 * 功能：方法重载。判断某字符串(srcStr)是否含有另一字符串(subStr)中由分隔符(“;”)分开的全部子串中的任何一个。
	 * @param srcStr 原字符串；
	 * @param subStr 目标字符串；
	 * @return 返回true或false。
	 * @throws StrException
	 */
	public static boolean containsOneOf(String srcStr, String subStr) throws
			StrException {
		return Contains(srcStr, subStr, WorldConstant.SPLITOR, false, false);
	}

	/**
	 * 功能：把原字符串srcStr中第startTime个和第endTime个之间（含首尾）的所有oldStr替换成newStr，返回替换后的整个字符串
	 * @param srcStr    原字符串；
	 * @param oldStr    原子字符串
	 * @param startTime 开始序数；
	 * @param endTime   结束序数；
	 * @param newStr    欲替换成的新子符串；
	 * @param ignCase   是否忽略大小写，true则表示忽略大小写，否则原样查找；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, int startTime,
									int endTime, String newStr,
									boolean ignCase) {
		String retStr = srcStr;
		String finalStr = srcStr;
		if (!isEmpty(srcStr) && !isEmpty(oldStr)) {
			if (ignCase) {
				srcStr = srcStr.toLowerCase();
				oldStr = oldStr.toLowerCase();
			}
			int fromIndex = findIndex(srcStr, oldStr, startTime, ignCase);
			int toIndex = findIndex(srcStr, oldStr, endTime, ignCase);
			if (fromIndex != -1 && toIndex != -1) {
				retStr = finalStr.substring(0, fromIndex);
				String finalTail = finalStr.substring(toIndex + oldStr.length());
				srcStr = srcStr.substring(fromIndex, toIndex + oldStr.length());
				finalStr = finalStr.substring(fromIndex,
						toIndex + oldStr.length());
				int index = -1;
				while ((index = srcStr.indexOf(oldStr)) != -1) {
					retStr += finalStr.substring(0, index) + newStr;
					srcStr = srcStr.substring(index + oldStr.length());
					finalStr = finalStr.substring(index + oldStr.length());
				}
				if (!isEmpty(finalStr)) {
					retStr += finalStr;
				}
				retStr += finalTail;
			}
		}
		return retStr;
	}

	/**
	 * 功能：方法重载。把原字符串srcStr中第startTime个和第endTime个之间（含首尾）的所有oldStr替换成newStr，查找中不忽略大小写，返回替换后的整个字符串。
	 * @param srcStr    原字符串；
	 * @param oldStr    原子字符串
	 * @param startTime 开始序数；
	 * @param endTime   结束序数；
	 * @param newStr    欲替换成的新子符串；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, int startTime,
									int endTime, String newStr) {
		return replaceStr(srcStr, oldStr, startTime, endTime, newStr, false);
	}

	/**
	 * 功能：方法重载。把原字符串srcStr中从第startTime个之后（含startTime）的所有oldStr替换成newStr，返回替换后的整个字符串。
	 * @param srcStr    原字符串；
	 * @param oldStr    原子字符串
	 * @param startTime 开始序数；
	 * @param newStr    欲替换成的新子符串；
	 * @param ignCase   是否忽略大小写，true则表示忽略大小写，否则原样查找；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, int startTime,
									String newStr, boolean ignCase) {
		int endTime = containNum(srcStr, oldStr, ignCase);
		return replaceStr(srcStr, oldStr, startTime, endTime, newStr,
				ignCase);
	}

	/**
	 * 功能：方法重载。把原字符串srcStr中第endTime之前（含endTime）的所有oldStr替换成newStr，返回替换后的整个字符串
	 * @param srcStr  原字符串；
	 * @param oldStr  原子字符串
	 * @param endTime 结束序数；
	 * @param newStr  欲替换成的新子符串；
	 * @param ignCase 是否忽略大小写，true则表示忽略大小写，否则原样查找；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, String newStr,
									int endTime, boolean ignCase) {
		return replaceStr(srcStr, oldStr, 1, endTime, newStr, ignCase);
	}

	/**
	 * 功能：方法重载。把原字符串srcStr中从第startTime个之后（含startTime）的所有oldStr替换成newStr，替换过程中，原样查找，不忽略大小写。返回替换后的整个字符串
	 * @param srcStr    原字符串；
	 * @param oldStr    原子字符串
	 * @param startTime 开始序数；
	 * @param newStr    欲替换成的新子符串；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, int startTime,
									String newStr) {
		int endTime = containNum(srcStr, oldStr, false);
		return replaceStr(srcStr, oldStr, startTime, endTime, newStr, false);
	}

	/**
	 * 功能：方法重载。把原字符串srcStr中第endTime之前（含endTime）的所有oldStr替换成newStr，替换过程中，原样查找，不忽略大小写。返回替换后的整个字符串
	 * @param srcStr  原字符串；
	 * @param oldStr  原子字符串
	 * @param endTime 结束序数；
	 * @param newStr  欲替换成的新子符串；
	 * @return 替换后的字符串，如没有替换则返回原字符串。
	 */
	public static String replaceStr(String srcStr, String oldStr, String newStr,
									int endTime) {
		return replaceStr(srcStr, oldStr, 1, endTime, newStr, false);
	}

	/**
	 * 功能：方法重载。字符串替换。将原字符串(srcStr)中的子字符串(oldStr)替换为新字符串(newStr)，并返回替换后的字符串。
	 * @param srcStr  原字符串
	 * @param oldStr  原子字符串
	 * @param newStr  新字符串
	 * @param ignCase 是否忽略大小写
	 * @return 替换后的字符串
	 */
	public static String replaceStr(String srcStr, String oldStr, String newStr,
									boolean ignCase) {
		int endTime = containNum(srcStr, oldStr, ignCase);
		return replaceStr(srcStr, oldStr, 1, endTime, newStr, ignCase);
	}

	/**
	 * 功能：方法重载。字符串替换。将原字符串(srcStr)中的子字符串(oldStr)替换为新字符串(newStr)，并返回替换后的字符串。
	 * 注意 原样查找，不忽略大小写。
	 * @param srcStr 原字符串
	 * @param oldStr 原子字符串
	 * @param newStr 新字符串
	 * @return 替换后的字符串
	 */
	public static String replaceStr(String srcStr, String oldStr, String newStr) {
		int endTime = containNum(srcStr, oldStr, false);
		return replaceStr(srcStr, oldStr, 1, endTime, newStr, false);
	}

	/**
	 * 功能：替换原字符串srcStr中第一个旧字符串oldStr为新字符串newStr。
	 * @param srcStr String
	 * @param oldStr String
	 * @param newStr String
	 * @return String 包含旧字符串时返回替换后的字符串，否者原样返回。
	 */
	public static String replaceFirst(String srcStr, String oldStr,
									  String newStr) {
		String retStr = srcStr;
		if (!isEmpty(srcStr) && !isEmpty(oldStr) && !isEmpty(newStr)) {
			int index = srcStr.indexOf(oldStr);
			if (index > -1) {
				retStr = srcStr.substring(0, index);
				retStr += newStr;
				retStr += srcStr.substring(index + oldStr.length());
			}
		}
		return retStr;
	}

	/**
	 * 功能：获取字符串。内部调用。
	 * @param srcStr        原字符串；
	 * @param subStr        子字符串；
	 * @param existTime     子字符串出新的次数；
	 * @param afterOrBefore 取子字符串之前还是之后的字符串作为返回字符串；
	 * @param ignCase       是否忽略大小写；
	 * @return 返回获取的字符串，否则返回空字符串""。
	 */
	private static String getStr(String srcStr, String subStr, int existTime,
								 boolean afterOrBefore, boolean ignCase) {
		String retStr = "";
		if (!isEmpty(srcStr) && !isEmpty(subStr) &&
				containNum(srcStr, subStr, ignCase) >= existTime && existTime > 0) {
			int index = findIndex(srcStr, subStr, existTime, ignCase);
			if (afterOrBefore) {
				retStr = srcStr.substring(subStr.length() + index);
			} else {
				retStr = srcStr.substring(0, index);
			}
		}
		return retStr;
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第existTime次之后出现的字符串。
	 * @param srcStr    原字符串
	 * @param subStr    子串
	 * @param existTime 子串在原字符串中出现的次数
	 * @param ignCase   是否忽略大小写；
	 * @return 成功则返回原字符串srcStr的子串subStr第existTime之后出现的字符串，否则返回""。
	 */
	public static String getStrAfter(String srcStr, String subStr,
									 int existTime, boolean ignCase) {
		return getStr(srcStr, subStr, existTime, true, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第1次之后出现的字符串。
	 * @param srcStr  原字符串
	 * @param subStr  子串
	 * @param ignCase 是否忽略大小写；
	 * @return 成功则返回原字符串srcStr的子串subStr第1次后出现的字符串，否则返回""。
	 */
	public static String getStrAfter(String srcStr, String subStr,
									 boolean ignCase) {
		return getStr(srcStr, subStr, 1, true, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第existTime次之后出现的字符串，不忽略大小写，原样查找。
	 * @param srcStr    原字符串
	 * @param subStr    子串
	 * @param existTime 子串在原字符串中出现的次数
	 * @return 成功则返回原字符串srcStr的子串subStr第existTime之后出现的字符串，否则返回""。
	 */
	public static String getStrAfter(String srcStr, String subStr,
									 int existTime) {
		return getStr(srcStr, subStr, existTime, true, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第1次之后出现的字符串，不忽略大小写，原样查找。
	 * @param srcStr 原字符串
	 * @param subStr 子串
	 * @return 成功则返回原字符串srcStr的子串subStr第existTime之后出现的字符串，否则返回""。
	 */
	public static String getStrAfter(String srcStr, String subStr) {
		return getStr(srcStr, subStr, 1, true, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第existTime次之前出现的字符串。
	 * @param srcStr    原字符串；
	 * @param subStr    子串；
	 * @param existTime 子串在原字符串中出现的次数；
	 * @param ignCase   是否忽略大小写；
	 * @return 成功则返回原字符串srcStr的子串subStr第existTime次之前出现的字符串，否则返回""。
	 */
	public static String getBeforeStr(String srcStr, String subStr,
									  int existTime, boolean ignCase) {
		return getStr(srcStr, subStr, existTime, false, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第1次之前出现的字符串。
	 * @param srcStr  原字符串；
	 * @param subStr  子串；
	 * @param ignCase 是否忽略大小写；
	 * @return 成功则返回原字符串srcStr的子串subStr第1次之前出现的字符串，否则返回""。
	 */
	public static String getBeforeStr(String srcStr, String subStr,
									  boolean ignCase) {
		return getStr(srcStr, subStr, 1, false, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第existTime次之前出现的字符串，不忽略大小写，原样查找。
	 * @param srcStr    原字符串；
	 * @param subStr    子串；
	 * @param existTime 子串在原字符串中出现的次数；
	 * @return 成功则返回原字符串srcStr的子串subStr第existTime次之前出现的字符串，否则返回""。
	 */
	public static String getBeforeStr(String srcStr, String subStr,
									  int existTime) {
		return getStr(srcStr, subStr, existTime, false, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr的子串subStr第1次之前出现的字符串，不忽略大小写，原样查找。
	 * @param srcStr 原字符串；
	 * @param subStr 子串；
	 * @return 成功则返回原字符串srcStr的子串subStr第1次之前出现的字符串，否则返回""。
	 */
	public static String getBeforeStr(String srcStr, String subStr) {
		return getStr(srcStr, subStr, 1, false, false);
	}

	/**
	 * 功能：取原字符串srcStr中，第existTime1个子串subStr1和第existTime2个子串subStr2之间的字符串。
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param subStr2    第二个子串；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @param ignCase    是否忽略大小写
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									int existTime1, String subStr2,
									int existTime2, boolean ignCase) {
		String retStr = "";
		if (!isEmpty(srcStr) && !isEmpty(subStr1) && !isEmpty(subStr2) &&
				existTime1 > 0 && existTime2 > 0 &&
				containNum(srcStr, subStr1, ignCase) >= existTime1 &&
				containNum(srcStr, subStr2, ignCase) >= existTime2) {
			int length1 = subStr1.length();
			int length2 = subStr2.length();
			int index1 = findIndex(srcStr, subStr1, existTime1, ignCase);
			int index2 = findIndex(srcStr, subStr2, existTime2, ignCase);
			if (index1 + length1 < index2) {
				retStr = srcStr.substring(index1 + length1, index2);
			}
			if (index2 + length2 < index1) {
				retStr = srcStr.substring(index2 + length2, index1);
			}
		}
		return retStr;
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第existTime1个子串subStr1和第existTime2个子串subStr2之间的字符串。不忽略大小写，原样查找
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param subStr2    第二个子串；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									int existTime1, String subStr2,
									int existTime2) {
		return getBetwStr(srcStr, subStr1, existTime1, subStr2, existTime2, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第1个子串subStr1和第existTime2个子串subStr2之间的字符串。
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param subStr2    第二个子串；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @param ignCase    是否忽略大小写
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									String subStr2, int existTime2,
									boolean ignCase) {
		return getBetwStr(srcStr, subStr1, 1, subStr2, existTime2, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第1个子串subStr1和第existTime2个子串subStr2之间的字符串，不否忽略大小写，原样查找。
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param subStr2    第二个子串；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */

	public static String getBetwStr(String srcStr, String subStr1,
									String subStr2, int existTime2) {
		return getBetwStr(srcStr, subStr1, 1, subStr2, existTime2, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第existTime1个子串subStr1和第1个子串subStr2之间的字符串。
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param subStr2    第二个子串；
	 * @param ignCase    是否忽略大小写
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									int existTime1, String subStr2,
									boolean ignCase) {
		return getBetwStr(srcStr, subStr1, existTime1, subStr2, 1, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第existTime1个子串subStr1和第1个子串subStr2之间的字符串，不忽略大小写，原样查找。
	 * @param srcStr     原字符串；
	 * @param subStr1    第一个子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param subStr2    第二个子串；
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									int existTime1, String subStr2) {
		return getBetwStr(srcStr, subStr1, existTime1, subStr2, 1, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第1个子串subStr1和第1个子串subStr2之间的字符串。
	 * @param srcStr  原字符串；
	 * @param subStr1 第一个子串；
	 * @param subStr2 第二个子串；
	 * @param ignCase 是否忽略大小写
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									String subStr2, boolean ignCase) {
		return getBetwStr(srcStr, subStr1, 1, subStr2, 1, ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第1个子串subStr1和第1个子串subStr2之间的字符串，不忽略大小写，原样查找。
	 * @param srcStr  原字符串；
	 * @param subStr1 第一个子串；
	 * @param subStr2 第二个子串；
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr1,
									String subStr2) {
		return getBetwStr(srcStr, subStr1, 1, subStr2, 1, false);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第existTime1个子串subStr和第existTime2个子串subStr之间的字符串。
	 * @param srcStr     原字符串；
	 * @param subStr     子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @param ignCase    是否忽略大小写
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr,
									int existTime1, int existTime2,
									boolean ignCase) {
		return getBetwStr(srcStr, subStr, existTime1, subStr, existTime2,
				ignCase);
	}

	/**
	 * 功能：方法重载。取原字符串srcStr中，第existTime1个子串subStr和第existTime2个子串subStr之间的字符串，不忽略大小写，原样查找。
	 * @param srcStr     原字符串；
	 * @param subStr     子串；
	 * @param existTime1 要求第一个子串出现的次数；
	 * @param existTime2 要求第二个子串出现的次数；
	 * @return 成功则返回两个子串之间的字符串，否则返回""。
	 */
	public static String getBetwStr(String srcStr, String subStr,
									int existTime1, int existTime2) {
		return getBetwStr(srcStr, subStr, existTime1, subStr, existTime2, false);
	}

	/**
	 * 功能：产生count个srcStr，其间以连接字符串splitStr连接，但splitStr不作结尾用
	 * @param srcStr   产生目标字符串的种子字符串；
	 * @param splitStr 连接字符串
	 * @param count    产生个数
	 * @return 产生的目标字符串
	 */
	public static String getStrByCount(String srcStr, String splitStr,
									   int count) {
		StringBuffer strBuff = new StringBuffer("");
		if (!isEmpty(srcStr) && splitStr != null && count > 0) {
			while (count-- > 0) {
				strBuff.append(srcStr);
				strBuff.append(splitStr);
			}
		}
		String retStr = strBuff.toString();
		if (!isEmpty(retStr)) {
			retStr = retStr.substring(0, retStr.length() - splitStr.length());
		}
		return retStr;
	}

	/**
	 * 功能：方法重载。不需要分割字符串连接
	 * @param srcStr String
	 * @param count  int
	 * @return String
	 */
	public static String getStrByCount(String srcStr, int count) {
		return getStrByCount(srcStr, "", count);
	}

	/**
	 * 功能：ISO-8859-1编码和GBK编码之间的转换
	 * @param srcStr             原字符串
	 * @param toChineseOrUniCode 转码方式，true表示将字符串从ISO-8859-1转换为GBK，反之则相反；
	 * @return 转码后的字符串。
	 */
	private static String UnicodeChineseTransfer(String srcStr,
												 boolean toChineseOrUniCode) {
		String retStr = srcStr;
		try {
			if (srcStr != null && !srcStr.equals("")) {
				retStr = (toChineseOrUniCode) ?
						new String(srcStr.getBytes("ISO-8859-1"), "GBK") :
						new String(srcStr.getBytes("GBK"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			Logger.logError(e);
		}
		return retStr;
	}

	/**
	 * 功能：将中文字符串从GBK编码转换为ISO-8859-1编码。
	 * @param srcStr 原字符串
	 * @return 转码后的字符串
	 */
	public static String ChineseToUnicode(String srcStr) {
		return UnicodeChineseTransfer(srcStr, false);
	}

	/**
	 * 功能：将中文字符串从ISO-8859-1编码转换为GBK编码。
	 * @param srcStr 原字符串
	 * @return 转码后的字符串
	 */
	public static String UnicodeToChinese(String srcStr) {
		return UnicodeChineseTransfer(srcStr, true);
	}

	/**
	 * 功能：判断某一字符串srcStr是否包含子串subStr，且子串subStr不出现在开头和结尾。
	 * @param srcStr String
	 * @param subStr String
	 * @return boolean
	 */
	private static boolean isMiddleWith(String srcStr, String subStr) {
		boolean retVal = false;
		if (srcStr != null && subStr != null && !srcStr.equals("") &&
				!subStr.equals("")) {
			retVal = (contains(srcStr, subStr) && !srcStr.startsWith(subStr) &&
					!srcStr.endsWith(subStr));
		}
		return retVal;
	}

	/**
	 * 功能：判断某字符串是否以另一字符串开头，忽略大小写。
	 * @param srcStr String
	 * @param subStr String
	 * @return boolean
	 */
	public static boolean startsWith(String srcStr, String subStr) {
		boolean retVal = false;
		if (srcStr != null && !isEmpty(subStr)) {
			srcStr = srcStr.toLowerCase();
			subStr = subStr.toLowerCase();
			retVal = srcStr.startsWith(subStr);
		}
		return retVal;
	}

	/**
	 * 功能：判断某字符串是否以另一字符串结尾，忽略大小写。
	 * @param srcStr String
	 * @param subStr String
	 * @return boolean
	 * @throws StrException
	 */
	public static boolean endsWith(String srcStr, String subStr) throws
			StrException {
		StrValidator.validateEmpty(srcStr);
		StrValidator.validateEmpty(subStr);
		srcStr = srcStr.toLowerCase();
		subStr = subStr.toLowerCase();
		return srcStr.endsWith(subStr);
	}

	/**
	 * 功能：判断字符串srcStr是否为数字，包含百分数的判断。由于分数表示过于复杂，在此不作判断，即认为其不是数子。
	 * @param srcStr 原字符串；
	 * @return 是数字则返回true，否则返回false。
	 */
	public static boolean isNumber(String srcStr) {
		boolean retVal = false;
		String checkStr = "0123456789+-%.";
		if (srcStr != null && !srcStr.equals("") &&
				containNum(srcStr, "+") <= 1 && containNum(srcStr, "-") <= 1 &&
				containNum(srcStr, "%") <= 1 && containNum(srcStr, ".") <= 1) {
			String tempStr = "";
			int containNum = 0;
			for (int i = 0; i < srcStr.length(); i++) {
				tempStr = srcStr.substring(i, i + 1);
				if (contains(checkStr, tempStr)) {
					containNum++;
				}
			}
			if (containNum == srcStr.length()) {
				tempStr = srcStr;
				if (contains(srcStr, "+") && srcStr.startsWith("+")) {
					tempStr = srcStr.substring(1);
				}
				if (contains(srcStr, "-") && srcStr.startsWith("-")) {
					tempStr = srcStr.substring(1);
				}
				if (contains(tempStr, "%") && tempStr.endsWith("%")) {
					tempStr = tempStr.substring(0, tempStr.length() - 1);
				}
				if (contains(tempStr, ".") && isMiddleWith(tempStr, ".")) {
					tempStr = tempStr.substring(0, tempStr.indexOf(".")) +
							tempStr.substring(tempStr.indexOf(".") + 1);
				}
				retVal = isInteger(tempStr);
			}

		}
		return retVal;
	}

	/**
	 * 功能：判断某一字符串是否正整数。
	 * @param srcStr 字符串
	 * @return 是正整数则返回true，否则返回false。
	 */
	public static boolean isInteger(String srcStr) {
		boolean retVal = true;
		if (!isEmpty(srcStr)) {
			String numStr = "0123456789";
			String tempStr = "";
			for (int i = 0; i < srcStr.length(); i++) {
				tempStr = srcStr.substring(i, i + 1);
				if (numStr.indexOf(tempStr) == -1) {
					retVal = false;
					break;
				}
			}
		} else {
			retVal = false;
		}
		return retVal;
	}

	/**
	 * 功能：格式化字符串。在字符串的前面加前导字符toChar或后面加后缀字符toChar，以补足满足要求的长度。
	 * 可用在要求各字符串具有相同长度的场合。
	 * @param srcStr    欲格式化的字符串；
	 * @param objLength 目标长度；
	 * @param toChar    欲添加的字符；
	 * @param preOrPost 前导还是后导；true表示在srcStr前面加上toChar字符，反之则在后面
	 * @return 返回格式化后的字符串，否则原样返回。
	 */
	public static String formatStr(String srcStr, int objLength, char toChar,
								   boolean preOrPost) {
		srcStr = (srcStr == null) ? "" : srcStr;
		int count = objLength - srcStr.length();
		char[] tempArr = {toChar};
		if (count > 0) {
			if (preOrPost) {
				srcStr = getStrByCount(new String(tempArr), count) + srcStr;
			} else {
				srcStr = srcStr + getStrByCount(new String(tempArr), count);
			}
		}
		return srcStr;
	}

	/**
	 * 功能：方法重载。前导字符或后缀字符为0。可用在要
	 * 求各种编号具有相同长度的场合，例如数据库中的流水号。
	 * @param srcStr       String
	 * @param objectLength int
	 * @param preOrPost    boolean
	 * @return String 返回值 成功则返回格式化后的字符串，否则返回null。
	 */
	public static String formatStr(String srcStr, int objectLength,
								   boolean preOrPost) {
		return formatStr(srcStr, objectLength, '0', preOrPost);
	}

	/**
	 * 功能：方法重载。完成前导字符的增加。返回值 成功则为格式化后的字符串；否则返回null。
	 * @param srcStr       String
	 * @param objectLength int
	 * @param toChar       char
	 * @return String
	 */
	public static String formatStr(String srcStr, int objectLength, char toChar) {
		return formatStr(srcStr, objectLength, toChar, true);
	}

	/**
	 * 功能：方法重载。完成前导0的增加。返回值 成功则为格式化后的字符串；否则返回null。
	 * @param srcStr       String
	 * @param objectLength int
	 * @return String
	 */
	public static String formatStr(String srcStr, int objectLength) {
		return formatStr(srcStr, objectLength, '0', true);
	}

	/**
	 * 功能:用于屏蔽页面提交的内容含有JSP代码的情况，当从数据库中读出时避免浏览器进行解析
	 * @param srcStr String
	 * @return String
	 */
	public static String htmlStrEncode(String srcStr) {
		StringBuffer strBuffer = new StringBuffer();
		if (srcStr == null) {
			return srcStr;
		}
		int j = srcStr.length();
		for (int i = 0; i < j; i++) {
			char c = srcStr.charAt(i);
			switch (c) {
				case 60:
					strBuffer.append("&lt;");
					break;
				case 62:
					strBuffer.append("&gt;");
					break;
				case 38:
					strBuffer.append("&amp;");
					break;
				case 34:
					strBuffer.append("&quot;");
					break;
				case 169:
					strBuffer.append("&copy;");
					break;
				case 174:
					strBuffer.append("&reg;");
					break;
				case 165:
					strBuffer.append("&yen;");
					break;
				case 8364:
					strBuffer.append("&euro;");
					break;
				case 8482:
					strBuffer.append("&#153;");
					break;
				case 13:
					if (i < j - 1 && srcStr.charAt(i + 1) == 10) {
						strBuffer.append("<br>");
						i++;
					}
					break;
				case 32:
					if (i < j - 1 && srcStr.charAt(i + 1) == ' ') {
						strBuffer.append(" &nbsp;");
						i++;
						break;
					}
				default:
					strBuffer.append(c);
					break;
			}
		}
		return new String(strBuffer.toString());
	}

	/**
	 * 功能：从字符串中将数字提取出来
	 * @param srcStr String
	 * @return String
	 */
	public static String getNumFromText(String srcStr) {
		String retStr = "";
		if (!isEmpty(srcStr)) {
			int strLength = srcStr.length();
			String currChar = "";
			for (int i = 0; i < strLength; i++) {
				currChar = srcStr.substring(i, i + 1);
				if (isInteger(currChar)) {
					retStr += currChar;
				}
			}
		}
		return retStr;
	}

	/**
	 * 功能：获取字符串去除数字后的字符串
	 * @param srcStr String
	 * @return String
	 */
	public static String trimNum(String srcStr) {
		String retStr = "";
		if (!isEmpty(srcStr)) {
			int strLength = srcStr.length();
			String currChar = "";
			for (int i = 0; i < strLength; i++) {
				currChar = srcStr.substring(i, i + 1);
				if (!isInteger(currChar)) {
					retStr += currChar;
				}
			}
		}
		return retStr;
	}

	/**
	 * 功能：判断指定对象是否为空。
	 * @param obj Object
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		return (obj == null || obj.toString().equals(""));
	}

	/**
	 * 功能：判断指定对象是否不为空
	 * @param obj Object
	 * @return boolean
	 */
	public static boolean  isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

	/**
	 * 功能：将srcStr中前或后的subStr去除掉。
	 * @param srcStr     String
	 * @param subStr     String
	 * @param startOrEnd boolean true表示去除前面部分，false表示去除后面部分。
	 * @return String
	 */
	public static String trim(String srcStr, String subStr, boolean startOrEnd) {
		if (!isEmpty(subStr)) {
			if (startOrEnd) {
				while (srcStr.startsWith(subStr)) {
					srcStr = srcStr.substring(subStr.length());
				}
			} else {
				while (srcStr.endsWith(subStr)) {
					srcStr = srcStr.substring(0, srcStr.length() - subStr.length());
				}
			}
		}
		return srcStr;
	}

	/**
	 * 功能：将srcStr中前后的subStr去除掉。
	 * @param srcStr String
	 * @param subStr String
	 * @return String
	 */
	public static String trim(String srcStr, String subStr) {
		srcStr = trim(srcStr, subStr, false);
		return trim(srcStr, subStr, true);
	}

	/**
	 * 功能：将srcStr中前后的空格去除掉。
	 * @param srcStr String
	 * @return String
	 */
	public static String trim(String srcStr) {
		return trim(srcStr, WorldConstant.EMPTY_SPACE);
	}

	/**
	 * 功能：将列表内容转换为字符串。
	 * @param list List
	 * @return String
	 */
	public static String list2String(List list) {
		StringBuffer str = new StringBuffer();
		String strVal = "";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				strVal = list.get(i).toString().trim();
			}
			if (str.length() == 0) {
				str.append("'" + strVal + "'");
			} else {
				str.append(",'" + strVal + "'");
			}
			strVal = "";
		}
		return str.toString();
	}

	/**
	 * 功能：将数据库字段名转换为符合规则的Java字段名，变量命名格式符合JavaBean规则。
	 * @param dbField String
	 * @return String
	 */
	public static String getJavaField(String dbField) {
		String retStr = dbField;
		if (!isEmpty(dbField) && isAllUpperCase(dbField)) {
			StringBuffer tmpBuff = new StringBuffer();
			String[] splitArr = splitStr(dbField, "_");
			String arrEle = "";
			for (int i = 0; i < splitArr.length; i++) {
				arrEle = splitArr[i];
				if (i > 0) {
					tmpBuff.append(arrEle.substring(0, 1).toUpperCase());
					tmpBuff.append(arrEle.substring(1).toLowerCase());
				} else {
					tmpBuff.append(arrEle.toLowerCase());
				}
			}
			retStr = tmpBuff.toString();
		}
		return retStr;
	}

	/**
	 * 功能：将web页面的表单字段名转换为符合规则的数据库字段名，且以大写形式返回。
	 * @param javaField String
	 * @return String
	 */
	public static String getDbField(String javaField) {
		String retStr = "";
		if (!isEmpty(javaField) && !isAllUpperCase(javaField)) {
			int strLength = javaField.length();
			char thisChar = 1;
			for (int i = 0; i < strLength; i++) {
				thisChar = javaField.charAt(i);
				if (thisChar >= 97 && thisChar <= 122) {
					retStr += (char) (thisChar - 32);
				} else if (thisChar >= 65 && thisChar <= 90) { //大写,则在前面加一个"_"
					if (i == 0) {
						retStr += thisChar;
					} else {
						retStr += "_" + thisChar;
					}
				} else {
					retStr += thisChar;
				}
			}
		} else {
			retStr = javaField;
		}
		return retStr;
	}

	/**
	 * 功能：获取数组中等于srcStr的元素。
	 * @param srcStr String
	 * @param strArr String[]
	 * @param igCase boolean
	 * @return String 返回对应元素值或空字符串。
	 */
	public static String getStrInArr(String srcStr, String[] strArr,
									 boolean igCase) {
		String retStr = "";
		if (!isEmpty(srcStr) && strArr != null && strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (contains(srcStr, strArr[i], igCase)) {
					retStr = strArr[i];
					break;
				}
			}
		}
		return retStr;
	}

	/**
	 * 功能：方法重载。获取数组中等于srcStr的元素。
	 * @param srcStr String
	 * @param strArr String[]
	 * @return String
	 */
	public static String getStrInArr(String srcStr, String[] strArr) {
		return getStrInArr(srcStr, strArr, false);
	}

	/**
	 * 功能：转化字符串为符合xml要求的字符串
	 * @param strValue String
	 * @return String
	 */
	public static String formatXML(String strValue) {
		strValue = replaceStr(strValue, "&", "&amp;");
		strValue = replaceStr(strValue, "<", "&lt;");
		strValue = replaceStr(strValue, ">", "&gt;");
		strValue = replaceStr(strValue, "\"", "&quot;");
		strValue = replaceStr(strValue, "'", "&apos;");
		return strValue;
	}

	public static String nullToString(Object val) {
		if (val == null) {
			val = "";
		}
		return val.toString();
	}

	/**
	 * 功能：将后台的数组赋给前台的JavaScript数组，JavaScript数组名为jsArrName。
	 * @param srcVal    String[]
	 * @param jsArrName String
	 * @return String
	 */
	public static String dataToJsArr(String[] srcVal, String jsArrName) {
		String retStr = "";
		if (srcVal != null && srcVal.length > 0) {
			retStr = "var " + jsArrName + " = new Array(";
			int arrLength = srcVal.length;
			for (int i = 0; i < arrLength; i++) {
				retStr += "'" + srcVal[i] + "', ";
			}
			retStr = retStr.substring(0, retStr.length() - 2);
			retStr += ");\n";
		}
		return retStr;
	}

	public static String dataToJsArr(String[] srcVal, String jsArrName,
									 String defaultValue) {
		for (int i = 0; i < srcVal.length; i++) {
			srcVal[i] = defaultValue;
		}
		return dataToJsArr(srcVal, jsArrName);
	}

	/**
	 * 功能：将后台的数组赋给前台的JavaScript数组，JavaScript数组名为jsArrName。
	 * @param srcVal    String[][]
	 * @param jsArrName String
	 * @return String
	 */
	public static String dataToJsArr(String[][] srcVal, String jsArrName) {
		String retStr = "";
		if (srcVal != null && srcVal.length > 0) {
			retStr = "var " + jsArrName + " = new Array()\n";
			int arrLength = srcVal.length;
			for (int i = 0; i < arrLength; i++) {
				retStr += jsArrName + "[" + i + "] = new Array(";
				for (int j = 0; j < srcVal[i].length; j++) {
					retStr += "\"" + srcVal[i][j] + "\", ";
				}
				retStr = retStr.substring(0, retStr.length() - 2);
				retStr += ");\n";
			}
		}
		return retStr;
	}

	/**
	 * 功能：将日期的月和天自动补零
	 * @param srcStr 原日期字符串
	 * @return 补充之后的日期
	 */
	public static String formatDate(String srcStr) {
		String[] strArr = splitStr(srcStr, "-");
		if (strArr[1] != null && strArr[1].length() == 1) {
			strArr[1] = "0" + strArr[1];
		}
		if (strArr[2] != null && strArr[2].length() == 1) {
			strArr[2] = "0" + strArr[2];
		}
		return strArr[0] + "-" + strArr[1] + "-" + strArr[2];
	}

	/**
	 * 功能 ：将金额补零
	 * @param srcStr 传入的金额字符串
	 * @return 补零后的金额
	 */
	public static String getFormatCurrence(String srcStr) {
		if (srcStr.equals("")) {
			srcStr = "0";
		}
		String pattern = "###0.00";
		double src = Double.parseDouble(srcStr);
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(src);
	}

	/**
	 * 功能 ：将金额补零
	 * @param srcStr 传入的金额
	 * @return 补零后的金额
	 */
	public static String getFormatCurrence(double srcStr) {
		String pattern = "###0.00";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(srcStr);
	}

	/**
	 * 功能：判断某字符串是否以数组中某一元素结束。
	 * @param srcStr String
	 * @param strArr String[]
	 * @return boolean
	 */
	public static boolean endsWith(String srcStr, String[] strArr) {
		boolean endsWith = false;
		try {
			if (!isEmpty(srcStr) && strArr != null && strArr.length > 0) {
				for (int i = 0; i < strArr.length; i++) {
					if (isEmpty(strArr[i])) {
						continue;
					}
					if (endsWith(srcStr, strArr[i])) {
						endsWith = true;
						break;
					}
				}
			}
		} catch (StrException ex) {
			ex.printLog();
		}
		return endsWith;
	}

	/**
	 * 功能：将传入字符串首字母大写后返回。
	 * @param passValue String
	 * @return String
	 */
	public static String upperCapital(String passValue) {
		String strVal = "";
		if (!isEmpty(passValue)) {
			strVal = passValue.substring(0, 1).toUpperCase();
			strVal += passValue.substring(1);
		}
		return strVal;
	}

	/**
	 * 功能：将传入字符串首字母小写后返回。
	 * @param passValue String
	 * @return String
	 */
	public static String lowerCapital(String passValue) {
		String strVal = "";
		if (!isEmpty(passValue)) {
			strVal = passValue.substring(0, 1).toLowerCase();
			strVal += passValue.substring(1);
		}
		return strVal;
	}

	/**
	 * 功能：将CSV的显示字符串转换为CSV存储格式的字符串。
	 * @param csvStr String
	 * @return String
	 */
	public static String encodeCSVStr(String csvStr) {
		String retStr = csvStr;
		boolean hasProcessed = false;
		if (contains(retStr, WorldConstant.QUOTE_MARK)) { //如果包含引号，先做转义处理，但前后先不增加分号。
			retStr = replaceStr(retStr, WorldConstant.QUOTE_MARK, WorldConstant.DOUBLE_QUOTE_MARK);
			hasProcessed = true;
		}
		if (contains(retStr, WorldConstant.COMMA_CHAR) || hasProcessed) { //前后加上双引号。
			retStr = WorldConstant.QUOTE_MARK + retStr + WorldConstant.QUOTE_MARK;
		}
		return retStr;
	}

	/**
	 * 功能：将CSV存储的字符串解析转换为显示字符串。
	 * @param csvStr String
	 * @return String
	 * @throws StrException
	 */
	public static String decodeCSVStr(String csvStr) throws StrException {
		StringBuffer strB = new StringBuffer();
		if (!isEmpty(csvStr)) {
			int quoteCount = containNum(csvStr, WorldConstant.QUOTE_MARK);
			if (quoteCount % 2 != 0) {
				throw new StrException("传入的字符串“" + csvStr + "”并非合法的CSV存储格式。");
//				csvStr = replaceFirst(csvStr, WorldConstant.QUOTE_MARK, "");
			}
			int startIndex = csvStr.indexOf(WorldConstant.QUOTE_MARK);
			int lastIndex = csvStr.lastIndexOf(WorldConstant.QUOTE_MARK);
			if (startIndex == 0 && lastIndex == csvStr.length() - 1) {
				csvStr = csvStr.substring(1, lastIndex);
			}
			int strLength = csvStr.length();
			StringBuffer quoteBuf = new StringBuffer();
			String quoteStr = "";
			String thisChar = "";
			for (int i = 0; i < strLength; i++) {
				thisChar = csvStr.substring(i, i + 1);
				if (!thisChar.equals(WorldConstant.QUOTE_MARK)) {
					quoteStr = quoteBuf.toString();
					quoteStr = quoteStr.substring(0, quoteStr.length() / 2);
					strB.append(quoteStr);
					quoteBuf = new StringBuffer();
				}
				if (thisChar.equals(WorldConstant.QUOTE_MARK)) {
					quoteBuf.append(thisChar);
				} else {
					strB.append(thisChar);
				}
			}
			if (quoteBuf.length() > 0) {
				quoteStr = quoteBuf.toString();
				quoteStr = quoteStr.substring(0, quoteStr.length() / 2);
				strB.append(quoteStr);
			}
		}
		return strB.toString();
	}

	/**
	 * 功能：将CSV的一行存储字符串解析成显示用字符串列表。
	 * @param csvStr String
	 * @return List
	 * @throws StrException
	 */
	public static List decodeCSVRow(String csvStr) throws StrException {
		List csvList = new ArrayList();
		if (!isEmpty(csvStr)) {
			int strLength = csvStr.length();
			String cellStr = "";
			String thisChar = "";
			int quoteNumber = 0;
			for (int i = 0; i < strLength; i++) {
				thisChar = csvStr.substring(i, i + 1);
				if (thisChar.equals(WorldConstant.COMMA_CHAR)) {
					quoteNumber = containNum(cellStr, WorldConstant.QUOTE_MARK);
					if (quoteNumber % 2 == 0) {
						csvList.add(decodeCSVStr(cellStr));
						cellStr = "";
					} else {
						cellStr += thisChar;
					}
				} else {
					cellStr += thisChar;
				}
			}
			if (cellStr.length() != 0) {
				csvList.add(decodeCSVStr(cellStr));
			}
		}
		return csvList;
	}

	/**
	 * 功能：将科学计数法显示的数字转换为字符串实数
	 * @param scienceNum String
	 * @return String
	 */
	public static String formatScienceNum(String scienceNum) {
		String retValue = scienceNum;
		if (!isEmpty(scienceNum)) {
			int index = scienceNum.indexOf("E");
			if (index > -1 && index != scienceNum.length() - 1) {
				String strHeader = scienceNum.substring(0, index);
				String strTail = scienceNum.substring(index + 1);
				if (isNumber(strHeader) && isNumber(strTail)) {
					double matter = Double.parseDouble(strHeader); //底数
					double exponent = Double.parseDouble(strTail); //指数
					BigDecimal bigNum = new BigDecimal(matter * Math.pow(10, exponent));
					retValue = bigNum.toString();
				}
			}
		}
		return retValue;
	}

	/**
	 * 功能：判断某字符串是否全是大写字母。
	 * @param strValue String
	 * @return boolean
	 */
	public static boolean isAllUpperCase(String strValue) {
		boolean isAllUpperCase = true;
		if (!isEmpty(strValue)) {
			int strLength = strValue.length();
			char singleChar = (char) -1;
			for (int i = 0; i < strLength; i++) {
				singleChar = strValue.charAt(i);
				if (singleChar == '_') {
					continue;
				}
				if (singleChar == '-') {
					continue;
				}
				if (singleChar > -1 && singleChar < 10) {
					continue;
				}
				if (singleChar >= 48 && singleChar <= 57) { //0~9
					continue;
				}
				if (singleChar > 90 || singleChar < 65) { //65:A  , 90:Z
					isAllUpperCase = false;
					break;
				}
			}
		}
		return isAllUpperCase;
	}

	/**
	 * 功能：判断某字符串是否全是大写字母。
	 * @param strValue String
	 * @return boolean
	 */
	public static boolean isAllLowerCase(String strValue) {
		boolean isAllLowerCase = true;
		if (!isEmpty(strValue)) {
			int strLength = strValue.length();
			char singleChar = (char) -1;
			for (int i = 0; i < strLength; i++) {
				singleChar = strValue.charAt(i);
				if (singleChar == '_') {
					continue;
				}
				if (singleChar == '-') {
					continue;
				}
				if (singleChar > -1 && singleChar < 10) {
					continue;
				}
				if (singleChar > 122 || singleChar < 97) {
					isAllLowerCase = false;
					break;
				}
			}
		}
		return isAllLowerCase;
	}

	/**
	 * 功能：判断传入字符串是否十六进制表示的RGB颜色描述。
	 * @param color String
	 * @return boolean
	 */
	public static boolean isColorStr(String color) {
		boolean isColor = false;
		if (!isEmpty(color)) {
			color = color.toLowerCase();
			if (color.startsWith("#") && containNum(color, "#") == 1) {
				color = color.substring(1);
				String standardStr = "0123456789abcdef";
				isColor = true;
				String singleChar = "";
				for (int i = 0; i < color.length(); i++) {
					singleChar = color.substring(i, i + 1);
					if (standardStr.indexOf(singleChar) == -1) {
						isColor = false;
						break;
					}
				}
			}
		}
		return isColor;
	}

	public static String xmlFormat(String str) {
		String m = str;
		//& < > " '，需要分别转换成 &amp; &lt; &gt; &quot; &apos;
		m = replaceStr(m, "&", "&amp;");
		m = replaceStr(m, "<", "&lt;");
		m = replaceStr(m, ">", "&gt;");
		m = replaceStr(m, "\"", "&quot;");
		m = replaceStr(m, "'", "&apos;");
		return m;
	}

	public static void main(String[] args){
		String a = "userAge;;userName;;userPwd;;;;";//null ,gf null
		String b = ";;";
		StrUtilHave.splitStr(a, b);
	}
}

