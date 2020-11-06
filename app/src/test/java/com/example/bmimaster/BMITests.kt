package com.example.bmimaster

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import org.junit.jupiter.api.Test

class BMITests : FunSpec({


    test("metric single value") {
        val height = 180f
        val weight = 80f
        val (bmi, _) = BMI.getBMI(height, weight, MeasureSystem.Metric)
        bmi shouldBe 24.7f.plusOrMinus(0.1f)
    }

    test("imperial single value") {
        val height = 72f
        val weight = 170f
        val (bmi, _) = BMI.getBMI(height, weight, MeasureSystem.Imperial)

        bmi shouldBe 23.1f.plusOrMinus(0.1f)
    }

    //For some reason numericFloats won't work :<
    test("metric many values"){
        checkAll(Arb.numericDoubles(100.0, 200.0), Arb.numericDoubles(1.0, 200.0)){ height, weight ->
            BMI.getBMI(height.toFloat(), weight.toFloat(), MeasureSystem.Metric).first.toDouble() shouldBe (weight/(height/100.0)/(height/100.0)).plusOrMinus(
                0.5
            )
        }
    }

    //Same as above
    test("imperial many values"){
        checkAll(Arb.numericDoubles(50.0, 100.0), Arb.numericDoubles(100.0, 200.0)){ height, weight ->
            BMI.getBMI(height.toFloat(), weight.toFloat(), MeasureSystem.Imperial).first.toDouble() shouldBe (weight/(height)/(height)*703).plusOrMinus(
                0.5
            )
        }
    }
})




