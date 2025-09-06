package com.victor

import org.junit.jupiter.api.Test

class MyStringParserTest {

    // Bedford's law: in many real-life sets of numerical data, the leading digit is likely to be small.

    // from assignment: We already converted our documents to a long string which has the amounts in it,
    //                  eg: "account1: 123.45, account2: 67.89, account3: 0.12"


    // chi-square: statistical method for determining if there is a significant association between categorical
    //             variables by comparing observed frequencies to expected frequencies.
    //             It is used to test hypotheses about count data and to determine if differences between
    //             observed and expected outcomes are due to chance or a true relationship.
    //             There are two main types: the chi-square goodness of fit test, which compares one categorical
    //             variable to expected proportions, and the chi-square test of independence, which checks for
    //             a relationship between two categorical variables.
    //
    //             This test is a general purpose test for verifying whether data are distributed according to any
    //             arbitrary distribution. The test statistic is computed from counts rather than proportions
    //             (https://blog.bigml.com/2015/05/15/detecting-numeric-irregularities-with-benfords-law)
    //
    //             I'm guessing we need the goodness of fit test here.


    // googling around: you c an use Bedford's law to detect fraud/suspicious data
    // "A post mortem Benford’s law analysis of the accounts for several bankrupt US municipalities revealed inconsistent figures, which could be indicative of the fiscal dishonesty which led to the municipalities’ financial ruin."
    // https://blog.bigml.com/2015/05/15/detecting-numeric-irregularities-with-benfords-law/


    @Test
    fun testParse() {


        val parser = MyStringParser();
        val actual = parser.parse("hello")
//        assert(result == "HELLO")
    }

}