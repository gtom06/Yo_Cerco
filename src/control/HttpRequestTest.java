package control;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpRequestTest {

    @Test
    public void getTest() {
        String s = HttpRequest.get("https://apis.woptima.com/it/caps/FR/ripi");
        assertEquals("[\"03027\"]", s);

        String expected = "{\"name\":\"GPP\",\"surname\":\"TMS\",\"gender\":\"M\",\"day\":6,\"year\":1996,\"month\":11,\"birthday\":\"1996-11-06\",\"birthplace\":\"FROSINONE\",\"birthplaceProvincia\":\"FR\",\"cf\":\"TMSGPP96S06D810K\"}";
        s = HttpRequest.get("https://apis.woptima.com/cf/TMSGPP96S06D810K");
        assertEquals(expected, s);
    }
}