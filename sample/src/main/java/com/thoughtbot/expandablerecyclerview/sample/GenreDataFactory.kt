package com.thoughtbot.expandablerecyclerview.sample

import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckGenre
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckGenre
import java.util.*

object GenreDataFactory {
    @kotlin.jvm.JvmStatic
    fun makeGenres(): List<Genre> {
        return Arrays.asList(makeRockGenre(),
                makeJazzGenre(),
                makeClassicGenre(),
                makeSalsaGenre(),
                makeBluegrassGenre())
    }

    fun makeMultiCheckGenres(): List<MultiCheckGenre> {
        return Arrays.asList(makeMultiCheckRockGenre(),
                makeMultiCheckJazzGenre(),
                makeMultiCheckClassicGenre(),
                makeMultiCheckSalsaGenre(),
                makeMulitCheckBluegrassGenre())
    }

    fun makeSingleCheckGenres(): List<SingleCheckGenre> {
        return Arrays.asList(makeSingleCheckRockGenre(),
                makeSingleCheckJazzGenre(),
                makeSingleCheckClassicGenre(),
                makeSingleCheckSalsaGenre(),
                makeSingleCheckBluegrassGenre())
    }

    fun makeRockGenre(): Genre {
        return Genre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar)
    }

    fun makeMultiCheckRockGenre(): MultiCheckGenre {
        return MultiCheckGenre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar)
    }

    fun makeSingleCheckRockGenre(): SingleCheckGenre {
        return SingleCheckGenre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar)
    }

    fun makeRockArtists(): List<Artist?> {
        val queen = Artist("Queen", true)
        val styx = Artist("Styx", false)
        val reoSpeedwagon = Artist("REO Speedwagon", false)
        val boston = Artist("Boston", true)
        return Arrays.asList(queen, styx, reoSpeedwagon, boston)
    }

    fun makeJazzGenre(): Genre {
        return Genre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone)
    }

    fun makeMultiCheckJazzGenre(): MultiCheckGenre {
        return MultiCheckGenre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone)
    }

    fun makeSingleCheckJazzGenre(): SingleCheckGenre {
        return SingleCheckGenre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone)
    }

    fun makeJazzArtists(): List<Artist?> {
        val milesDavis = Artist("Miles Davis", true)
        val ellaFitzgerald = Artist("Ella Fitzgerald", true)
        val billieHoliday = Artist("Billie Holiday", false)
        return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday)
    }

    fun makeClassicGenre(): Genre {
        return Genre("Classic", makeClassicArtists(), R.drawable.ic_violin)
    }

    fun makeMultiCheckClassicGenre(): MultiCheckGenre {
        return MultiCheckGenre("Classic", makeClassicArtists(), R.drawable.ic_violin)
    }

    fun makeSingleCheckClassicGenre(): SingleCheckGenre {
        return SingleCheckGenre("Classic", makeClassicArtists(), R.drawable.ic_violin)
    }

    fun makeClassicArtists(): List<Artist?> {
        val beethoven = Artist("Ludwig van Beethoven", false)
        val bach = Artist("Johann Sebastian Bach", true)
        val brahms = Artist("Johannes Brahms", false)
        val puccini = Artist("Giacomo Puccini", false)
        return Arrays.asList(beethoven, bach, brahms, puccini)
    }

    fun makeSalsaGenre(): Genre {
        return Genre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas)
    }

    fun makeMultiCheckSalsaGenre(): MultiCheckGenre {
        return MultiCheckGenre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas)
    }

    fun makeSingleCheckSalsaGenre(): SingleCheckGenre {
        return SingleCheckGenre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas)
    }

    fun makeSalsaArtists(): List<Artist?> {
        val hectorLavoe = Artist("Hector Lavoe", true)
        val celiaCruz = Artist("Celia Cruz", false)
        val willieColon = Artist("Willie Colon", false)
        val marcAnthony = Artist("Marc Anthony", false)
        return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony)
    }

    fun makeBluegrassGenre(): Genre {
        return Genre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo)
    }

    fun makeMulitCheckBluegrassGenre(): MultiCheckGenre {
        return MultiCheckGenre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo)
    }

    fun makeSingleCheckBluegrassGenre(): SingleCheckGenre {
        return SingleCheckGenre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo)
    }

    fun makeBluegrassArtists(): List<Artist?> {
        val billMonroe = Artist("Bill Monroe", false)
        val earlScruggs = Artist("Earl Scruggs", false)
        val osborneBrothers = Artist("Osborne Brothers", true)
        val johnHartford = Artist("John Hartford", false)
        return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford)
    }
}