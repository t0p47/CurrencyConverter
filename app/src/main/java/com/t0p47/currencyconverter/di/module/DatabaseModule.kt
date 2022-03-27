package com.t0p47.currencyconverter.di.module

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.t0p47.currencyconverter.CurrencyConverterApp
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.room.AppDatabase
import com.t0p47.currencyconverter.room.dao.CurrencyDao
import com.t0p47.currencyconverter.room.dao.CurrencyModelDao
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton
import com.t0p47.currencyconverter.R

@Module
class DatabaseModule {

    lateinit var appDatabase: AppDatabase

    //TODO: Need ILS image
    var currencyInfoList = listOf(
        CurrencyModelEntity(null, CurrencyType.AED, R.mipmap.aed, false),
        CurrencyModelEntity(null, CurrencyType.AFN, R.mipmap.afn, false),
        CurrencyModelEntity(null, CurrencyType.ALL, R.mipmap.all, false),
        CurrencyModelEntity(null, CurrencyType.AMD, R.mipmap.amd, false),
        CurrencyModelEntity(null, CurrencyType.ANG, R.mipmap.ang, false),
        CurrencyModelEntity(null, CurrencyType.AOA, R.mipmap.aoa, false),
        CurrencyModelEntity(null, CurrencyType.ARS, R.mipmap.ars, false),
        CurrencyModelEntity(null, CurrencyType.AUD, R.mipmap.aud, false),
        CurrencyModelEntity(null, CurrencyType.AWG, R.mipmap.awg, false),
        CurrencyModelEntity(null, CurrencyType.AZN, R.mipmap.azn, false),
        CurrencyModelEntity(null, CurrencyType.BAM, R.mipmap.bam, false),
        CurrencyModelEntity(null, CurrencyType.BBD, R.mipmap.bbd, false),
        CurrencyModelEntity(null, CurrencyType.BDT, R.mipmap.bdt, false),
        CurrencyModelEntity(null, CurrencyType.BGN, R.mipmap.bgn, false),
        CurrencyModelEntity(null, CurrencyType.BHD, R.mipmap.bhd, false),
        CurrencyModelEntity(null, CurrencyType.BIF, R.mipmap.bif, false),
        CurrencyModelEntity(null, CurrencyType.BMD, R.mipmap.bmd, false),
        CurrencyModelEntity(null, CurrencyType.BND, R.mipmap.bnd, false),
        CurrencyModelEntity(null, CurrencyType.BOB, R.mipmap.bob, false),
        CurrencyModelEntity(null, CurrencyType.BRL, R.mipmap.brl, false),
        CurrencyModelEntity(null, CurrencyType.BSD, R.mipmap.bsd, false),
        CurrencyModelEntity(null, CurrencyType.BTC, R.mipmap.btc, false),
        CurrencyModelEntity(null, CurrencyType.BTN, R.mipmap.btn, false),
        CurrencyModelEntity(null, CurrencyType.BWP, R.mipmap.bwp, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.BYN, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.BYR, R.mipmap.byr, false),
        CurrencyModelEntity(null, CurrencyType.BZD, R.mipmap.bzd, false),
        CurrencyModelEntity(null, CurrencyType.CAD, R.mipmap.cad, false),
        CurrencyModelEntity(null, CurrencyType.CDF, R.mipmap.cdf, false),
        CurrencyModelEntity(null, CurrencyType.CHF, R.mipmap.chf, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.CLF, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.CLP, R.mipmap.clp, false),
        CurrencyModelEntity(null, CurrencyType.CNY, R.mipmap.cny, false),
        CurrencyModelEntity(null, CurrencyType.COP, R.mipmap.cop, false),
        CurrencyModelEntity(null, CurrencyType.CRC, R.mipmap.crc, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.CUC, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.CUP, R.mipmap.cup, false),
        CurrencyModelEntity(null, CurrencyType.CVE, R.mipmap.cve, false),
        CurrencyModelEntity(null, CurrencyType.CZK, R.mipmap.czk, false),
        CurrencyModelEntity(null, CurrencyType.DJF, R.mipmap.djf, false),
        CurrencyModelEntity(null, CurrencyType.DKK, R.mipmap.dkk, false),
        CurrencyModelEntity(null, CurrencyType.DOP, R.mipmap.dop, false),
        CurrencyModelEntity(null, CurrencyType.DZD, R.mipmap.dzd, false),
        CurrencyModelEntity(null, CurrencyType.EGP, R.mipmap.egp, false),
        CurrencyModelEntity(null, CurrencyType.ERN, R.mipmap.ern, false),
        CurrencyModelEntity(null, CurrencyType.ETB, R.mipmap.etb, false),
        CurrencyModelEntity(null, CurrencyType.EUR, R.mipmap.eur, false),
        CurrencyModelEntity(null, CurrencyType.FJD, R.mipmap.fjd, false),
        CurrencyModelEntity(null, CurrencyType.FKP, R.mipmap.fkp, false),
        CurrencyModelEntity(null, CurrencyType.GBP, R.mipmap.gbp, false),
        CurrencyModelEntity(null, CurrencyType.GEL, R.mipmap.gel, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.GGP, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.GHS, R.mipmap.ghs, false),
        CurrencyModelEntity(null, CurrencyType.GIP, R.mipmap.gip, false),
        CurrencyModelEntity(null, CurrencyType.GMD, R.mipmap.gmd, false),
        CurrencyModelEntity(null, CurrencyType.GNF, R.mipmap.gnf, false),
        CurrencyModelEntity(null, CurrencyType.GTQ, R.mipmap.gtq, false),
        CurrencyModelEntity(null, CurrencyType.GYD, R.mipmap.gyd, false),
        CurrencyModelEntity(null, CurrencyType.HKD, R.mipmap.hkd, false),
        CurrencyModelEntity(null, CurrencyType.HNL, R.mipmap.hnl, false),
        CurrencyModelEntity(null, CurrencyType.HRK, R.mipmap.hrk, false),
        CurrencyModelEntity(null, CurrencyType.HTG, R.mipmap.htg, false),
        CurrencyModelEntity(null, CurrencyType.HUF, R.mipmap.huf, false),
        CurrencyModelEntity(null, CurrencyType.IDR, R.mipmap.idr, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.ILS, R.mipmap.usd, false),
        CurrencyModelEntity(null, CurrencyType.IMP, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.INR, R.mipmap.inr, false),
        CurrencyModelEntity(null, CurrencyType.IQD, R.mipmap.iqd, false),
        CurrencyModelEntity(null, CurrencyType.IRR, R.mipmap.irr, false),
        CurrencyModelEntity(null, CurrencyType.ISK, R.mipmap.isk, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.JEP, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.JMD, R.mipmap.jmd, false),
        CurrencyModelEntity(null, CurrencyType.JOD, R.mipmap.jod, false),
        CurrencyModelEntity(null, CurrencyType.JPY, R.mipmap.jpy, false),
        CurrencyModelEntity(null, CurrencyType.KES, R.mipmap.kes, false),
        CurrencyModelEntity(null, CurrencyType.KGS, R.mipmap.kgs, false),
        CurrencyModelEntity(null, CurrencyType.KHR, R.mipmap.khr, false),
        CurrencyModelEntity(null, CurrencyType.KMF, R.mipmap.kmf, false),
        CurrencyModelEntity(null, CurrencyType.KPW, R.mipmap.kpw, false),
        CurrencyModelEntity(null, CurrencyType.KRW, R.mipmap.krw, false),
        CurrencyModelEntity(null, CurrencyType.KWD, R.mipmap.kwd, false),
        CurrencyModelEntity(null, CurrencyType.KYD, R.mipmap.kyd, false),
        CurrencyModelEntity(null, CurrencyType.KZT, R.mipmap.kzt, false),
        CurrencyModelEntity(null, CurrencyType.LAK, R.mipmap.lak, false),
        CurrencyModelEntity(null, CurrencyType.LBP, R.mipmap.lbp, false),
        CurrencyModelEntity(null, CurrencyType.LKR, R.mipmap.lkr, false),
        CurrencyModelEntity(null, CurrencyType.LRD, R.mipmap.lrd, false),
        CurrencyModelEntity(null, CurrencyType.LSL, R.mipmap.lsl, false),
        CurrencyModelEntity(null, CurrencyType.LTL, R.mipmap.ltl, false),
        CurrencyModelEntity(null, CurrencyType.LVL, R.mipmap.lvl, false),
        CurrencyModelEntity(null, CurrencyType.LYD, R.mipmap.lyd, false),
        CurrencyModelEntity(null, CurrencyType.MAD, R.mipmap.mad, false),
        CurrencyModelEntity(null, CurrencyType.MDL, R.mipmap.mdl, false),
        CurrencyModelEntity(null, CurrencyType.MGA, R.mipmap.mga, false),
        CurrencyModelEntity(null, CurrencyType.MKD, R.mipmap.mkd, false),
        CurrencyModelEntity(null, CurrencyType.MMK, R.mipmap.mmk, false),
        CurrencyModelEntity(null, CurrencyType.MNT, R.mipmap.mnt, false),
        CurrencyModelEntity(null, CurrencyType.MOP, R.mipmap.mop, false),
        CurrencyModelEntity(null, CurrencyType.MRO, R.mipmap.mro, false),
        CurrencyModelEntity(null, CurrencyType.MUR, R.mipmap.mur, false),
        CurrencyModelEntity(null, CurrencyType.MVR, R.mipmap.mvr, false),
        CurrencyModelEntity(null, CurrencyType.MWK, R.mipmap.mwk, false),
        CurrencyModelEntity(null, CurrencyType.MXN, R.mipmap.mxn, false),
        CurrencyModelEntity(null, CurrencyType.MYR, R.mipmap.myr, false),
        CurrencyModelEntity(null, CurrencyType.MZN, R.mipmap.mzn, false),
        CurrencyModelEntity(null, CurrencyType.NAD, R.mipmap.nad, false),
        CurrencyModelEntity(null, CurrencyType.NGN, R.mipmap.ngn, false),
        CurrencyModelEntity(null, CurrencyType.NIO, R.mipmap.nio, false),
        CurrencyModelEntity(null, CurrencyType.NOK, R.mipmap.nok, false),
        CurrencyModelEntity(null, CurrencyType.NPR, R.mipmap.npr, false),
        CurrencyModelEntity(null, CurrencyType.NZD, R.mipmap.nzd, false),
        CurrencyModelEntity(null, CurrencyType.OMR, R.mipmap.omr, false),
        CurrencyModelEntity(null, CurrencyType.PAB, R.mipmap.pab, false),
        CurrencyModelEntity(null, CurrencyType.PEN, R.mipmap.pen, false),
        CurrencyModelEntity(null, CurrencyType.PGK, R.mipmap.pgk, false),
        CurrencyModelEntity(null, CurrencyType.PHP, R.mipmap.php, false),
        CurrencyModelEntity(null, CurrencyType.PKR, R.mipmap.pkr, false),
        CurrencyModelEntity(null, CurrencyType.PLN, R.mipmap.pln, false),
        CurrencyModelEntity(null, CurrencyType.PYG, R.mipmap.pyg, false),
        CurrencyModelEntity(null, CurrencyType.QAR, R.mipmap.qar, false),
        CurrencyModelEntity(null, CurrencyType.RON, R.mipmap.ron, false),
        CurrencyModelEntity(null, CurrencyType.RSD, R.mipmap.rsd, false),
        CurrencyModelEntity(null, CurrencyType.RUB, R.mipmap.rub, false),
        CurrencyModelEntity(null, CurrencyType.RWF, R.mipmap.rwf, false),
        CurrencyModelEntity(null, CurrencyType.SAR, R.mipmap.sar, false),
        CurrencyModelEntity(null, CurrencyType.SBD, R.mipmap.sbd, false),
        CurrencyModelEntity(null, CurrencyType.SCR, R.mipmap.scr, false),
        CurrencyModelEntity(null, CurrencyType.SDG, R.mipmap.sdg, false),
        CurrencyModelEntity(null, CurrencyType.SEK, R.mipmap.sek, false),
        CurrencyModelEntity(null, CurrencyType.SGD, R.mipmap.sgd, false),
        CurrencyModelEntity(null, CurrencyType.SHP, R.mipmap.shp, false),
        CurrencyModelEntity(null, CurrencyType.SLL, R.mipmap.sll, false),
        CurrencyModelEntity(null, CurrencyType.SOS, R.mipmap.sos, false),
        CurrencyModelEntity(null, CurrencyType.SRD, R.mipmap.srd, false),
        CurrencyModelEntity(null, CurrencyType.STD, R.mipmap.std, false),
        CurrencyModelEntity(null, CurrencyType.SVC, R.mipmap.svc, false),
        CurrencyModelEntity(null, CurrencyType.SYP, R.mipmap.syp, false),
        CurrencyModelEntity(null, CurrencyType.SZL, R.mipmap.szl, false),
        CurrencyModelEntity(null, CurrencyType.THB, R.mipmap.thb, false),
        CurrencyModelEntity(null, CurrencyType.TJS, R.mipmap.tjs, false),
        CurrencyModelEntity(null, CurrencyType.TMT, R.mipmap.tmt, false),
        CurrencyModelEntity(null, CurrencyType.TND, R.mipmap.tnd, false),
        CurrencyModelEntity(null, CurrencyType.TOP, R.mipmap.top, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.TRY, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.TTD, R.mipmap.ttd, false),
        CurrencyModelEntity(null, CurrencyType.TWD, R.mipmap.twd, false),
        CurrencyModelEntity(null, CurrencyType.TZS, R.mipmap.tzs, false),
        CurrencyModelEntity(null, CurrencyType.UAH, R.mipmap.uah, false),
        CurrencyModelEntity(null, CurrencyType.UGX, R.mipmap.ugx, false),
        CurrencyModelEntity(null, CurrencyType.USD, R.mipmap.usd, false),
        CurrencyModelEntity(null, CurrencyType.UYU, R.mipmap.uyu, false),
        CurrencyModelEntity(null, CurrencyType.UZS, R.mipmap.uzs, false),
        CurrencyModelEntity(null, CurrencyType.VEF, R.mipmap.vef, false),
        CurrencyModelEntity(null, CurrencyType.VND, R.mipmap.vnd, false),
        CurrencyModelEntity(null, CurrencyType.VUV, R.mipmap.vuv, false),
        CurrencyModelEntity(null, CurrencyType.WST, R.mipmap.wst, false),
        CurrencyModelEntity(null, CurrencyType.XAF, R.mipmap.xaf, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.XAG, R.mipmap.usd, false),
        CurrencyModelEntity(null, CurrencyType.XAU, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.XCD, R.mipmap.xcd, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.XDR, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.XOF, R.mipmap.xof, false),
        CurrencyModelEntity(null, CurrencyType.XPF, R.mipmap.xpf, false),
        CurrencyModelEntity(null, CurrencyType.YER, R.mipmap.yer, false),
        CurrencyModelEntity(null, CurrencyType.ZAR, R.mipmap.zar, false),

        //TODO: Incorrect image
        CurrencyModelEntity(null, CurrencyType.ZMK, R.mipmap.usd, false),

        CurrencyModelEntity(null, CurrencyType.ZMW, R.mipmap.zmw, false),
        CurrencyModelEntity(null, CurrencyType.ZWL, R.mipmap.zwl, false)
    )

    @Provides
    @Singleton
    fun provideRoomDatabase(app: CurrencyConverterApp): AppDatabase {
        appDatabase = Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val scope = CoroutineScope(Dispatchers.IO)

                    scope.launch {
                        this@DatabaseModule.appDatabase.currencyModelDao().addNewCurrencies(currencyInfoList)
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()

        return appDatabase
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao{
        return appDatabase.currencyDao()
    }

    @Provides
    fun provideCurrencyModelDao(appDatabase: AppDatabase): CurrencyModelDao{
        return appDatabase.currencyModelDao()
    }

}