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
package org.gradle.api.internal.project;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.api.initialization.dsl.ScriptHandler;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.LoggingManager;
import org.gradle.groovy.scripts.DefaultScript;
import org.gradle.internal.logging.StandardOutputCapture;

import java.util.Map;

public abstract class ProjectScript extends DefaultScript {
    public void apply(Closure closure) {
        ((Project) getScriptTarget()).apply(closure);
    }

    @SuppressWarnings("unchecked")
    public void apply(Map options) {
        ((Project) getScriptTarget()).apply(options);
    }

    public Project getProject() {
        return (Project) getScriptTarget();
    }

    public ScriptHandler getBuildscript() {
        return ((Project) getScriptTarget()).getBuildscript();
    }

    public void buildscript(Closure configureClosure) {
        ((Project) getScriptTarget()).buildscript(configureClosure);
    }

    public StandardOutputCapture getStandardOutputCapture() {
        return ((ProjectInternal) getScriptTarget()).getStandardOutputCapture();
    }

    public LoggingManager getLogging() {
        return ((Project) getScriptTarget()).getLogging();
    }

    public Logger getLogger() {
        return ((Project) getScriptTarget()).getLogger();
    }

    public String toString() {
        return getScriptTarget().toString();
    }
}
