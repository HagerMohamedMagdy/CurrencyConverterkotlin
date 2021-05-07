package challengue.swensonhe.com.currencyconverter.frontEndChallenge

import java.util.*

/**
 * Created by Hager Magdy on 2021-05-08.
 */

fun main(args: Array<String>) {
    val three = "dorm"
    //Input Stream
    val sc = Scanner(System.`in`)

    //Enter String Value
    println("Enter String1 : ")
    val str1: String = sc.nextLine()

    println("Enter String2 : ")
    val str2: String = sc.next()


    if (checkAnagramText(str1, str2)) {
        println("Anagram Strings !!")
    } else {
        println("Strings are not Anagram !!")
    }
}

private fun areAnagrams(one: String, two: String): Boolean {
    val map = HashMap<Char, Int>()

    for (c in one.toCharArray())
        if (map.containsKey(c))
            map[c] = map[c]!! + 1
        else
            map[c] = 1

    for (c in two.toCharArray())
        if (!map.containsKey(c))
            return false
        else {
            map[c] = map[c]!! - 1

            if (map[c] == 0)
                map.remove(c)
        }
    return map.isEmpty()
}
//anther function
//Anagram Function
fun checkAnagramText(str1: String, str2: String): Boolean {
    //Both String Length must be Equal
    if (str1.length != str2.length) {
        return false
    }

    //Convert Strings to character Array
    val strArray1 = str1.toCharArray()
    val strArray2 = str2.toCharArray()

    //Sort the Arrays
    Arrays.sort(strArray1)
    Arrays.sort(strArray2)

    //Convert Arrays to String
    val sortedStr1 = String(strArray1)
    val sortedStr2 = String(strArray2)

    //Check Both String Equals or not After Sorting
    //and Return value True or False
    return sortedStr1 == sortedStr2
}