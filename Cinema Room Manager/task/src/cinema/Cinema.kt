package cinema

const val freeSeat = 'S'
const val boughtSeat = 'B'

fun printMenu() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    print("> ")
}

fun getTicketPrice(totalSeats: Int, numOfRows: Int, seatRowNumber: Int): Int {
    return if (totalSeats < 60) {
        10
    } else {
        if (numOfRows / 2 > seatRowNumber) {
            10
        } else if (seatRowNumber <= numOfRows / 2) {
            10
        } else {
            8
        }
    }
}

fun getTotalIncome(totalSeats: Int, numOfRows: Int, seatsPerRow: Int): Int {
    return if (totalSeats < 60) {
        totalSeats * 10
    } else {
        val numOfFrontSeats = (numOfRows / 2) * seatsPerRow
        var numOfBackSeats = (numOfRows / 2) * seatsPerRow
        if (numOfRows % 2 != 0) {
            numOfBackSeats += seatsPerRow
        }
        (numOfFrontSeats * 10) + (numOfBackSeats * 8)
    }
}

fun printStatistics(ticketsPurchased: Int, totalSeats: Int, currentIncome: Int, totalIncome: Int) {
    val printPercentage = "%.2f".format((ticketsPurchased.toDouble() / totalSeats.toDouble()) * 100)
    println()
    println("Number of purchased tickets: $ticketsPurchased")
    println("Percentage: $printPercentage%")
    println("Current income: $${currentIncome}")
    println("Total income: $${totalIncome}")
}

fun printSeats(seats: MutableList<MutableList<Char>>, numOfRows: Int, seatRowNumber: Int) {
    println()
    println("Cinema:")
    print("  ")
    for (i in 1..seatRowNumber) {
        print("$i ")
    }
    println()
    for (i in 0 until numOfRows) {
        println("${i + 1} ${seats[i].joinToString(" ")}")
    }
}

fun main() {
    var ticketsPurchased = 0
    var currentIncome = 0
    var selection = 0
    var errorPresent = false

    println("Enter the number of rows:")
    print("> ")
    val numOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    print("> ")
    val seatsPerRow = readln().toInt()
    val seats = MutableList(numOfRows) { MutableList(seatsPerRow) { freeSeat } }
    val totalSeats = seatsPerRow * numOfRows
    val totalIncome = getTotalIncome(totalSeats, numOfRows, seatsPerRow)

    while (true) {
        if (!errorPresent) {
            printMenu()
            selection = readln().toInt()
        }
        when (selection) {
            1 -> printSeats(seats, numOfRows, seatsPerRow)
            2 -> {
                println()
                println("Enter a row number:")
                print("> ")
                val seatRowNumber = readln().toInt()
                println("Enter a seat number in that row:")
                print("> ")
                val seatColumnNumber = readln().toInt()
                try {
                    val currentTicketPrice = getTicketPrice(totalSeats, numOfRows, seatRowNumber)
                    if (seats[seatRowNumber - 1][seatColumnNumber - 1] != boughtSeat) {
                        seats[seatRowNumber - 1][seatColumnNumber - 1] = boughtSeat
                        ticketsPurchased += 1
                        currentIncome += currentTicketPrice
                        println()
                        println("Ticket price: $${currentTicketPrice}")
                        errorPresent = false
                    } else {
                        println()
                        println("That ticket has already been purchased!")
                        errorPresent = true
                        selection = 2
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println()
                    println("Wrong Input!")
                    errorPresent = true
                    selection = 2
                }
            }
            3 -> printStatistics(ticketsPurchased, totalSeats, currentIncome, totalIncome)
            0 -> return
        }
    }
}
