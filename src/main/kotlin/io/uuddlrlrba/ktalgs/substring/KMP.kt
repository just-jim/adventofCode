package io.uuddlrlrba.ktalgs.substring

class KMP(val pat: String) {
    private val R: Int = 256 // the radix
    private val dfa: Array<IntArray> // the KMP automoton

    init {
        // build DFA from pattern
        val m = pat.length
        dfa = Array(R) { IntArray(m) }
        dfa[pat[0].toString().toInt()][0] = 1
        var x = 0
        var j = 1
        while (j < m) {
            for (c in 0 until R) {
                dfa[c][j] = dfa[c][x] // Copy mismatch cases.
            }
            dfa[pat[j].toString().toInt()][j] = j + 1 // Set match case.
            x = dfa[pat[j].toString().toInt()][x] // Update restart state.
            j++
        }
    }

    fun search(txt: String): Int {
        // simulate operation of DFA on text
        val m = pat.length
        val n = txt.length
        var i: Int = 0
        var j: Int
        j = 0
        while (i < n && j < m) {
            j = dfa[txt[i].toString().toInt()][j]
            i++
        }
        if (j == m) return i - m // found
        return n // not found
    }
}
