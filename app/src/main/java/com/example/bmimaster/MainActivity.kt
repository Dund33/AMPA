package com.example.bmimaster

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmimaster.databinding.ActivityMainBinding
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var measureSystem: MeasureSystem = MeasureSystem.Metric

    private lateinit var sharedPreferences: SharedPreferences

    private var savedBMIs by Delegates.notNull<Int>()

    private val recordLength = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BMIRepo.init(applicationContext, "data", recordLength)

        sharedPreferences = applicationContext
            .getSharedPreferences("measurements", MODE_PRIVATE)

        savedBMIs = sharedPreferences.getInt("measurement_count", 0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val saveButton = binding.button
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar
        val bmiTV = binding.bmiEditTextNumber
        bmiTV.setOnLongClickListener { openDetails() }
        saveButton.setOnLongClickListener { openSaved() }

        val listener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateUI()
            }
        }

        massSeekBar.setOnSeekBarChangeListener(listener)
        heightSeekBar.setOnSeekBarChangeListener(listener)
        setupMetric()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.select_system, menu)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val weight = binding.massSeekBar.progress
        val height = binding.heightSeekBar.progress
        outState.putString("system", measureSystem.toString())
        outState.putInt("weight", weight)
        outState.putInt("height", height)
    }

    private fun openDetails(): Boolean {
        val bmiEditTextNumber = binding.bmiEditTextNumber
        val intent = Intent(this, Details::class.java)
        intent.putExtra(
            "bmi", bmiEditTextNumber
                .text
                .toString()
                .toDouble()
        )
        startActivityForResult(intent, 0) // Activity is started with requestCode

        return true
    }

    private fun openSaved(): Boolean {
        val intent = Intent(this, BMIList::class.java)
        startActivityForResult(intent, 0) // Act
        return true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val systemString = savedInstanceState.getString("system")
        measureSystem = when (systemString) {
            MeasureSystem.Metric.toString() -> MeasureSystem.Metric
            MeasureSystem.Imperial.toString() -> MeasureSystem.Imperial
            else -> throw Exception("cannot restore :<")
        }
        setup()
        val weight = savedInstanceState.getInt("weight")
        val height = savedInstanceState.getInt("height")
        binding.massSeekBar.progress = weight
        binding.heightSeekBar.progress = height
        //TODO odt. stanu
    }

    @Suppress("UNUSED_PARAMETER")
    fun swapSystem(caller: MenuItem) {
        measureSystem = when (measureSystem) {
            MeasureSystem.Imperial -> MeasureSystem.Metric
            MeasureSystem.Metric -> MeasureSystem.Imperial
        }
        setup()
    }

    private fun setup() {
        if (measureSystem == MeasureSystem.Imperial)
            setupImperial()
        else
            setupMetric()
    }

    private fun setupMetric() {
        val massTV = binding.massTV
        val heightTV = binding.heightTV
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar

        massTV.setText(R.string.mass_metric)
        heightTV.setText(R.string.height_metric)
        massSeekBar.progress = 0
        heightSeekBar.progress = 0
        massSeekBar.max = resources.getInteger(R.integer.maximal_mass_metric)
        heightSeekBar.max = resources.getInteger(R.integer.maximal_height_metric)

    }

    private fun setupImperial() {
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar
        val massTV = binding.massTV
        val heightTV = binding.heightTV

        massTV.setText(R.string.mass_imperial)
        heightTV.setText(R.string.height_imperial)
        massSeekBar.progress = 0
        heightSeekBar.progress = 0
        massSeekBar.max = resources.getInteger(R.integer.maximal_mass_imperial)
        heightSeekBar.max = resources.getInteger(R.integer.maximal_height_imperial)

    }

    private fun getBMI(): Pair<Float, Int> {
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar

        val mass = when (measureSystem) {
            MeasureSystem.Metric ->
                massSeekBar.progress + resources.getInteger(R.integer.base_mass_metric)
            MeasureSystem.Imperial ->
                massSeekBar.progress + resources.getInteger(R.integer.base_mass_imperial)
        }.toFloat()

        val height = when (measureSystem) {
            MeasureSystem.Metric ->
                heightSeekBar.progress + resources.getInteger(R.integer.base_height_metric)
            MeasureSystem.Imperial ->
                heightSeekBar.progress + resources.getInteger(R.integer.base_height_imperial)
        }.toFloat()

        //Update labels
        val massEditTextNumber = binding.massEditTextNumber
        val heightEditTextNumber = binding.heightEditTextNumber
        massEditTextNumber.setText(mass.toString())
        heightEditTextNumber.setText(height.toString())

        return BMI.getBMI(height, mass, measureSystem)
    }

    fun updateUI() {
        val (bmi, color) = getBMI()
        val bmiEditTextNumber = binding.bmiEditTextNumber
        bmiEditTextNumber.setTextColor(color)
        bmiEditTextNumber.setText(bmi.toString())
    }

    @Suppress("UNUSED_PARAMETER")
    fun saveBMI(caller: View) {
        val (bmi, _) = getBMI()
        BMIRepo.addBMI(bmi)
    }
}