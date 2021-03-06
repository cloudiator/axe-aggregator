/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.uniulm.omi.cloudiator.axe.aggregator;

import com.google.gson.internal.LazilyParsedNumber;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.frontend.FrontendCommunicator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MonitorInstance;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 23.07.2015.
 */
public class Utils {
    public static long timeToMilliseconds(Object unit, long interval) {
        switch (unit.toString()) {
            case "SECONDS":
                return interval * 1000L;
            case "MINUTES":
                return interval * 1000L * 60L;
            case "HOURS":
                return interval * 1000L * 60L * 60L;
            case "DAYS":
                return interval * 1000L * 60L * 60L * 24L;
            default:
                throw new AssertionError("TimeUnit for Schedule not implemented! (1)");
        }
    }

    public static boolean checkIdNotNull(long id) {
        return id > 0L;
    }

    public static boolean isLocal(FrontendCommunicator fc,
        Map<String, List<MonitorInstance>> monitors) {
        String first_ip = null;
        String ip = null;

        for (Map.Entry<String, List<MonitorInstance>> entry : monitors.entrySet()) {

            for (MonitorInstance instance : entry.getValue()) {

                ip = fc.getPublicAddressByMetricInstance(instance);

                if (first_ip == null) {
                    first_ip = fc.getPublicAddressByMetricInstance(instance);
                }

                if (!ip.equals(first_ip)) {
                    return true;
                }
            }
        }

        return true;
    }

    public static Double numberToDouble(Object rawNumber) {
        LazilyParsedNumber number = (LazilyParsedNumber) rawNumber;
        return number.doubleValue();
    }
}