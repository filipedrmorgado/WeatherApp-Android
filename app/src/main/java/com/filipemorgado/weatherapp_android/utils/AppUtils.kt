package com.filipemorgado.weatherapp_android.utils

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.bumptech.glide.Glide
import com.filipemorgado.weatherapp_android.R

object AppUtils {

    private var fastOutSlowIn: Interpolator? = null

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    fun getWeatherAnimation(weatherCode: Int): Int {
        return when (weatherCode) {
            1000 -> R.raw.clear_day
            1003 -> R.raw.few_clouds
            1006, 1030, 1069 -> R.raw.cloudy_weather
            1009 -> R.raw.broken_clouds
            1087, 1273, 1276, 1279, 1282 -> R.raw.storm_weather
            1135, 1147 -> R.raw.mostly_cloudy
            1063, 1072, 1150, 1153, 1168, 1183, 1186, 1189, 1198,
            1204, 1240, 1249 -> R.raw.rainy_weather
            1089, 1192, 1195, 1207, 1243, 1246, 1252 -> R.raw.shower_rain
            1066, 1114, 1117, 1171, 1201, 1210, 1213, 1216,
            1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> R.raw.snow_weather
            else -> R.raw.unknown
        }
    }

    /**
     * Set icon to imageView according to weather day details. Definitly not an AI generated code.
     *
     * @param context     instance of [Context]
     * @param imageView   instance of [android.widget.ImageView]
     * @param weatherCode weatherCode int weather status code
     */
    fun setWeatherIcon(context: Context, imageView: ImageView, weatherCode: Int) {
        when (weatherCode) {
            1000 -> Glide.with(context).load(R.drawable.ic_clear_day).into(imageView)
            1003 -> Glide.with(context).load(R.drawable.ic_few_clouds).into(imageView)
            1006, 1030, 1069 -> Glide.with(context).load(R.drawable.ic_cloudy_weather).into(imageView)
            1009 -> Glide.with(context).load(R.drawable.ic_broken_clouds).into(imageView)
            1087, 1273, 1276, 1279, 1282 -> Glide.with(context).load(R.drawable.ic_storm_weather).into(imageView)
            1135, 1147 -> Glide.with(context).load(R.drawable.ic_mostly_cloudy).into(imageView)
            1063, 1072, 1150, 1153, 1168, 1183, 1186, 1189, 1198,
            1204, 1240, 1249 -> Glide.with(context).load(R.drawable.ic_rainy_weather).into(imageView)
            1089, 1192, 1195, 1207, 1243, 1246, 1252 -> Glide.with(context).load(R.drawable.ic_shower_rain).into(imageView)
            1066, 1114, 1117, 1171, 1201, 1210, 1213, 1216,
            1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> Glide.with(context).load(R.drawable.ic_snow_weather).into(imageView)
            else -> R.raw.unknown
        }
    }


    val DAYS_OF_WEEK = arrayOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )

    val RECYCLER_COLORS = arrayOf(
        R.color.babyGreen,
        R.color.colorAccent,
        R.color.orange,
        R.color.babyBlue,
        R.color.red,
    )

    /**
     * Determine if the navigation bar will be on the bottom of the screen, based on logic in
     * PhoneWindowManager.
     */
    fun isNavBarOnBottom(context: Context): Boolean {
        val res = context.resources
        val cfg = context.resources.configuration
        val dm = res.displayMetrics
        val canMove = dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600
        return !canMove || dm.widthPixels < dm.heightPixels
    }

    fun getFastOutSlowInInterpolator(context: Context?): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn =
                AnimationUtils.loadInterpolator(
                    context,
                    android.R.interpolator.fast_out_slow_in
                )
        }
        return fastOutSlowIn
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        @IntRange(from = 0, to = 255) alpha: Int
    ): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }

}