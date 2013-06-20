/**
 * @author mshtang
 */


/**************************************************½ğ¶î(ÈËÃñ±Ò)´óĞ¡Ğ´×ª»»*******************************************/

/**
 * ¹¦ÄÜ£º½«Êı×Ö½ğ¶î×ª»»Îª´óĞ´ÖĞÎÄ½ğ¶î¡£
 */
function toChineseCapital(num)
{
    if (isNaN(num) || num > Math.pow(10, 12))   return   ""
    var cn = "ÁãÒ¼·¡ÈşËÁÎéÂ½Æâ°Æ¾Á"
    var unit = new Array("Ê°°ÛÇª", "·Ö½Ç")
    var unit1 = new Array("ÍòÒÚ", "")
    var numArray = num.toString().split(".")
    var start = new Array(numArray[0].length - 1, 2)

    function toChinese(num, index)
    {
        var num = num.replace(/\d/g, function   ($1)
        {
            return   cn.charAt($1) + unit[index].charAt(start-- % 4 ? start % 4 : -1)
        })
        return   num
    }

    for (var i = 0; i < numArray.length; i++)
    {
        var tmp = ""
        for (var j = 0; j * 4 < numArray[i].length; j++)
        {
            var strIndex = numArray[i].length - (j + 1) * 4
            var str = numArray[i].substring(strIndex, strIndex + 4)
            var start = i ? 2 : str.length - 1
            var tmp1 = toChinese(str, i)
            tmp1 = tmp1.replace(/(Áã.)+/g, "Áã").replace(/Áã+$/, "")
            tmp1 = tmp1.replace(/^Ò¼Ê°/, "Ê°")
            tmp = (tmp1 + unit1[i].charAt(j - 1)) + tmp
        }
        numArray[i] = tmp
    }

    numArray[1] = numArray[1] ? numArray[1] : ""
    numArray[0] = numArray[0] ? numArray[0] + "Ô²" : numArray[0], numArray[1].replace(/^Áã+/, "");
    numArray[1] = numArray[1].match(/·Ö/) ? numArray[1] : numArray[1] + "Õû"
    return   numArray[0] + numArray[1]
}

function toNumberCase(num)
{
    var numArray = new Array()
    var unit = "ÒÚÍòÔ²$"
    for (var i = 0; i < unit.length; i++)
    {
        var re = eval("/" + (numArray[i - 1] ? unit.charAt(i - 1) : "") + "(.*)" + unit.charAt(i) + "/")
        if (num.match(re))
        {
            numArray[i] = num.match(re)[1].replace(/^Ê°/, "Ò¼Ê°")
            numArray[i] = numArray[i].replace(/[ÁãÒ¼·¡ÈşËÁÎéÂ½Æâ°Æ¾Á]/g, function   ($1)
            {
                return   "ÁãÒ¼·¡ÈşËÁÎéÂ½Æâ°Æ¾Á".indexOf($1)
            })
            numArray[i] = numArray[i].replace(/[·Ö½ÇÊ°°ÛÇª]/g, function   ($1)
            {
                return   "*" + Math.pow(10, "·Ö½Ç   Ê°°ÛÇª   ".indexOf($1) - 2) + "+"
            }).replace(/^\*|\+$/g, "").replace(/Õû/, "0")
            numArray[i] = "(" + numArray[i] + ")*" + Math.ceil(Math.pow(10, (2 - i) * 4))
        }
        else   numArray[i] = 0
    }
    return   eval(numArray.join("+"))
}
