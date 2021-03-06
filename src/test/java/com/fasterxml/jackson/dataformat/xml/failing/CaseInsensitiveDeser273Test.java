package com.fasterxml.jackson.dataformat.xml.failing;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlTestBase;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CaseInsensitiveDeser273Test extends XmlTestBase
{
    // [dataformat-xml#273]
    static class Depots273
    {
        public String command;
        public String taskId;

        @JacksonXmlElementWrapper(useWrapping = false)
        public List<Depot273> element;

        public void setElement(List<Depot273> l) {
            element = l;
//            System.err.println("setElement: "+l+" / "+l.size());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Depot273
    {
        public Depot273() {
//            System.err.println("<Depot273>");
        }
        @JacksonXmlProperty(isAttribute = true)
        public String number;

        @JacksonXmlProperty(isAttribute = true)
        public String name;
    }

    /*
    /********************************************************
    /* Test methods
    /********************************************************
     */

    private final ObjectMapper INSENSITIVE_MAPPER = newObjectMapper()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);

    // [dataformat-xml#273]
    public void testCaseInsensitiveComplex() throws Exception
    {
        final String DOC =
"<AcResponse Command='show depots' TaskId='1260'>\n"+
"  <Element Number='1' Name='accurev' Slice='1'\n"+
"exclusiveLocking='false' case='insensitive' locWidth='128'"+
" />\n"+
"  <Element Number='2' Name='second accurev' Slice='2'\n"+
"exclusiveLocking='false' case='insensitive' locWidth='128'\n"+
" />\n"+
"</AcResponse>"
        ;

        Depots273 result = INSENSITIVE_MAPPER.readValue(DOC, Depots273.class);
        assertNotNull(result);
        assertNotNull(result.element);
        assertEquals(2, result.element.size());
    }
}
