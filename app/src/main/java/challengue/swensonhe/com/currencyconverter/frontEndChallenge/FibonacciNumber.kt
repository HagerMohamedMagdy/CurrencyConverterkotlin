package challengue.swensonhe.com.currencyconverter.frontEndChallenge

/**
 * Created by Hager Magdy on 2020-07-11.
 */
// A. recursive approach

@JvmOverloads
tailrec fun fibonacci(n: Int, a: Int = 0, b: Int = 1): Int =
    when (n) {
        0 -> a
        1 -> b
        else -> fibonacci(n - 1, b, a + b)
    }
//B.iterative approach
fun main(args: Array<String>) {
   var i = 1
    val n = 10
    var t1 = 1
    var t2 = 1

    print("First $n terms: ")

    while (i <= n) {
        print("$t1 + ")

        val sum = t1 + t2
        t1 = t2
        t2 = sum

        i++

    }}



