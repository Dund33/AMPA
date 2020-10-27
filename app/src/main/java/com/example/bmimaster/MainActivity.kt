package com.example.bmimaster

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bmimaster.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var measureSystem: MeasureSystem = MeasureSystem.Metric

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar
        val bmiTV = binding.bmiEditTextNumber
        bmiTV.setOnLongClickListener { openDetails(it) }

        val listener = object: SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun  onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
                {updateUI()}
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
        //TODO oprogramowac zapamietywanie stanu ui (tam gdzie potrzeba)
    }

    fun openDetails(view: View): Boolean{
        val bmiEditTextNumber = binding.bmiEditTextNumber
        val intent = Intent(this, Details::class.java )
        intent.putExtra("bmi", bmiEditTextNumber
            .text
            .toString()
            .toDouble())
        startActivityForResult(intent, 0) // Activity is started with requestCode

        return true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val systemString = savedInstanceState.getString("system")
        measureSystem = when(systemString){
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

    fun swapSystem(caller: MenuItem){
        measureSystem = when(measureSystem){
            MeasureSystem.Imperial -> MeasureSystem.Metric
            MeasureSystem.Metric -> MeasureSystem.Imperial
        }
        setup()
    }

    fun setup(){
        if(measureSystem == MeasureSystem.Imperial)
            setupImperial()
        else
            setupMetric()
    }

    fun setupMetric(){
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

    fun setupImperial(){
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

    fun updateUI(){
        val massSeekBar = binding.massSeekBar
        val heightSeekBar = binding.heightSeekBar

        val mass = when(measureSystem){
            MeasureSystem.Metric ->
                massSeekBar.progress + resources.getInteger(R.integer.base_mass_metric)
            MeasureSystem.Imperial ->
                massSeekBar.progress + resources.getInteger(R.integer.base_mass_imperial)
        }.toDouble()

        val height = when(measureSystem){
            MeasureSystem.Metric ->
                heightSeekBar.progress + resources.getInteger(R.integer.base_height_metric)
            MeasureSystem.Imperial ->
                heightSeekBar.progress + resources.getInteger(R.integer.base_height_imperial)
        }.toDouble()

        //Update labels
        val massEditTextNumber = binding.massEditTextNumber
        val heightEditTextNumber = binding.heightEditTextNumber
        massEditTextNumber.setText(mass.toString())
        heightEditTextNumber.setText(height.toString())
        val bmiEditTextNumber = binding.bmiEditTextNumber

        val (bmi, color) = BMI.getBMI(height, mass, measureSystem)
        bmiEditTextNumber.setTextColor(color)
        bmiEditTextNumber.setText(bmi.toString())
    }
}