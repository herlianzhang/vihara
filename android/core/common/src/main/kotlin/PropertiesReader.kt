package pro.herlian.vihara.core.common

import android.util.Log
import java.util.Properties

private const val CONFIG = "secrets.properties"
object PropertiesReader {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader?.getResourceAsStream(CONFIG)
        properties.load(file)
        assert(file != null)
        Log.d("Masuk", properties.getProperty("BACKEND_URL"))
    }
}