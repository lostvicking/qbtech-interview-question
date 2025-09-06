package com.victor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BenfordLawCheckerTest {


    private var sut: BenfordLawChecker = BenfordLawChecker();

    private var accountGen: AccountStringGenerator  = AccountStringGenerator();


    @Test
    fun chiSquaredTest() {
        val benfordAccounts = accountGen.generateAccountBalanceString(true, 100);
        val randomAccounts = accountGen.generateAccountBalanceString(false, 100);

//        sut.chiSquaredTest(benfordAccounts, 0.05);
    }

}