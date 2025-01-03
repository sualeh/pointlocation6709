/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.format;

import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Angle.AngleFormat;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;
import us.fatehi.pointlocation6709.PointLocation;

/**
 * Formats point locations to strings.
 *
 * @author Sualeh Fatehi
 */
public final class PointLocationFormatter {

  /**
   * Formats a latitude as an ISO 6709 string.
   *
   * @param latitude Latitude to format
   * @param formatType Format type
   * @return Formatted string
   * @throws FormatterException On an exception
   */
  public static String formatLatitude(
      final Latitude latitude, final PointLocationFormatType formatType) throws FormatterException {
    if (latitude == null) {
      throw new FormatterException("No point location provided");
    }
    if (formatType == null) {
      throw new FormatterException("No format type provided");
    }

    final String formatted;
    switch (formatType) {
      case HUMAN_LONG:
        formatted = latitude.toString();
        break;
      case HUMAN_MEDIUM:
        formatted = formatLatitudeHumanMedium(latitude);
        break;
      case LONG:
        formatted = formatLatitudeLong(latitude);
        break;
      case MEDIUM:
        formatted = formatLatitudeMedium(latitude);
        break;
      case SHORT:
        formatted = formatLatitudeShort(latitude);
        break;
      case DECIMAL:
        formatted = formatLatitudeWithDecimals(latitude);
        break;
      default:
        throw new FormatterException("Unsupported format type");
    }
    return formatted;
  }

  /**
   * Formats a longitude as an ISO 6709 string.
   *
   * @param longitude Longitude to format
   * @param formatType Format type
   * @return Formatted string
   * @throws FormatterException On an exception
   */
  public static String formatLongitude(
      final Longitude longitude, final PointLocationFormatType formatType)
      throws FormatterException {
    if (longitude == null) {
      throw new FormatterException("No point location provided");
    }
    if (formatType == null) {
      throw new FormatterException("No format type provided");
    }

    final String formatted;
    switch (formatType) {
      case HUMAN_LONG:
        formatted = longitude.toString();
        break;
      case HUMAN_MEDIUM:
        formatted = formatLongitudeHumanMedium(longitude);
        break;
      case LONG:
        formatted = formatLongitudeLong(longitude);
        break;
      case MEDIUM:
        formatted = formatLongitudeMedium(longitude);
        break;
      case SHORT:
        formatted = formatLongitudeShort(longitude);
        break;
      case DECIMAL:
        formatted = formatLongitudeWithDecimals(longitude);
        break;
      default:
        throw new FormatterException("Unsupported format type");
    }
    return formatted;
  }

  /**
   * Formats a point location as an ISO 6709 or human readable string.
   *
   * @param pointLocation Point location to format
   * @param formatType Format type
   * @return Formatted string
   * @throws FormatterException On an exception
   */
  public static String formatPointLocation(
      final PointLocation pointLocation, final PointLocationFormatType formatType)
      throws FormatterException {
    if (pointLocation == null) {
      throw new FormatterException("No point location provided");
    }
    if (formatType == null) {
      throw new FormatterException("No format type provided");
    }

    final String formatted;
    switch (formatType) {
      case HUMAN_LONG:
        formatted = pointLocation.toString();
        break;
      case HUMAN_MEDIUM:
        formatted = formatHumanMedium(pointLocation);
        break;
      case HUMAN_SHORT:
        formatted = formatHumanShort(pointLocation);
        break;
      case LONG:
        formatted = formatISO6709Long(pointLocation);
        break;
      case MEDIUM:
        formatted = formatISO6709Medium(pointLocation);
        break;
      case SHORT:
        formatted = formatISO6709Short(pointLocation);
        break;
      case DECIMAL:
        formatted = formatISO6709WithDecimals(pointLocation);
        break;
      default:
        throw new FormatterException("Unsupported format type");
    }

    return formatted;
  }

  private static String formatAltitudeWithSign(final double value) {
    if (Math.abs(value) > 1E-6) {
      return (Math.signum(value) < 0 ? "-" : "+") + getNumberFormat(1).format(Math.abs(value));
    } else {
      return "";
    }
  }

