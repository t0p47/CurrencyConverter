package com.t0p47.currencyconverter.view.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.network.Api
import com.t0p47.currencyconverter.repository.currency.CurrencyRepository
import com.t0p47.currencyconverter.room.entity.CurrencyEntity
import com.t0p47.currencyconverter.utils.PreferenceHelper
import com.t0p47.currencyconverter.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val sharPref: PreferenceHelper, private val currencyRepository: CurrencyRepository): ViewModel() {

    /*
    * private val _newFavoriteCurrency = SingleLiveEvent<Boolean>()
    val newFavoriteCurrency: LiveData<Boolean>
        get() = _newFavoriteCurrency
    * */

    //TODO: Wrong place
    private val apiKey = "4438e0d7a2c3a4de1545d68c119f2fc5"

    private val _currenciesLoaded = SingleLiveEvent<Boolean>()
    val currenciesLoaded: LiveData<Boolean>
        get() = _currenciesLoaded

    fun initCurrenciesFirstTime(){

        viewModelScope.launch {
            val apiParams = HashMap<String,String>()
            apiParams["access_key"] = apiKey

            try{
                val latestCurrency = Api.retrofitService.getLatestCurrency(apiParams)

                if(sharPref.checkRefreshRateTime()){
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AED, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AED.toString()) 1f else latestCurrency.rates.aedRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AFN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AFN.toString()) 1f else latestCurrency.rates.afnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ALL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ALL.toString()) 1f else latestCurrency.rates.allRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AMD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AMD.toString()) 1f else latestCurrency.rates.amdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ANG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ANG.toString()) 1f else latestCurrency.rates.angRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AOA, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AOA.toString()) 1f else latestCurrency.rates.aoaRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ARS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ARS.toString()) 1f else latestCurrency.rates.arsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AUD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AUD.toString()) 1f else latestCurrency.rates.audRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AWG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AWG.toString()) 1f else latestCurrency.rates.awgRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.AZN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.AZN.toString()) 1f else latestCurrency.rates.aznRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BAM, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BAM.toString()) 1f else latestCurrency.rates.bamRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BBD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BBD.toString()) 1f else latestCurrency.rates.bbdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BDT, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BDT.toString()) 1f else latestCurrency.rates.bdtRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BGN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BGN.toString()) 1f else latestCurrency.rates.bgnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BHD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BHD.toString()) 1f else latestCurrency.rates.bhdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BIF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BIF.toString()) 1f else latestCurrency.rates.bifRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BMD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BMD.toString()) 1f else latestCurrency.rates.bmdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BND, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BND.toString()) 1f else latestCurrency.rates.bndRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BOB, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BOB.toString()) 1f else latestCurrency.rates.bobRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BRL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BRL.toString()) 1f else latestCurrency.rates.brlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BSD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BSD.toString()) 1f else latestCurrency.rates.bsdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BTC, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BTC.toString()) 1f else latestCurrency.rates.btcRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BTN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BTN.toString()) 1f else latestCurrency.rates.btnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BWP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BWP.toString()) 1f else latestCurrency.rates.bwpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BYN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BYN.toString()) 1f else latestCurrency.rates.bynRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BYR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BYR.toString()) 1f else latestCurrency.rates.byrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.BZD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.BZD.toString()) 1f else latestCurrency.rates.bzdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CAD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CAD.toString()) 1f else latestCurrency.rates.cadRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CDF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CDF.toString()) 1f else latestCurrency.rates.cdfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CHF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CHF.toString()) 1f else latestCurrency.rates.chfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CLF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CLF.toString()) 1f else latestCurrency.rates.clfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CLP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CLP.toString()) 1f else latestCurrency.rates.clpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CNY, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CNY.toString()) 1f else latestCurrency.rates.cnyRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.COP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.COP.toString()) 1f else latestCurrency.rates.copRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CRC, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CRC.toString()) 1f else latestCurrency.rates.crcRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CUC, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CUC.toString()) 1f else latestCurrency.rates.cucRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CUP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CUP.toString()) 1f else latestCurrency.rates.cupRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CVE, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CVE.toString()) 1f else latestCurrency.rates.cveRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.CZK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.CZK.toString()) 1f else latestCurrency.rates.czkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.DJF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.DJF.toString()) 1f else latestCurrency.rates.djfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.DKK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.DKK.toString()) 1f else latestCurrency.rates.dkkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.DOP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.DOP.toString()) 1f else latestCurrency.rates.dopRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.DZD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.DZD.toString()) 1f else latestCurrency.rates.dzdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.EGP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.EGP.toString()) 1f else latestCurrency.rates.egpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ERN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ERN.toString()) 1f else latestCurrency.rates.ernRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ETB, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ETB.toString()) 1f else latestCurrency.rates.etbRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.EUR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.EUR.toString()) 1f else latestCurrency.rates.eurRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.FJD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.FJD.toString()) 1f else latestCurrency.rates.fjdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.FKP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.FKP.toString()) 1f else latestCurrency.rates.fkpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GBP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GBP.toString()) 1f else latestCurrency.rates.gbpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GEL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GEL.toString()) 1f else latestCurrency.rates.gelRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GGP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GGP.toString()) 1f else latestCurrency.rates.ggpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GHS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GHS.toString()) 1f else latestCurrency.rates.ghsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GIP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GIP.toString()) 1f else latestCurrency.rates.gipRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GMD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GMD.toString()) 1f else latestCurrency.rates.gmdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GNF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GNF.toString()) 1f else latestCurrency.rates.gnfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GTQ, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GTQ.toString()) 1f else latestCurrency.rates.gtqRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.GYD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.GYD.toString()) 1f else latestCurrency.rates.gydRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.HKD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.HKD.toString()) 1f else latestCurrency.rates.hkdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.HNL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.HNL.toString()) 1f else latestCurrency.rates.hnlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.HRK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.HRK.toString()) 1f else latestCurrency.rates.hrkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.HTG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.HTG.toString()) 1f else latestCurrency.rates.htgRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.HUF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.HUF.toString()) 1f else latestCurrency.rates.hufRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.IDR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.IDR.toString()) 1f else latestCurrency.rates.idrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ILS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ILS.toString()) 1f else latestCurrency.rates.ilsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.IMP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.IMP.toString()) 1f else latestCurrency.rates.impRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.INR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.INR.toString()) 1f else latestCurrency.rates.inrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.IQD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.IQD.toString()) 1f else latestCurrency.rates.iqdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.IRR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.IRR.toString()) 1f else latestCurrency.rates.irrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ISK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ISK.toString()) 1f else latestCurrency.rates.iskRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.JEP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.JEP.toString()) 1f else latestCurrency.rates.jepRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.JMD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.JMD.toString()) 1f else latestCurrency.rates.jmdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.JOD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.JOD.toString()) 1f else latestCurrency.rates.jodRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.JPY, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.JPY.toString()) 1f else latestCurrency.rates.jpyRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KES, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KES.toString()) 1f else latestCurrency.rates.kesRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KGS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KGS.toString()) 1f else latestCurrency.rates.kgsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KHR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KHR.toString()) 1f else latestCurrency.rates.khrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KMF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KMF.toString()) 1f else latestCurrency.rates.kmfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KPW, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KPW.toString()) 1f else latestCurrency.rates.kpwRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KRW, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KRW.toString()) 1f else latestCurrency.rates.krwRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KWD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KWD.toString()) 1f else latestCurrency.rates.kwdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KYD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KYD.toString()) 1f else latestCurrency.rates.kydRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.KZT, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.KZT.toString()) 1f else latestCurrency.rates.kztRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LAK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LAK.toString()) 1f else latestCurrency.rates.lakRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LBP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LBP.toString()) 1f else latestCurrency.rates.lbpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LKR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LKR.toString()) 1f else latestCurrency.rates.lkrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LRD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LRD.toString()) 1f else latestCurrency.rates.lrdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LSL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LSL.toString()) 1f else latestCurrency.rates.lslRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LTL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LTL.toString()) 1f else latestCurrency.rates.ltlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LVL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LVL.toString()) 1f else latestCurrency.rates.lvlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.LYD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.LYD.toString()) 1f else latestCurrency.rates.lydRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MAD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MAD.toString()) 1f else latestCurrency.rates.madRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MDL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MDL.toString()) 1f else latestCurrency.rates.mdlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MGA, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MGA.toString()) 1f else latestCurrency.rates.mgaRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MKD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MKD.toString()) 1f else latestCurrency.rates.mkdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MMK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MMK.toString()) 1f else latestCurrency.rates.mmkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MNT, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MNT.toString()) 1f else latestCurrency.rates.mntRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MOP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MOP.toString()) 1f else latestCurrency.rates.mopRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MRO, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MRO.toString()) 1f else latestCurrency.rates.mroRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MUR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MUR.toString()) 1f else latestCurrency.rates.murRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MVR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MVR.toString()) 1f else latestCurrency.rates.mvrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MWK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MWK.toString()) 1f else latestCurrency.rates.mwkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MXN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MXN.toString()) 1f else latestCurrency.rates.mxnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MYR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MYR.toString()) 1f else latestCurrency.rates.myrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.MZN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.MZN.toString()) 1f else latestCurrency.rates.mznRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NAD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NAD.toString()) 1f else latestCurrency.rates.nadRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NGN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NGN.toString()) 1f else latestCurrency.rates.ngnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NIO, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NIO.toString()) 1f else latestCurrency.rates.nioRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NOK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NOK.toString()) 1f else latestCurrency.rates.nokRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NPR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NPR.toString()) 1f else latestCurrency.rates.nprRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.NZD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.NZD.toString()) 1f else latestCurrency.rates.nzdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.OMR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.OMR.toString()) 1f else latestCurrency.rates.omrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PAB, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PAB.toString()) 1f else latestCurrency.rates.pabRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PEN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PEN.toString()) 1f else latestCurrency.rates.penRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PGK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PGK.toString()) 1f else latestCurrency.rates.pgkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PHP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PHP.toString()) 1f else latestCurrency.rates.phpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PKR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PKR.toString()) 1f else latestCurrency.rates.pkrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PLN, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PLN.toString()) 1f else latestCurrency.rates.plnRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.PYG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.PYG.toString()) 1f else latestCurrency.rates.pygRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.QAR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.QAR.toString()) 1f else latestCurrency.rates.qarRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.RON, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.RON.toString()) 1f else latestCurrency.rates.ronRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.RSD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.RSD.toString()) 1f else latestCurrency.rates.rsdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.RUB, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.RUB.toString()) 1f else latestCurrency.rates.rubRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.RWF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.RWF.toString()) 1f else latestCurrency.rates.rwfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SAR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SAR.toString()) 1f else latestCurrency.rates.sarRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SBD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SBD.toString()) 1f else latestCurrency.rates.sbdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SCR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SCR.toString()) 1f else latestCurrency.rates.scrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SDG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SDG.toString()) 1f else latestCurrency.rates.sdgRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SEK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SEK.toString()) 1f else latestCurrency.rates.sekRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SGD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SGD.toString()) 1f else latestCurrency.rates.sgdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SHP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SHP.toString()) 1f else latestCurrency.rates.shpRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SLL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SLL.toString()) 1f else latestCurrency.rates.sllRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SOS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SOS.toString()) 1f else latestCurrency.rates.sosRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SRD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SRD.toString()) 1f else latestCurrency.rates.srdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.STD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.STD.toString()) 1f else latestCurrency.rates.stdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SVC, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SVC.toString()) 1f else latestCurrency.rates.svcRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SYP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SYP.toString()) 1f else latestCurrency.rates.sypRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.SZL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.SZL.toString()) 1f else latestCurrency.rates.szlRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.THB, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.THB.toString()) 1f else latestCurrency.rates.thbRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TJS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TJS.toString()) 1f else latestCurrency.rates.tjsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TMT, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TMT.toString()) 1f else latestCurrency.rates.tmtRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TND, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TND.toString()) 1f else latestCurrency.rates.tndRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TOP, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TOP.toString()) 1f else latestCurrency.rates.topRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TRY, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TRY.toString()) 1f else latestCurrency.rates.tryRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TTD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TTD.toString()) 1f else latestCurrency.rates.ttdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TWD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TWD.toString()) 1f else latestCurrency.rates.twdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.TZS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.TZS.toString()) 1f else latestCurrency.rates.tzsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.UAH, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.UAH.toString()) 1f else latestCurrency.rates.uahRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.UGX, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.UGX.toString()) 1f else latestCurrency.rates.ugxRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.USD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.USD.toString()) 1f else latestCurrency.rates.usdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.UYU, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.UYU.toString()) 1f else latestCurrency.rates.uyuRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.UZS, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.UZS.toString()) 1f else latestCurrency.rates.uzsRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.VEF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.VEF.toString()) 1f else latestCurrency.rates.vefRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.VND, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.VND.toString()) 1f else latestCurrency.rates.vndRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.VUV, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.VUV.toString()) 1f else latestCurrency.rates.vuvRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.WST, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.WST.toString()) 1f else latestCurrency.rates.wstRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XAF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XAF.toString()) 1f else latestCurrency.rates.xafRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XAG, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XAG.toString()) 1f else latestCurrency.rates.xagRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XAU, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XAU.toString()) 1f else latestCurrency.rates.xauRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XCD, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XCD.toString()) 1f else latestCurrency.rates.xcdRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XDR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XDR.toString()) 1f else latestCurrency.rates.xdrRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XOF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XOF.toString()) 1f else latestCurrency.rates.xofRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.XPF, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.XPF.toString()) 1f else latestCurrency.rates.xpfRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.YER, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.YER.toString()) 1f else latestCurrency.rates.yerRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ZAR, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ZAR.toString()) 1f else latestCurrency.rates.zarRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ZMK, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ZMK.toString()) 1f else latestCurrency.rates.zmkRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ZMW, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ZMW.toString()) 1f else latestCurrency.rates.zmwRate))
                    currencyRepository.localDataSource.insert(CurrencyEntity(null, CurrencyType.ZWL, CurrencyType.valueOf(latestCurrency.baseCurrency), if(latestCurrency.baseCurrency == CurrencyType.ZWL.toString()) 1f else latestCurrency.rates.zwlRate))

                    _currenciesLoaded.postValue(true)

                    sharPref.storeRefreshRateTime()
                }
            }catch(ex: IOException){
                _currenciesLoaded.postValue(false)
                Log.d("LOG_TAG","MainViewModel: initCurrencies: IOEx: ${ex.message}")
            }catch (ex: HttpException){
                _currenciesLoaded.postValue(false)
                Log.d("LOG_TAG","MainViewModel: initCurrencies: HttpEx: ${ex.message}")
            }
        }

    }

}