package com.t0p47.currencyconverter.view.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.t0p47.currencyconverter.databinding.ActivitySettingsBinding
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.ads.AdsController
import com.t0p47.currencyconverter.extension.DiActivity
import com.t0p47.currencyconverter.inapp_purchase.BillingManager
import javax.inject.Inject

class SettingsActivity @Inject constructor() : DiActivity() {

    @Inject
    lateinit var billingManager: BillingManager

    @Inject
    lateinit var adsController: AdsController

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.btnSwitchTheme.setOnClickListener {
            when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.btnUpdatePro.setOnClickListener {
            Log.d("LOG_TAG","SettingsActivity: onCreate: buyProVersion")

            val sku = "update_to_professional"
            billingManager.buyProVersion(this, sku)
        }

        billingManager.skusWithSkuDetails.value?.forEach { (key, value) ->
            Log.d("LOG_TAG","SettingsActivity: onCreate: key: $key, value: $value")
        }


        binding.btnShare.setOnClickListener {
                ShareCompat.IntentBuilder(this)
                    .setType("text/plain")
                    .setChooserTitle("Share CurrencyConverter")
                    .setSubject("CurrencyConverter")
                    .setText("Check the CurrencyConverter app on Android: http://play.google.com/store/apps/details?id=$packageName")
                    .startChooser();
        }

        binding.btnRateApp.setOnClickListener {
            val uri = Uri.parse("market://details?id=${packageName}")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try{
                startActivity(goToMarket)
            }catch (e: ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=${packageName}")))
            }
        }

        binding.btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("tertylian759@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_contact_subject))

            val packageInfo = packageManager.getPackageInfo(packageName, 0)

            val versionName = packageInfo.versionName

            val deviceModel = Build.MODEL

            emailIntent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.email_contact_enter_text)} \n\n\n App: ${getString(R.string.app_name)} $versionName " +
                    "\n$deviceModel, Android SDK: ${Build.VERSION.SDK_INT} \n\n\n${getString(R.string.email_contact_android_send)}")

            emailIntent.type = "message/rfc822"

            //TODO: Use R.string for translate
            startActivity(Intent.createChooser(emailIntent, "Выберите приложение для отправки Email"))
        }
    }
}