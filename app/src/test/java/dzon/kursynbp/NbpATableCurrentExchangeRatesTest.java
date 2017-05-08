package dzon.kursynbp;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JSONArray.class)
public class NbpATableCurrentExchangeRatesTest {
    @Mock
    InputStream mockIs;
    @Mock
    BufferedReader mockBr;
    @Mock
    JSONArray mockJSONArray;
    @Mock
    JSONObject mockJSONObject;

    private final String jsonDateField = "effectiveDate";
    private final String jsonNameField = "currency";
    private final String jsonCodeField = "code";
    private final String jsonExchangeRateField = "mid";

    private final String dateString = "2017-01-01";
    private final double rate = 2.15;
    private final String name = "lew (Bułgaria)";
    private final String code = "BGN";

    @Before
    public void setup() throws IOException, JSONException {
        when(mockBr.read()).thenReturn(-1);

        when(mockJSONArray.getJSONObject(0)).thenReturn(mockJSONObject);
        when(mockJSONObject.getString(jsonDateField)).thenReturn(dateString);

        when(mockJSONArray.length()).thenReturn(1);
        when(mockJSONArray.getJSONObject(0)).thenReturn(mockJSONObject);

        when(mockJSONObject.getDouble(jsonExchangeRateField)).thenReturn(rate);
        when(mockJSONObject.getString(jsonNameField)).thenReturn(name);
        when(mockJSONObject.getString(jsonCodeField)).thenReturn(code);
    }

    @Test
    public void test() throws IOException {
        NbpATableCurrentExchangeRates nbpATableCurrentExchangeRates = NbpATableCurrentExchangeRates.getInstance();
        List<Currency> currencies =  nbpATableCurrentExchangeRates.getCurrentRates();

        assertEquals(1, currencies.size());
    }
}
