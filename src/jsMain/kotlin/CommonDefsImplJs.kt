package org.intellij.markdown.html

import org.w3c.dom.url.URL

actual class URI actual constructor(str: String) {
    private val base = if (isGoodURL(str)) {
        str
    } else {
        fakeStart + str
    }

    actual fun resolve(str: String): URI {
        return URI(trimFake(URL(str, base).href))
    }


    override fun toString(): String {
        return trimFake(base)
    }

    companion object {
        // A tricky trick for distinguishing invalid relative and absolute paths.
        // Absolute ones will eat 'r001ez' on resolution, relative ones won't.
        private const val fakeHost = "fake://h"
        private const val fakeStart = "$fakeHost/r001ez"

        private fun isGoodURL(str: String): Boolean {
            return try {
                URL(str); true
            } catch (e: Throwable) {
                false
            }
        }

        private fun trimFake(str: String): String {
            return if (str.startsWith(fakeStart)) {
                str.substringAfter(fakeStart)
            } else {
                str.substringAfter(fakeHost)
            }
        }
    }
}

actual fun isWhitespace(char: Char): Boolean {
    return char == 0.toChar() || char.isWhitespace()
}

actual fun isPunctuation(char: Char): Boolean {
    // https://stackoverflow.com/questions/7576945/javascript-regular-expression-for-punctuation-international
    return char.toString().matches("[!\"#\\$%&'()*+,\\-./:;<=>?@[\\\\\\]^_`{|}~\\u00A1\\u00A7\\u00AB\\u00B6\\u00B7\\u00BB\\u00BF\\u037E\\u0387\\u055A-\\u055F\\u0589\\u058A\\u05BE\\u05C0\\u05C3\\u05C6\\u05F3\\u05F4\\u0609\\u060A\\u060C\\u060D\\u061B\\u061E\\u061F\\u066A-\\u066D\\u06D4\\u0700-\\u070D\\u07F7-\\u07F9\\u0830-\\u083E\\u085E\\u0964\\u0965\\u0970\\u0AF0\\u0DF4\\u0E4F\\u0E5A\\u0E5B\\u0F04-\\u0F12\\u0F14\\u0F3A-\\u0F3D\\u0F85\\u0FD0-\\u0FD4\\u0FD9\\u0FDA\\u104A-\\u104F\\u10FB\\u1360-\\u1368\\u1400\\u166D\\u166E\\u169B\\u169C\\u16EB-\\u16ED\\u1735\\u1736\\u17D4-\\u17D6\\u17D8-\\u17DA\\u1800-\\u180A\\u1944\\u1945\\u1A1E\\u1A1F\\u1AA0-\\u1AA6\\u1AA8-\\u1AAD\\u1B5A-\\u1B60\\u1BFC-\\u1BFF\\u1C3B-\\u1C3F\\u1C7E\\u1C7F\\u1CC0-\\u1CC7\\u1CD3\\u2010-\\u2027\\u2030-\\u2043\\u2045-\\u2051\\u2053-\\u205E\\u207D\\u207E\\u208D\\u208E\\u2329\\u232A\\u2768-\\u2775\\u27C5\\u27C6\\u27E6-\\u27EF\\u2983-\\u2998\\u29D8-\\u29DB\\u29FC\\u29FD\\u2CF9-\\u2CFC\\u2CFE\\u2CFF\\u2D70\\u2E00-\\u2E2E\\u2E30-\\u2E3B\\u3001-\\u3003\\u3008-\\u3011\\u3014-\\u301F\\u3030\\u303D\\u30A0\\u30FB\\uA4FE\\uA4FF\\uA60D-\\uA60F\\uA673\\uA67E\\uA6F2-\\uA6F7\\uA874-\\uA877\\uA8CE\\uA8CF\\uA8F8-\\uA8FA\\uA92E\\uA92F\\uA95F\\uA9C1-\\uA9CD\\uA9DE\\uA9DF\\uAA5C-\\uAA5F\\uAADE\\uAADF\\uAAF0\\uAAF1\\uABEB\\uFD3E\\uFD3F\\uFE10-\\uFE19\\uFE30-\\uFE52\\uFE54-\\uFE61\\uFE63\\uFE68\\uFE6A\\uFE6B\\uFF01-\\uFF03\\uFF05-\\uFF0A\\uFF0C-\\uFF0F\\uFF1A\\uFF1B\\uFF1F\\uFF20\\uFF3B-\\uFF3D\\uFF3F\\uFF5B\\uFF5D\\uFF5F-\\uFF65]")
}

actual fun urlEncode(str: String): String {
    return js("encodeURIComponent")(str) as String
}
