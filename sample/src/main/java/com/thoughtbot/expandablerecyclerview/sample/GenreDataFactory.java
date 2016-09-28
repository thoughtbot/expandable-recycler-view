package com.thoughtbot.expandablerecyclerview.sample;

import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckGenre;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckGenre;
import java.util.Arrays;
import java.util.List;

public class GenreDataFactory {

  public static List<Genre> makeGenres() {
    return Arrays.asList(makeRockGenre(),
        makeJazzGenre(),
        makeClassicGenre(),
        makeSalsaGenre(),
        makeBluegrassGenre());
  }

  public static List<MultiCheckGenre> makeMultiCheckGenres() {
    return Arrays.asList(makeMultiCheckRockGenre(),
        makeMultiCheckJazzGenre(),
        makeMultiCheckClassicGenre(),
        makeMultiCheckSalsaGenre(),
        makeMulitCheckBluegrassGenre());
  }

  public static List<SingleCheckGenre> makeSingleCheckGenres() {
    return Arrays.asList(makeSingleCheckRockGenre(),
        makeSingleCheckJazzGenre(),
        makeSingleCheckClassicGenre(),
        makeSingleCheckSalsaGenre(),
        makeSingleCheckBluegrassGenre());
  }

  public static Genre makeRockGenre() {
    return new Genre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar);
  }

  public static MultiCheckGenre makeMultiCheckRockGenre() {
    return new MultiCheckGenre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar);
  }

  public static SingleCheckGenre makeSingleCheckRockGenre() {
    return new SingleCheckGenre("Rock", makeRockArtists(), R.drawable.ic_electric_guitar);
  }

  public static List<Artist> makeRockArtists() {
    Artist queen = new Artist("Queen", true);
    Artist styx = new Artist("Styx", false);
    Artist reoSpeedwagon = new Artist("REO Speedwagon", false);
    Artist boston = new Artist("Boston", true);

    return Arrays.asList(queen, styx, reoSpeedwagon, boston);
  }

  public static Genre makeJazzGenre() {
    return new Genre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone);
  }

  public static MultiCheckGenre makeMultiCheckJazzGenre() {
    return new MultiCheckGenre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone);
  }

  public static SingleCheckGenre makeSingleCheckJazzGenre() {
    return new SingleCheckGenre("Jazz", makeJazzArtists(), R.drawable.ic_saxaphone);
  }

  public static List<Artist> makeJazzArtists() {
    Artist milesDavis = new Artist("Miles Davis", true);
    Artist ellaFitzgerald = new Artist("Ella Fitzgerald", true);
    Artist billieHoliday = new Artist("Billie Holiday", false);

    return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
  }

  public static Genre makeClassicGenre() {
    return new Genre("Classic", makeClassicArtists(), R.drawable.ic_violin);
  }

  public static MultiCheckGenre makeMultiCheckClassicGenre() {
    return new MultiCheckGenre("Classic", makeClassicArtists(), R.drawable.ic_violin);
  }

  public static SingleCheckGenre makeSingleCheckClassicGenre() {
    return new SingleCheckGenre("Classic", makeClassicArtists(), R.drawable.ic_violin);
  }

  public static List<Artist> makeClassicArtists() {
    Artist beethoven = new Artist("Ludwig van Beethoven", false);
    Artist bach = new Artist("Johann Sebastian Bach", true);
    Artist brahms = new Artist("Johannes Brahms", false);
    Artist puccini = new Artist("Giacomo Puccini", false);

    return Arrays.asList(beethoven, bach, brahms, puccini);
  }

  public static Genre makeSalsaGenre() {
    return new Genre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas);
  }

  public static MultiCheckGenre makeMultiCheckSalsaGenre() {
    return new MultiCheckGenre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas);
  }

  public static SingleCheckGenre makeSingleCheckSalsaGenre() {
    return new SingleCheckGenre("Salsa", makeSalsaArtists(), R.drawable.ic_maracas);
  }

  public static List<Artist> makeSalsaArtists() {
    Artist hectorLavoe = new Artist("Hector Lavoe", true);
    Artist celiaCruz = new Artist("Celia Cruz", false);
    Artist willieColon = new Artist("Willie Colon", false);
    Artist marcAnthony = new Artist("Marc Anthony", false);

    return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony);
  }

  public static Genre makeBluegrassGenre() {
    return new Genre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo);
  }

  public static MultiCheckGenre makeMulitCheckBluegrassGenre() {
    return new MultiCheckGenre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo);
  }

  public static SingleCheckGenre makeSingleCheckBluegrassGenre() {
    return new SingleCheckGenre("Bluegrass", makeBluegrassArtists(), R.drawable.ic_banjo);
  }

  public static List<Artist> makeBluegrassArtists() {
    Artist billMonroe = new Artist("Bill Monroe", false);
    Artist earlScruggs = new Artist("Earl Scruggs", false);
    Artist osborneBrothers = new Artist("Osborne Brothers", true);
    Artist johnHartford = new Artist("John Hartford", false);

    return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford);
  }

}

