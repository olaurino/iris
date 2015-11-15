/**
 * Copyright (C) 2012 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.sed.builder;

import cfa.vo.sedlib.Sed;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NEDImporterIT {

    /**
     * Test of getSedFromName method, of class NEDImporter.
     */
    @Test
    public void testGetSedFromWrongName() throws Exception {
        String targetName = "pippo";
        Sed result = NEDImporter.getSedFromName(targetName);
        assertEquals(0, result.getNumberOfSegments());
    }

    /**
     * Test of getSedFromName method, of class NEDImporter.
     */
    @Test
    public void testGetSedFor3C186() throws Exception {
        String targetName = "3c186";
        Sed result = NEDImporter.getSedFromName(targetName);
        assertEquals(1, result.getNumberOfSegments());
    }

    /**
     * Test of getSedFromName method, of class NEDImporter.
     */
    @Test
    public void testGetSedFor3C273() throws Exception {
        String targetName = "3c273";
        Sed result = NEDImporter.getSedFromName(targetName);
        assertEquals(1, result.getNumberOfSegments());
    }

}