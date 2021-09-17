package com.example.mytv.utils

import com.example.mytv.R

object WeatherUtils {
    fun getLottieResourceIdForWeatherCondition(weatherId: Int): String {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId in 200..232) {
//            storm
            return "https://assets2.lottiefiles.com/temp/lf20_XkF78Y.json"
        } else if (weatherId in 300..321) {
//            light rain
            return "https://assets7.lottiefiles.com/packages/lf20_0pbhvyhq.json"
        } else if (weatherId in 500..504) {
//             R.drawable.ic_rain
            return "https://assets9.lottiefiles.com/private_files/lf30_orqfuyox.json"
        } else if (weatherId == 511) {
//             R.drawable.ic_snow
            return "https://assets1.lottiefiles.com/private_files/lf30_0tyvusxj.json"
        } else if (weatherId in 520..531) {
//             R.drawable.ic_rain
            return "https://assets9.lottiefiles.com/private_files/lf30_orqfuyox.json"
        } else if (weatherId in 600..622) {
//             R.drawable.ic_snow
            return "https://assets1.lottiefiles.com/private_files/lf30_0tyvusxj.json"
        } else if (weatherId in 701..761) {
//             R.drawable.ic_fog
            return "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json"
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
//             R.drawable.ic_storm
            return "https://assets2.lottiefiles.com/temp/lf20_XkF78Y.json"
        } else if (weatherId == 800) {
//             R.drawable.ic_clear
            return "https://assets7.lottiefiles.com/private_files/lf30_rsattbhn.json"
        } else if (weatherId == 801) {
//             R.drawable.ic_light_clouds
            return "https://assets7.lottiefiles.com/temp/lf20_dgjK9i.json"
        } else if (weatherId in 802..804) {
//             R.drawable.ic_cloudy
            return "https://assets9.lottiefiles.com/temp/lf20_ZCwXJD.json"
        } else if (weatherId in 900..906) {
//             R.drawable.ic_storm
            return "https://assets2.lottiefiles.com/temp/lf20_XkF78Y.json"
        } else if (weatherId in 958..962) {
//             R.drawable.ic_storm
            return "https://assets2.lottiefiles.com/temp/lf20_XkF78Y.json"
        } else if (weatherId in 951..957) {
            return "https://assets7.lottiefiles.com/private_files/lf30_rsattbhn.json"
        }
        return "https://assets5.lottiefiles.com/private_files/lf30_jmgekfqg.json"
    }


    fun  getSmallArtResourceIdForWeatherCondition(weatherId: Int): Int {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId in 200..232) {
//            storm
            return R.drawable.ic_storm;
        } else if (weatherId in 300..321) {
//            light rain
            return R.drawable.ic_rain
        } else if (weatherId in 500..504) {

            return R.drawable.ic_rain
        } else if (weatherId == 511) {
            return R.drawable.ic_snow
        } else if (weatherId in 520..531) {
            return R.drawable.ic_rain
        } else if (weatherId in 600..622) {
            return R.drawable.ic_snow
        } else if (weatherId in 701..761) {
            return R.drawable.ic_fog
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm
        } else if (weatherId == 800) {
            return R.drawable.ic_clear
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds
        } else if (weatherId in 802..804) {
            return R.drawable.ic_cloudy
        } else if (weatherId in 900..906) {
            return R.drawable.ic_storm
        } else if (weatherId in 958..962) {
            return R.drawable.ic_storm
        } else if (weatherId in 951..957) {
            return R.drawable.ic_clear
        }
        return return R.drawable.ic_clear

    }

}
