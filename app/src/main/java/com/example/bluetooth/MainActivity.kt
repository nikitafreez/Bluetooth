package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mBlueAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter()

        if(mBlueAdapter == null) {
            statusBluetoothTv.text = "Bluetooth недоступен"
        }
        else {
            statusBluetoothTv.text = "Bluetooth доступен"
        }

        onButton.setOnClickListener {
            if(!mBlueAdapter!!.isEnabled) {
                showToast("Включение Bluetooth...")

                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            }
            else {
                showToast("Bluetooth включен")
            }
        }

        discoverableButton.setOnClickListener {
            if(!mBlueAdapter!!.isDiscovering) {
                showToast("Сделайте ваше устройство видимым для других")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_DISCOVER_BT)
            }
        }

        offButton.setOnClickListener {
            if(mBlueAdapter!!.isEnabled) {
                mBlueAdapter!!.disable()
                showToast("Bluetooth выключается")
            }
            else {
                showToast("Bluetooth выключен")
            }
        }

        pairedButton.setOnClickListener {
            if(mBlueAdapter!!.isEnabled) {
                pairedTv.text = "Сопряжённые устройтсва"
                val devices = mBlueAdapter!!.bondedDevices
                for(device in devices) {
                    pairedTv.append("""Device: ${device.name}, $device""".trimIndent())
                }
            }
            else {
                showToast("Включите Bluetooth, чтобы увидеть сопряжённые устройства")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 0
        private  const val REQUEST_DISCOVER_BT = 1
    }
}