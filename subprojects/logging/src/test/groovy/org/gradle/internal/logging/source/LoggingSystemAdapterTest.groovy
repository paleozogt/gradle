/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.logging.source

import org.gradle.api.logging.LogLevel
import spock.lang.Specification

class LoggingSystemAdapterTest extends Specification {
    def loggingConfigurer = Mock(LoggingConfigurer)
    def LoggingSystemAdapter loggingSystem = new LoggingSystemAdapter(loggingConfigurer)

    def onUsesLoggingConfigurerToSetLoggingLevel() {
        when:
        loggingSystem.on(LogLevel.DEBUG, LogLevel.DEBUG)

        then:
        1 * loggingConfigurer.configure(LogLevel.DEBUG)
        0 * loggingConfigurer._
    }

    def restoreSetsLoggingLevelToDefaultLoggingLevelWhenOff() {
        when:
        def snapshot = loggingSystem.snapshot()
        loggingSystem.restore(snapshot)

        then:
        1 * loggingConfigurer.configure(LogLevel.LIFECYCLE)
        0 * loggingConfigurer._
    }

    def restoreSetsLoggingLevel() {
        given:
        def snapshot1 = loggingSystem.on(LogLevel.DEBUG, LogLevel.DEBUG)
        def snapshot2 = loggingSystem.snapshot()
        def snapshot3 = loggingSystem.on(LogLevel.INFO, LogLevel.INFO)

        when:
        loggingSystem.restore(snapshot3)

        then:
        1 * loggingConfigurer.configure(LogLevel.DEBUG)
        0 * loggingConfigurer._

        when:
        loggingSystem.restore(snapshot1)

        then:
        1 * loggingConfigurer.configure(LogLevel.LIFECYCLE)
        0 * loggingConfigurer._

        when:
        loggingSystem.restore(snapshot2)

        then:
        1 * loggingConfigurer.configure(LogLevel.DEBUG)
        0 * loggingConfigurer._
    }

}
