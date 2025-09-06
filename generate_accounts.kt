import kotlin.random.Random

fun generateAccountString(): String {
    val entries = mutableListOf<String>()
    
    for (i in 1..100) {
        val accountNumber = "account$i"
        val accountBalance = generateRandomBalance()
        entries.add("$accountNumber: $accountBalance")
    }
    
    return entries.joinToString(", ")
}

fun generateRandomBalance(): String {
    // Generate random balance between 100.00 and 90000.99
    val balance = Random.nextDouble(100.0, 90001.0)
    return String.format("%.2f", balance)
}

fun main() {
    val result = generateAccountString()
    println(result)
}
