package ai.legendary;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReformatterTest {
    Reformatter rf = null;
    @Before
    public void init() {
        rf = new Reformatter();
    }
    @Test
    public void offeronewhodoestest() {
        String expected = "offerer";
        rf.addWord("offer", "verb");
        String actual = rf.oneWhoDoes();
        assertEquals(expected, actual);
    }
    @Test
    public void discussonewhodoestest() {
        String expected = "discussant or discusser";
        rf.addWord("discuss", "verb");
        String actual = rf.oneWhoDoes();
        assertEquals(expected, actual);
    }
    @Test
    public void discussresulteventstatetest() {
        String expected = "discussion";
        rf.addWord("discuss", "verb");
        String actual = rf.resultEventState();
        assertEquals(expected, actual);
    }
    @Test
    public void demandonewhodoestest() {
        String expected = "demandant or demander";
        rf.addWord("demand", "verb");
        String actual = rf.oneWhoDoes();
        assertEquals(expected, actual);
    }
    @Test
    public void proposeonewhodoestest() {
        String expected = "proposer";
        rf.addWord("propose", "verb");
        String actual = rf.oneWhoDoes();
        assertEquals(expected, actual);
    }
    @Test
    public void proposeresulteventstatetest() {
        String expected = "proposal or proposition";
        rf.addWord("propose", "verb");
        String actual = rf.resultEventState();
        assertEquals(expected, actual);
    }
    @Test
    public void warnresulteventstatetest() {
        String expected = "warning";
        rf.addWord("warn", "verb");
        String actual = rf.resultEventState();
        assertEquals(expected, actual);
    }
    @Test
    public void assertresulteventstatetest() {
        String expected = "assertion";
        rf.addWord("assert", "verb");
        String actual = rf.resultEventState();
        assertEquals(expected, actual);
    }
    @Test
    public void suggestresulteventstatetest() {
        String expected = "suggestion";
        rf.addWord("suggest", "verb");
        String actual = rf.resultEventState();
        assertEquals(expected, actual);
    }
}
