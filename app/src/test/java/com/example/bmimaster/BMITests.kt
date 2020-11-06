package com.example.bmimaster

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BMITests : FunSpec({


    test("metric") {
        val height = 180f
        val weight = 80f
        val (bmi, color) = BMI.getBMI(height, weight, MeasureSystem.Metric)
        bmi shouldBe 24.7f.plusOrMinus(0.1f)
    }

    test("imperial") {
        val height = 72f
        val weight = 170f
        val (bmi, color) = BMI.getBMI(height, weight, MeasureSystem.Imperial)

        bmi shouldBe 23.1f.plusOrMinus(0.1f)
    }
})




