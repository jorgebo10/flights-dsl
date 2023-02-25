package com.jbo.flights.dsl

import com.jbo.flights.dsl.Provider.Flybondi
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FlightsTest {

    @Test
    fun `test me`() {
        val aFlight = book flight {
            assign pnr "XDF" on Flybondi
            departs from "BUE" to "MIA" on 1 February 2023 at 14..30 //TODO validate only one exists, fecha mas que hoy
            returning on 2 February 2023 at 15..40   //TODO: validate a departs exists, only one returning, fecha mayor a departs
        }

        assertEquals("XDF", aFlight.pnr)
        assertEquals(Flybondi.code, aFlight.provider)
        assertEquals("BUE", aFlight.origin)
        assertEquals("MIA", aFlight.destination)
        assertEquals("2023-02-01", aFlight.departsOn)
        assertEquals("2023-02-02", aFlight.returnsOn)
        assertEquals("14:30", "${aFlight.departsAt.first}:${aFlight.departsAt.last}")
        assertEquals("15:40", "${aFlight.returnsAt.first}:${aFlight.returnsAt.last}")
    }
}

/*

    @Test
    fun `test me`() {
        val aFlight = book flight {
            assign pnr "XDF" on Flybondi validating "AR"
            departs {
                from "BUE" to "MIA"
                on 1 February 2023 at 14..30
                flight {
                    number "345"
                    cabin "Standard"
                    carrier {
                        marketing "AR"
                        operating "AR"
                    }
                    equipment "320"
                }
            }

            returning {
                on 2 February 2023 at 15..40
                flight {
                    number "345"
                    cabin "Standard"
                    carrier {
                        marketing "AR"
                        operating "AR"
                    }
                    equipment "320"
                }
            }
        }
    }
}
 */