  private static String formatCoordinateReferenceSystemIdentifier(final String crs) {
    if (StringUtils.isNotBlank(crs)) {
      return "CRS" + crs;
    } else {
      return "";
    }
  }

  private static String formatDecimalMinutesString(final Angle angle) {
    final double degrees = Math.abs(angle.getDegrees());
    final double absMinutes = degrees - (int) degrees;
    return getNumberFormat(0).format(absMinutes);
  }

  /**
   * Formats a point location as an human readable string.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatHumanMedium(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string =
        formatLatitudeHumanMedium(latitude) + " " + formatLongitudeHumanMedium(longitude);
    final double altitude = pointLocation.getAltitude();
    if (altitude != 0) {
      string = string + " " + formatAltitudeWithSign(altitude);
    }
    return string;
  }

  /**
   * Formats a point location as an human readable string.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatHumanShort(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string = formatLatitudeHumanShort(latitude) + " " + formatLongitudeHumanShort(longitude);
    final double altitude = pointLocation.getAltitude();
    if (altitude != 0) {
      string = string + " " + formatAltitudeWithSign(altitude);
    }
    return string;
  }

  private static String formatIntegerDegreesString(final Angle angle) {
    String sign = angle.getRadians() < 0 ? "-" : "+";
    final int intDegrees = Math.abs(angle.getField(Angle.Field.DEGREES));

    final int fieldlength;
    if (angle instanceof Latitude) {
      fieldlength = 2;
    } else if (angle instanceof Longitude) {
      // According to the ISO6709 standard,
      // the 180th meridian is negative
      if (intDegrees == 180) {
        sign = "-";
      }
      fieldlength = 3;
    } else {
      fieldlength = 0;
    }

    final String degrees = getIntegerFormat(fieldlength).format(intDegrees);

    return sign + degrees;
  }

  /**
   * Formats a point location as an ISO 6709 string.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatISO6709Long(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string = formatLatitudeLong(latitude) + formatLongitudeLong(longitude);
    final double altitude = pointLocation.getAltitude();
    string = string + formatAltitudeWithSign(altitude);
    final String crs = pointLocation.getCoordinateReferenceSystemIdentifier();
    string = string + formatCoordinateReferenceSystemIdentifier(crs);
    return string + "/";
  }

  /**
   * Formats a point location as an ISO 6709 string.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatISO6709Medium(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string = formatLatitudeMedium(latitude) + formatLongitudeMedium(longitude);
    final double altitude = pointLocation.getAltitude();
    string = string + formatAltitudeWithSign(altitude);
    final String crs = pointLocation.getCoordinateReferenceSystemIdentifier();
    string = string + formatCoordinateReferenceSystemIdentifier(crs);
    return string + "/";
  }

  /**
   * Formats a point location as an ISO 6709 string.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatISO6709Short(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string = formatLatitudeShort(latitude) + formatLongitudeShort(longitude);
    final double altitude = pointLocation.getAltitude();
    string = string + formatAltitudeWithSign(altitude);
    final String crs = pointLocation.getCoordinateReferenceSystemIdentifier();
    string = string + formatCoordinateReferenceSystemIdentifier(crs);
    return string + "/";
  }

  /**
   * Formats a point location as an ISO 6709 string, using decimals.
   *
   * @param pointLocation Point location to format
   * @return Formatted string
   */
  private static String formatISO6709WithDecimals(final PointLocation pointLocation) {
    final Latitude latitude = pointLocation.getLatitude();
    final Longitude longitude = pointLocation.getLongitude();
    String string = formatLatitudeWithDecimals(latitude) + formatLongitudeWithDecimals(longitude);
    final double altitude = pointLocation.getAltitude();
    string = string + formatAltitudeWithSign(altitude);
    final String crs = pointLocation.getCoordinateReferenceSystemIdentifier();
    string = string + formatCoordinateReferenceSystemIdentifier(crs);
    return string + "/";
  }

