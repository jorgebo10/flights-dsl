package com.jbo.flights.dsl

object book {
    infix fun flight(block: Flight.() -> Unit): Flight = Flight().apply(block)
}

enum class Provider(val code: String) {
    Flybondi("FB"),
    Viva("VIV")
}

class Flight {
    var pnr = ""
    var provider = ""
    var origin = ""
    var destination = ""
    var departsOn = ""
    var returnsOn = ""
    var departsAt = IntRange.EMPTY
    var returnsAt = IntRange.EMPTY
    val assign = this
    val departs = DepartsFrom()
    val returning = ReturningOn()

    inner class ReturningOn {
        infix fun on(day: Int): DateCreator {
            return DateCreator(day, { this@Flight.returnsOn = it }, { this@Flight.returnsAt = it })
        }
    }

    inner class DepartsTo {
        infix fun to(destination: String): DepartsOn {
            this@Flight.destination = destination
            return DepartsOn()
        }
    }

    inner class DepartsOn {
        infix fun on(day: Int): DateCreator {
            return DateCreator(day, { this@Flight.departsOn = it }, { this@Flight.departsAt = it })
        }
    }

    inner class OnProvider {
        infix fun on(provider: Provider) {
            this@Flight.provider = provider.code
        }
    }

    inner class TimeAt(
        val block: (IntRange) -> Unit
    ) {
        infix fun at(time: IntRange) {
            block(time)
        }
    }

    inner class DepartsFrom {
        infix fun from(origin: String): DepartsTo {
            this@Flight.origin = origin
            return DepartsTo()
        }
    }

    inner class DateCreator(
        val day: Int,
        val dateBlock: (String) -> Unit,
        val timeBlock: (IntRange) -> Unit

    ) {
        private fun setFlightDate(month: Int, year: Int) {
            dateBlock(java.time.LocalDate.of(year, month, day).toString())
        }

        infix fun January(year: Int): TimeAt {
            setFlightDate(1, year)
            return TimeAt(timeBlock)
        }

        infix fun February(year: Int): TimeAt {
            setFlightDate(2, year)
            return TimeAt(timeBlock)
        }

        infix fun March(year: Int): TimeAt {
            setFlightDate(3, year)
            return TimeAt(timeBlock)
        }
    }

    infix fun pnr(pnr: String): OnProvider {
        this.pnr = pnr
        return OnProvider()
    }
}