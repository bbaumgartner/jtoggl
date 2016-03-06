/*
 * jtoggl - Java Wrapper for Toggl REST API https://www.toggl.com/public/api
 *
 * Copyright (C) 2011 by simas GmbH, Moosentli 7, 3235 Erlach, Switzerland
 * http://www.simas.ch
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.simas.jtoggl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Simon Martinelli
 */
public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private DateUtil() {
    }

    public static Date convertStringToDate(String dateString) {
        if (dateString == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        int timezoneOffset = date.getTimezoneOffset();
        int hour = Math.abs(timezoneOffset / 60);
        int min = Math.abs(timezoneOffset % 60);
        String dateTime = sdf.format(date);
        String timeOffset = (timezoneOffset <= 0 ? "+" : "-") + (hour < 10 ? "0" : "") + hour + ":" + (min < 10 ? "0" : "") + min;
        return dateTime + timeOffset;
    }

    public static String formatDate(Calendar value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(value.getTimeZone());
        String result = simpleDateFormat.format(value.getTime());
        int o = value.getTimeZone().getRawOffset();
        if (o == 0) {
            result = result + 'Z';
        } else {
            o = o / 1000 / 60;
            int h = Math.abs(o / 60);
            int m = Math.abs(o % 60);

            result = String.format("%s%s%02d:%02d", result, o > 0 ? '+' : '-', h, m);
        }
        return result;
    }
}