  /**
   * Formats a latitude as an human readable string.
   *
   * @param latitude Latitude to format
   * @return Formatted string
   */
  private static String formatLatitudeHumanMedium(final Latitude latitude) {
    return latitude.format(AngleFormat.MEDIUM);
  }

  /**
   * Formats a latitude as an human readable string.
   *
   * @param latitude Latitude to format
   * @return Formatted string
   */
  private static String formatLatitudeHumanShort(final Latitude latitude) {
    return latitude.format(AngleFormat.SHORT);
  }

  private static String formatLatitudeLong(final Latitude latitude) {
    final String string =
        formatLatitudeShort(latitude) + formatSexagesimalMinutesStringLong(latitude);
    return string;
  }

  private static String formatLatitudeMedium(final Latitude latitude) {
    final String string =
        formatLatitudeShort(latitude) + formatSexagesimalMinutesStringMedium(latitude);
    return string;
  }

  private static String formatLatitudeShort(final Latitude latitude) {
    return formatIntegerDegreesString(latitude);
  }

  private static String formatLatitudeWithDecimals(final Latitude latitude) {
    final String string = formatLatitudeShort(latitude) + formatDecimalMinutesString(latitude);
    return string;
  }

  /**
   * Formats a longitude as an human readable string.
   *
   * @param longitude Longitude to format
   * @return Formatted string
   */
  private static String formatLongitudeHumanMedium(final Longitude longitude) {
    return longitude.format(AngleFormat.MEDIUM);
  }

  /**
   * Formats a longitude as an human readable string.
   *
   * @param longitude Longitude to format
   * @return Formatted string
   */
  private static String formatLongitudeHumanShort(final Longitude longitude) {
    return longitude.format(AngleFormat.SHORT);
  }

  private static String formatLongitudeLong(final Longitude longitude) {
    final String string =
        formatLongitudeShort(longitude) + formatSexagesimalMinutesStringLong(longitude);
    return string;
  }

  private static String formatLongitudeMedium(final Longitude longitude) {
    final String string =
        formatLongitudeShort(longitude) + formatSexagesimalMinutesStringMedium(longitude);
    return string;
  }

  private static String formatLongitudeShort(final Longitude longitude) {
    return formatIntegerDegreesString(longitude);
  }

  private static String formatLongitudeWithDecimals(final Longitude longitude) {
    final String string =
        formatIntegerDegreesString(longitude) + formatDecimalMinutesString(longitude);
    return string;
  }

  private static String formatSexagesimalMinutesStringLong(final Angle angle) {
    final int absMinutes = Math.abs(angle.getField(Angle.Field.MINUTES));
    final int absSeconds = Math.abs(angle.getField(Angle.Field.SECONDS));

    final NumberFormat integerFormat = getIntegerFormat(2);
    return integerFormat.format(absMinutes) + integerFormat.format(absSeconds);
  }

  private static String formatSexagesimalMinutesStringMedium(final Angle angle) {
    int absMinutes = Math.abs(angle.getField(Angle.Field.MINUTES));
    final int absSeconds = Math.abs(angle.getField(Angle.Field.SECONDS));
    if (absMinutes < 59 && absSeconds >= 30) {
      absMinutes = absMinutes + 1;
    }

    final NumberFormat integerFormat = getIntegerFormat(2);
    return integerFormat.format(absMinutes);
  }

  private static NumberFormat getIntegerFormat(final int integerDigits) {
    final NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    numberFormat.setMinimumIntegerDigits(integerDigits);
    return numberFormat;
  }

  private static NumberFormat getNumberFormat(final int integerDigits) {
    final NumberFormat numberFormat = NumberFormat.getInstance();
    numberFormat.setMinimumIntegerDigits(integerDigits);
    numberFormat.setMinimumFractionDigits(5);
    numberFormat.setMaximumFractionDigits(5);
    numberFormat.setGroupingUsed(false);
    return numberFormat;
  }

  private PointLocationFormatter() {
    // Prevent instantiation
  }
}
