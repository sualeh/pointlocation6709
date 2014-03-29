package us.fatehi.pointlocation6709.test;


import static org.junit.Assert.assertEquals;
import static us.fatehi.pointlocation6709.format.PointLocationFormatter.formatPointLocation;

import org.junit.Before;
import org.junit.Test;

import us.fatehi.pointlocation6709.PointLocation;
import us.fatehi.pointlocation6709.format.FormatterException;
import us.fatehi.pointlocation6709.format.PointLocationFormatType;
import us.fatehi.pointlocation6709.parse.ParserException;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestFormatter
{

  private final PointLocation[] pointLocation = new PointLocation[3];

  @Before
  public void _setup()
    throws ParserException
  {
    pointLocation[0] = PointLocationParser
      .parsePointLocation("+401213.123-0750015.123/");
    pointLocation[1] = PointLocationParser
      .parsePointLocation("+401213.123-075+23.23/");
    pointLocation[2] = PointLocationParser
      .parsePointLocation("+401213.123-075-13.13CRScustom/");
  }

  @Test
  public void decimalFormat()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.DECIMAL;
    assertEquals("+40.20365-075.00420/",
                 formatPointLocation(pointLocation[0], format));
    assertEquals("+40.20365-075.00000+23.23000/",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("+40.20365-075.00000-13.13000CRScustom/",
                 formatPointLocation(pointLocation[2], format));
  }

  @Test
  public void humanLong()
  {
    assertEquals("40°12'13\"N 75°00'15\"W", pointLocation[0].toString());
    assertEquals("40°12'13\"N 75°00'00\"W 23.230", pointLocation[1].toString());
    assertEquals("40°12'13\"N 75°00'00\"W -13.130", pointLocation[2].toString());
  }

  @Test
  public void humanMedium()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.HUMAN_MEDIUM;
    assertEquals("40°12'N 75°00'W",
                 formatPointLocation(pointLocation[0], format));
    assertEquals("40°12'N 75°00'W +23.23000",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("40°12'N 75°00'W -13.13000",
                 formatPointLocation(pointLocation[2], format));
  }

  @Test
  public void humanShort()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.HUMAN_SHORT;
    assertEquals("40°N 75°W", formatPointLocation(pointLocation[0], format));
    assertEquals("40°N 75°W +23.23000",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("40°N 75°W -13.13000",
                 formatPointLocation(pointLocation[2], format));
  }

  @Test
  public void longFormat()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.LONG;
    assertEquals("+401213-0750015/",
                 formatPointLocation(pointLocation[0], format));
    assertEquals("+401213-0750000+23.23000/",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("+401213-0750000-13.13000CRScustom/",
                 formatPointLocation(pointLocation[2], format));
  }

  @Test
  public void mediumFormat()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.MEDIUM;
    assertEquals("+4012-07500/", formatPointLocation(pointLocation[0], format));
    assertEquals("+4012-07500+23.23000/",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("+4012-07500-13.13000CRScustom/",
                 formatPointLocation(pointLocation[2], format));
  }

  @Test
  public void shortFormat()
    throws FormatterException
  {
    final PointLocationFormatType format = PointLocationFormatType.SHORT;
    assertEquals("+40-075/", formatPointLocation(pointLocation[0], format));
    assertEquals("+40-075+23.23000/",
                 formatPointLocation(pointLocation[1], format));
    assertEquals("+40-075-13.13000CRScustom/",
                 formatPointLocation(pointLocation[2], format));
  }

}
